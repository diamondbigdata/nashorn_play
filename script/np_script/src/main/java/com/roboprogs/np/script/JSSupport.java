package com.roboprogs.np.script;

/**
 * Base class to interact with a JavaScript script.
 */
public abstract class JSSupport {

	/** data to be shared with children (sub-classes) */
	protected final String data;

	/** init */
	protected JSSupport() {
		this.data = "family secret";
	}

	/** force superclass and subclass to interact */
	public void run() {
		jprint( "BEFORE helper routine");
		help();
		jprint( "AFTER helper routine");
	}

	/** "callback" for subclass to implement */
	protected abstract void help()
		;

	/** print stuff, Java style */
	protected void jprint( String s) {
		System.out.println( "JAVALAND print: " + s);
	}

}

// vi: ts=4 sw=4 ai expandtab
// *** EOF ***