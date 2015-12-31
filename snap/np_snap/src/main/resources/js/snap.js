"use strict"

/**
 * Namespace for top level request handlers in this demo.
 * @namespace
 */
var snap = {

    /** java based logger */
    log: Java.type( 'org.eclipse.jetty.util.log.Log').getLogger( 'snap.js' ),

    /**
     * Handle the "hello" (test) web request
     * @param req - Spark request
     * @param res - Spark response
     */
    hello: function ( req, res ) {
        var name

        name = req.queryParams( 'name' )
        return 'Hello, ' + ( name ?
                name :
                'World' )
    },

}

snap.log.info( 'Loaded' )

// vi: ts=4 sw=4 expandtab
// *** EOF ***
