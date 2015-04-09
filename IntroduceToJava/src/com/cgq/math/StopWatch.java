package com.cgq.math;
/**
 * @comment
 * @author cgqfzy
 * @version 1.0
 * @date Apr 9, 2015 9:55:24 PM
 */
public class StopWatch {

	 private final long start;

	   /**
	     * Create a stopwatch object.
	     */
	    public StopWatch() {
	        start = System.currentTimeMillis();
	    } 


	   /**
	     * Return elapsed time (in seconds) since this object was created.
	     */
	    public double elapsedTime() {
	        long now = System.currentTimeMillis();
	        return (now - start) / 1000.0;
	    }

}
