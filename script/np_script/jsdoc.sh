#!/bin/sh

# Run jsdoc3
# TODO:  get to work with jjs (possibly with jvm-npm?), rather than node.
# Note that jsdoc is also a Maven plugin, but you won't enjoy it :-)

jsdoc -d ./docs/ `find . -name '*.js'`

# *** EOF ***
