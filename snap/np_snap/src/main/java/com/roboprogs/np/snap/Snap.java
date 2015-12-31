package com.roboprogs.np.snap;

import java.io.*;
import java.sql.Driver;
import java.sql.DriverManager;
import javax.script.ScriptEngineManager;
import javax.sql.DataSource;
import jdk.nashorn.api.scripting.NashornScriptEngine;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.postgresql.ds.PGPoolingDataSource;

import spark.Request;
import spark.Response;
import spark.Spark;


/**
 * Demo SNAP web stack.
 */
public class Snap {

    private static final Logger log = Log.getLogger( Snap.class );

    /** Cache of script engines, one per thread. */
    private static final ThreadLocal <NashornScriptEngine> engines =
            ThreadLocal.withInitial( Snap::initScriptEngine );

    /** program entry point */
    public static void main( String[] args ) throws Exception {
        log.info( "Starting app..." );

        // e.g. - view localhost:4567/html/index.html
        Spark.staticFileLocation( "/public" );
        registerJsGetter( "/hello", "hello" );

        // TODO: get and use a DB connection...
        // getDataSource().getConnection();
    }

    /** register a JS function to handle a web request */
    private static void registerJsGetter(
            String path,
            String fname ) {
        Spark.get( path, ( req, res ) -> runSnapWebFunc( fname, req, res ) );
    }

    /** run a function from the JS snap object on a web request handler thread */
    private static Object runSnapWebFunc(
            String fname,
            Request req,
            Response res ) {
        final String WEB_REQ_OBJ = "snap";  // assume all handlers in this object

        NashornScriptEngine thdEngine;
        String errMsg;

        try {
            thdEngine = Snap.engines.get();  // one engine per thread, they are not thread safe
            return thdEngine.invokeMethod( thdEngine.get( WEB_REQ_OBJ ),
                    fname, req, res );
        } catch ( Throwable e ) {
            errMsg = "Failed request to " + fname;
            log.warn( errMsg, e );
            return errMsg;  // TODO: better error handling (can I return an exception and get an error page?)
        }
    }

    /** init an engine with all needed server side scripts */
    private static NashornScriptEngine initScriptEngine() {
        NashornScriptEngine engine;

        log.info( "Starting JS engine for thread" );
        try {
            engine = (NashornScriptEngine) ( new ScriptEngineManager() ) .
                    getEngineByName( "nashorn" );
            loadScript( engine, "/js/snap.js" );
            return engine;
        } catch ( Throwable e ) {
            throw new RuntimeException( "Script engine init failed", e );
        } finally {
            log.info( "JS engine started (or failed)" );
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
