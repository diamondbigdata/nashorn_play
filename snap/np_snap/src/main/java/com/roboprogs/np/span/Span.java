package com.roboprogs.np.span;

import java.io.*;
import javax.script.ScriptEngineManager;
import jdk.nashorn.api.scripting.NashornScriptEngine;

import spark.Spark;


/**
 * Demo SNAP web stack.
 */
public class Span {

    /**
     * Nashorn JS interpreter/JIT.
     * Assume it is thead-safe, for now.
     */
    public static final NashornScriptEngine engine =
            (NashornScriptEngine) ( new ScriptEngineManager() ) .
                getEngineByName( "nashorn" );

    /** program entry point */
    public static void main( String[] args ) throws Exception {
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
                Span.class.getResourceAsStream( fname ) ) );
        Span.engine.eval( in );
        in.close();;
    }

}
