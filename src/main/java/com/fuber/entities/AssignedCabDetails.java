package com.fuber.entities;

/**
 * POJO class for cab details to be returned to user
 * Created by Jaskaranjit on 11/30/17.
 */
public class AssignedCabDetails
{
    private final String message;
    private final String userId;
    private final Cab cabDetails;


    /**
     * Initialize {@link AssignedCabDetails}
     * @param message booking message
     * @param userId userId
     * @param cabDetails cab details assigned to user
     */
    public AssignedCabDetails( String message, String userId, Cab cabDetails )
    {
        this.message = message;
        this.userId = userId;
        this.cabDetails = cabDetails;
    }


    /**
     * Initiatize {@link AssignedCabDetails} with booking message
     * @param message message
     */
    public AssignedCabDetails( String message )
    {
        this( message, null, null );
    }


    public String getUserId()
    {
        return userId;
    }


    public Cab getCabDetails()
    {
        return cabDetails;
    }


    public String getMessage()
    {
        return message;
    }


    @Override public String toString()
    {
        return "AssignedCabDetails{" + "message='" + message + '\'' + ", userId='" + userId + '\'' + ", cabDetails="
            + cabDetails + '}';
    }
}
