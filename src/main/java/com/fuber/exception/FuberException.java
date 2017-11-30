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
     * Instantiates a new fuber exception.
     */
    public FuberException()
    {
        super();
    }


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
     * Initialises Exception with all parameters
     * @param message exception message
     * @param cause throwable cause
     * @param enableSuppression boolean for enabling suppression
     * @param writableStackTrace boolean for printing stack trace
     */
    public FuberException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace )
    {
        super( message, cause, enableSuppression, writableStackTrace );
    }


    /**
     * Initialises Exception with exception message
     * @param message exception message
     */
    public FuberException( String message )
    {
        super( message );
    }


    /**
     * Initialises Exception with throwable cause
     * @param cause throwable cause
     */
    public FuberException( Throwable cause )
    {
        super( cause );
    }
}