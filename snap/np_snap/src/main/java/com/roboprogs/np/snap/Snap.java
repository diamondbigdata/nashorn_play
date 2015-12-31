package com.roboprogs.np.snap;

import java.io.*;
import java.sql.Driver;
import java.sql.DriverManager;
import javax.script.ScriptEngineManager;
import javax.sql.DataSource;
import jdk.nashorn.api.scripting.NashornScriptEngine;

import org.postgresql.ds.PGPoolingDataSource;

import spark.Request;
import spark.Response;
import spark.Spark;


/**
 * Demo SNAP web stack.
 */
public class Snap {

    /** Cache of script engines, one per thread. */
    private static final ThreadLocal <NashornScriptEngine> engines =
            ThreadLocal.withInitial( Snap::initScriptEngine );

    /** program entry point */
    public static void main( String[] args ) throws Exception {

        // e.g. - view localhost:4567/html/index.html
        Spark.staticFileLocation( "/public" );

        // TODO: get and use a DB connection...
        // getDataSource().getConnection();

        Spark.get( "/hello", ( req, res ) -> {
            String name;

            runSnapWebFunc( "hello", req, res );

            name = req.queryParams( "name" );
            return "Hello, " + ( ( name != null) ?
                    name :
                    "World" );
        } );
    }

    /** run a function from the JS snap object as a web request handler */
    private static void runSnapWebFunc(
            String fname,
            Request req,
            Response res ) {
        final String WEB_REQ_OBJ = "snap";  // assume all handlers in this object

        NashornScriptEngine thdEngine;

        try {
            thdEngine = Snap.engines.get();  // one engine per thread, they are not thread safe
            thdEngine.invokeMethod( thdEngine.get( WEB_REQ_OBJ ),
                    fname, req, res );
        } catch ( Throwable e ) {
            // TODO: log, rather than re-throw
            throw new RuntimeException( "Failed request to " + fname, e );
        }
    }

    /** init an engine with all needed server side scripts */
    private static NashornScriptEngine initScriptEngine() {
        NashornScriptEngine engine;

        try {
            engine = (NashornScriptEngine) ( new ScriptEngineManager() ) .
                    getEngineByName( "nashorn" );
            loadScript( engine, "/js/snap.js" );
            return engine;
        } catch ( Throwable e ) {
            throw new RuntimeException( "Script engine init failed", e );
        }
    }

    /** Run (load) the indicated Javascript (resource) */
    private static void loadScript(
            NashornScriptEngine engine,
            String fname )
            throws Exception {
        Reader in;

        // TODO: proper error handling
        in = new BufferedReader( new InputStreamReader(
                Snap.class.getResourceAsStream( fname ) ) );
        engine.eval( in );
        in.close();
    }

    /** Get a datasource for connection. */
    private static DataSource getDataSource() {
        // TODO: configure externally
        final String DB_HOST = "localhost";
        final int DB_PORT = 5432;
        final String DB_NAME = "snap_demo_db";
        final String DB_USER = "app";
        final String DB_PWD = "cryptic";

        PGPoolingDataSource ds;

        try {
            DriverManager.registerDriver( (Driver)
                    Class.forName( "org.postgresql.Driver" ).newInstance() );
            ds = new PGPoolingDataSource();
            ds.setServerName( DB_HOST );
            ds.setPortNumber( DB_PORT );
            ds.setDatabaseName( DB_NAME );

            ds.setUser( DB_USER );
            ds.setPassword( DB_PWD );
            ds.setMaxConnections( 100 );  // default was less than 100
            return ds;
        } catch ( Throwable e) {
            throw new RuntimeException( "Datasource fetch failed", e );
        }
    }

}
