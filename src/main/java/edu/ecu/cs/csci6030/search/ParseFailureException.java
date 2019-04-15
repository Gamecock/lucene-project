package edu.ecu.cs.csci6030.search;

/**
 * Exception if word added out of order
 *
 * @author Mike Finch
 * @version 1.0
 *
 */
public class ParseFailureException extends RuntimeException {

    /**
     * Create a new NameNotDefinedException.
     *
     * @param msg the exception message
     */
    public ParseFailureException(String msg) {
        super(msg);
    }
}
