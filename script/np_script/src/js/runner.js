#!/usr/bin/env jjs -scripting -cp ../../target/np_script-1.0-SNAPSHOT.jar

// JavaScript script which calls an external program and sets environment variables.
//  
//  Usage:
//          runner.js -- name value
// .....................................

'use strict'

/**
 * An example JS object (singleton) used as a namespace
 * @namespace
 */
var runner = {

    /** alias this function to avoid warning (it really does take an argument) */
    log: print,

    /** let's get this party started! */
    run: function ( name, value ) {
        var cmd

        runner._update_env( name, value )
        cmd = '/usr/bin/env'
        runner._execute1( cmd )
        runner._execute2( cmd )
    },

    /** update environment variables to share with child process(es) */
    _update_env: function ( name, value ) {
        $ENV[ name ] = value
    },

    /** run an external command using $EXEC function */
    _execute1: function ( cmd ) {
        runner.log( '\n\nVia $EXEC:\n\n' )
        $EXEC( cmd )
        runner._dump_exec_output()
    },

    /** run an external command using backticks */
    _execute2: function ( cmd ) {
        runner.log( '\n\nVia backticks\n\n' )
        `${cmd}`  // WebStorm sees this as a syntax error :-(
        runner._dump_exec_output()
    },

    /** "un-constipate" the output of the last process ran */
    _dump_exec_output: function () {
        // alas, stdout/stderr are captured, whether or not you want that
        runner.log( $OUT )
        runner.log( $ERR )
        if ( $ERR ) {
            throw new Error( 'stderr had content' )
        }
    },

}

runner.run( $ARG[ 0 ], $ARG[ 1 ] )


// vi: ts=4 sw=4 expandtab ai
// *** EOF ***
