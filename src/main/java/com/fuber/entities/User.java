package com.fuber.entities;

import com.fuber.utils.Utils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.DBObject;


/**
 * POJO class for user tracking
 * Created by Jaskaranjit on 11/30/17.
 */
public class User
{
    private String userId;
    private String cabId;
    private Location pickUpLocation;
    private Location dropLocation;
    private double travelDistance;
    private double travelTime;
    private double totalFare;
    private String cabColor;
    private long travelDate;
    private TripStatus tripStatus;


    public String getUserId()
    {
        return userId;
    }


    public void setUserId( String userId )
    {
        this.userId = userId;
    }


    public String getCabId()
    {
        return cabId;
    }


    public void setCabId( String cabId )
    {
        this.cabId = cabId;
    }


    public Location getPickUpLocation()
    {
        return pickUpLocation;
    }


    public void setPickUpLocation( Location pickUpLocation )
    {
        this.pickUpLocation = pickUpLocation;
    }


    public Location getDropLocation()
    {
        return dropLocation;
    }


    public void setDropLocation( Location dropLocation )
    {
        this.dropLocation = dropLocation;
    }


    public double getTravelDistance()
    {
        return travelDistance;
    }


    public void setTravelDistance( double travelDistance )
    {
        this.travelDistance = travelDistance;
    }


    public double getTravelTime()
    {
        return travelTime;
    }


    public void setTravelTime( double travelTime )
    {
        this.travelTime = travelTime;
    }


    public double getTotalFare()
    {
        return totalFare;
    }


    public void setTotalFare( double totalFare )
    {
        this.totalFare = totalFare;
    }


    public String getCabColor()
    {
        return cabColor;
    }


    public void setCabColor( String cabColor )
    {
        this.cabColor = cabColor;
    }


    public long getTravelDate()
    {
        return travelDate;
    }


    public void setTravelDate( long travelDate )
    {
        this.travelDate = travelDate;
    }


    public TripStatus getTripStatus()
    {
        return tripStatus;
    }


    public void setTripStatus( TripStatus tripStatus )
    {
        this.tripStatus = tripStatus;
    }


    /**
     * Converts DBObject to user object
     * @param object object to be converted
     * @return user for dbObject
     */
    public static User fromDBObject( DBObject object )
    {
        JsonObject jsonObject = (JsonObject) new JsonParser().parse( object.toString() );
        User user = Utils.GSON.fromJson( jsonObject, User.class );
        return user;
    }


    @Override public String toString()
    {
        return "User{" + "userId='" + userId + '\'' + ", cabId='" + cabId + '\'' + ", pickUpLocation=" + pickUpLocation
            + ", dropLocation=" + dropLocation + ", travelDistance=" + travelDistance + ", travelTime=" + travelTime
            + ", totalFare=" + totalFare + ", cabColor='" + cabColor + '\'' + ", travelDate=" + travelDate + ", tripStatus="
            + tripStatus + '}';
    }
}
