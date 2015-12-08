package com.roboprogs.np.engine;

import java.io.*;
import javax.script.ScriptEngineManager;
import jdk.nashorn.api.scripting.NashornScriptEngine;

/**
 * Load the Nashorn scripting engine,
 *  and then load a script so that it could be run under debugger.
 */
public class Debug {

    public static void main( String[] args ) throws Throwable {
        NashornScriptEngine engine;
        Object status;
        MyRunner tsOps;

        engine = (NashornScriptEngine) ( new ScriptEngineManager() ) .
                getEngineByName( "nashorn" );
        // engine.compile( new FileReader( "../../script/np_script/src/js/moment.js") );
        status = engine.eval( new FileReader( "../../script/np_script/src/js/moment.js") );
        status = engine.eval( new FileReader( "src/js/js_dep.js") );
        // TODO: close files

/*
        tsOps = engine.getInterface( MyRunner.class );
        tsOps.run( null, 0, null);
        tsOps.run( "MM/DD/YYYY", 0, null);
        tsOps.run( "Do MMMM, YYYY", 7, "day");
        tsOps.run( "MM/DD/YYYY HH:mm", -4, "hour");
*/

        // step INTO these in debugger to enable JS debugging

        engine.eval( "dep.run()" );
        engine.eval( "dep.run( 'MM/DD/YYYY' )" );
        engine.eval( "dep.run( 'Do MMMM, YYYY', 7, 'day' )" );
        engine.eval( "dep.run( 'MM/DD/YYYY HH:mm', -4, 'hour' )" );
    }

    /** dummy interface to fish up the function we want in js_user.js */
    private interface MyRunner {

        public void run( Object format, Object increment, Object unit ) 
        ;

    }

}


// vi: ts=4 sw=4 expandtab
// *** EOF ***
