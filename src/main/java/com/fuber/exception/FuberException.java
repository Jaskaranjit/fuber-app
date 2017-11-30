package com.fuber.exception;

/**
 * User defined exception class
 * Created by Jaskaranjit on 11/30/17.
 */
public class FuberException extends Exception
{
    /**
     * Field serialVersionUID. (value is 1)
     */
    private static final long serialVersionUID = 1L;


    /**
     * Initialises Exception with exception message and throwable cause
     * @param message exception message
     * @param th exception message
     */
    public FuberException( String message, Throwable th )
    {
        super( message, th );
    }


    /**
     * Initialises Exception with exception message
     * @param message exception message
     */
    public FuberException( String message )
    {
        super( message );
    }

}