package com.roboprogs.np.snap;

import java.io.*;
import java.sql.Driver;
import java.sql.DriverManager;
import javax.script.ScriptEngineManager;
import javax.sql.DataSource;
import jdk.nashorn.api.scripting.NashornScriptEngine;

import org.postgresql.ds.PGPoolingDataSource;

import spark.Spark;


/**
 * Demo SNAP web stack.
 */
public class Snap {

    /**
     * Nashorn JS interpreter/JIT.
     * Assume it is thead-safe, for now.
     */
    public static final NashornScriptEngine engine =
            (NashornScriptEngine) ( new ScriptEngineManager() ) .
                getEngineByName( "nashorn" );

    /** program entry point */
    public static void main( String[] args ) throws Exception {

        // e.g. - view localhost:4567/html/index.html
        Spark.staticFileLocation( "/public" );

        // TODO: get and use a DB connection...
        getDataSource().getConnection();

        loadScript( "/js/snap.js" );
        Spark.get( "/hello", ( req, res ) -> {
            String name;

            name = req.queryParams( "name" );
            return "Hello, " + ( ( name != null) ?
                    name :
                    "World" );
        } );
    }

    /** Run (load) the indicated Javascript (resource) */
    private static void loadScript( String fname ) throws Exception {
        Reader in;

        // TODO: proper error handling
        in = new BufferedReader( new InputStreamReader(
                Snap.class.getResourceAsStream( fname ) ) );
        Snap.engine.eval( in );
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
