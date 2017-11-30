package com.fuber.entities;

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
    private long travelTime;
    private double totalFare;
    private String cabColor;
    private long travelDate;


    public String getUserId()
    {
        return userId;
    }


    public void setUserId( String userId )
    {
        this.userId = userId;
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


    public long getTravelTime()
    {
        return travelTime;
    }


    public void setTravelTime( long travelTime )
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


    @Override public String toString()
    {
        return "User{" + "userId='" + userId + '\'' + ", pickUpLocation=" + pickUpLocation + ", dropLocation=" + dropLocation
            + ", travelDistance=" + travelDistance + ", travelTime=" + travelTime + ", totalFare=" + totalFare + ", cabColor='"
            + cabColor + '\'' + '}';
    }
}
