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

    /** let's get this party started! */
    run: function () {
        $ENV[ 'NEW_NAME' ] = 'NEW-VAL'  // TODO: get from args
        $EXEC( "/usr/bin/env")
        print( $OUT )
        print( $ERR )
        if ( $ERR ) {
            throw new Error( 'stderr had content' )
        }
    },

}

runner.run()


// vi: ts=4 sw=4 expandtab ai
// *** EOF ***
