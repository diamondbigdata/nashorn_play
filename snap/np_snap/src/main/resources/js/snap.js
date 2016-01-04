"use strict"

/**
 * Namespace for top level request handlers in this demo.
 * @namespace
 */
var snap = {

    /** java based logger */
    log: Java.type( 'org.eclipse.jetty.util.log.Log').getLogger( 'snap.js' ),

    /** java side of snap app code */
    snap_j: Java.type( 'com.roboprogs.np.snap.Snap' ),

    /** jdbc wrapper tool (java based) */
    jdbc: Java.type( 'com.roboprogs.np.snap.Snap').getJdbcTemplate(),

    /** JSON conversion tool for native Java objects */
    gson: Java.type( 'com.roboprogs.np.snap.Snap').getGson(),

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

    /**
     * Handle a test request for JSON data from database
     * @param req - Spark request
     * @param res - Spark response
     */
    test: function ( req, res ) {
        var results, entry_set, json

        snap.log.info( 'Querying test collection...' )
        results = snap.jdbc.queryForList(
                "select * from collections where collection = 'test'" )
        snap.log.info( 'Raw results: ' + results )
        results.forEach( function ( row ) {
            snap.log.info( 'Row: ' + row )
            entry_set = new java.util.ArrayList ( row.entrySet() )
            entry_set.forEach( function ( entry ) {
                snap.log.info( '\tEntry: ' + entry )
                snap.log.info( '\t\tType: ' + entry.getValue().getClass().getName() )
            } )
        } )
        json = snap.gson.toJson( results )
        snap.log.info( 'JSON results: ' + json )
        return json
    },

}

snap.log.info( 'Loaded' )

// vi: ts=4 sw=4 expandtab
// *** EOF ***
