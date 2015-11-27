#!/usr/bin/env jjs -scripting -cp ../../target/np_script-1.0-SNAPSHOT.jar

// JavaScript script which calls back and forth between Java (1.8) code.
//  
//  Usage:
//          mixer.js
// .....................................

'use strict'

/**
 * An example JS object (singleton) used as a namespace
 * @namespace
 */
var mixer = {

    /** supporting java class reference */
    support: Java.type( 'com.roboprogs.np.script.JSSupport'),

    /** let's get this party started! */
    run: function () {
        var mixed

        print( "\nCreating (but NOT running) #1\n")
        mixed = mixer.create_mixed_object()

        print( "\nCreating and running #2\n")
        mixer.create_mixed_object().run()

        print( "\nRunning #1\n")
        mixed.run()
    },

    /** factory method for mixed JS/Java object */
    create_mixed_object: function () {
        var self

        // java calling down into JS is easy (provide one or more functions):
        self = new mixer.support( function () { mixer.help( self) } )

        // however, calling back into Java is somewhat more complicated...
        // NO: monkey patch the object to have superclass method access:
        // self.super_jprint = Java.super( self )
        // the object returned in this case is really a Java object,
        // which cannot be dynamic

        return self
    },

    /** function to use as the helper in the "mixed" object */
    help: function ( self ) {
        Java.super( self ).jprint( 'JavaScript asked to print this' )
    },

}

mixer.run()


// vi: ts=4 sw=4 expandtab ai
// *** EOF ***
