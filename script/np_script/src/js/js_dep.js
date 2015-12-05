#!/usr/bin/env jjs -scripting moment.js

// Run a script which has a dependency on another script.
// This script assumes moment.js is (downloaded and) in the current directory
//
//  Usage:
//          js_dep.js [ format [ increment unit ] ]
// .....................................

'use strict'

/**
 * Dependency demo namespace / singleton
 * @namespace
 */
var dep = {

    /** alias this function to avoid warning (it really does take an argument) */
    log: print,

    /**
     * Use the moment.js library to do some date formatting / arithmetic.
     * @param format {string} - date format
     * @param increment {number} - number of units to add (or subtract)
     * @param unit {string} - scale of unit (days, hours, etc)
     */
    run: function ( format, increment, unit ) {
        var result

        result =
                unit ?
                    moment().add( increment, unit ).format( format ) :
                format ?
                    moment().format( format ) :
                    moment()
        dep.log( result )
    },

}

dep.run( $ARG[ 0 ], $ARG[ 1 ], $ARG[ 2 ] )  // will pass undef vs out of bounds exception


// vi: ts=4 sw=4 expandtab ai
// *** EOF ***
