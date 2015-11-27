#!/usr/bin/env jjs -scripting

// JavaScript script which also makes use of Java (1.8) code.
//  
//  Usage:
//          java_user.js [ -- number number number ... ]
//      example: java_user.js -- .1 .2
//      (see http://0.30000000000000004.com/, http://roboprogs.com/devel/2010.02.html)
// .....................................

'use strict'

/**
 * An example JS object (singleton) used as a namespace
 * @namespace
 */
var ns = {

    /** take numbers from command line, and add them up */
    run: function ( argv) {
        var acc

        acc = ns.create_number( '0.0' )
        argv.forEach( function ( num_str) {
            acc = acc.add( ns.create_number( num_str ) )
        } )
        print( 'Total: ' + acc + '\n')
    },

    /**
     * Factory method for fixed precision numbers.
     * @param num_str {string} - numeric value as a string
     * @returns {java.math.BigDecimal}
     */
    create_number: function ( num_str ) {
        return new java.math.BigDecimal( num_str )
    }

}

ns.run( $ARG)


// vi: ts=4 sw=4 expandtab ai
// *** EOF ***
