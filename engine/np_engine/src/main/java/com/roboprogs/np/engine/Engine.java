package com.roboprogs.np.engine;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * Load the Nashorn scripting engine and run a simple script.
 */
public class Engine {

    public static void main( String[] args ) throws Throwable {
        ScriptEngine engine;

        engine = ( new ScriptEngineManager() ).getEngineByName( "nashorn" );
        engine.eval( "var x = { hello: 'world' }" ) );
        engine.eval( "print( x + '\\n' )" );
        System.out.println( engine.eval( "x" ) );
    }

}


// vi: ts=4 sw=4 expandtab
// *** EOF ***
