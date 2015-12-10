package com.roboprogs.np.span;

import spark.Spark;

/**
 * Hello world!
 */
public class Span {

    /** program entry point */
    public static void main( String[] args ) {
        Spark.get( "/hello", ( req, res ) -> {
            String name;

            name = req.queryParams( "name" );
            return "Hello, " + ( ( name != null) ?
                    name :
                    "World" );
        } );
    }

}
