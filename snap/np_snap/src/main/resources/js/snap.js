"use strict"

/**
 * Namespace for top level request handlers in this demo.
 * @namespace
 */
var snap = {

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

print( 'Loaded' )
// TODO: log that script was loaded
