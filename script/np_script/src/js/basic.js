#!/usr/bin/env jjs -scripting

// Basic demo of writing a JavaScript script,
//  but using Java 1.8 jjs rather than Node.js
//  
//  Usage:
//          basic.js [ -- <<name>> ]
// .....................................

'use strict'

// print( $ARG.length )
var who = ( $ARG[ 0 ] ) ?
        $ARG[ 0 ] : 'Kilroy'
print( who +  ' was here!' )


// vi: ts=4 sw=4 expandtab ai
// *** EOF ***
