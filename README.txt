Playing with Java 1.8 Nashorn JavaScript engine

Project file summary:

=== script directory ===

Javascript scripts, run via jjs

Key Files:
	* basic.js
		A simple script to read command line arguments.
	* java_user.js
		A script which uses a native java type (to do some arithmetic).
	* runner.js
		A script to manipulate environment variables,
		run an external program,
		and capture the program output.
	* mixer.js
		A script which calls back and forth with some custom java code.
	* JSSupport.java
		The java code used by mixer.js
	* js_dep.js
		Loads a 3rd party script as a dependency before starting.
	* jsdoc.sh
		Sample (shell) script to run (a version of) the JSDoc tool,
		which does for Javascript what javadoc does for Java.

=== engine directory ===

Java program(s), which load a JS engine and run scripts.

Key Files:
	* Engine.java
		Creates a javascript engine, and feeds it text to interpret.
	* Debug.java
		Loads an external script into an engine,
		then calls into it in a way that allows the IDE debugger
		to see the javascript source.
	* js_dep.js
		Copy of an earlier script, used as debugger fodder.


=== snap directory ===

Proof of concept of a web stack which mixes server side javascript with java
	* Spark (a Jetty wrapper)
	* Nashorn
	* AngularJS
	* PostgreSQL
	... "SNAP"

The idea is to provide an alternative to "MEAN"
(Mongo, Express, AngularJS, Node.js)

Key Files:
	* Snap.java
		Application start point.
	* snap.js
		Javascript part of the (server side) application.
	* index.html
		The static web page.
	* start_empty_pg_db.sh / init_snap_db.sql
		Scripting to create a test database instance and
		run a server against it.


