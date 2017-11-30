package com.fuber.entities;

/**
 * POJO class for location parameters
 * Created by Jaskaranjit on 11/30/17.
 */
public class Location
{

    private double latitude;
    private double longitude;


    /**
     * default constructor
     */
    public Location()
    {
        // do nothing
    }


    /**
     * Initialise the {@link Location}
     * @param latitude latitude
     * @param longitude longitude
     */
    public Location( double latitude, double longitude )
    {
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public double getLatitude()
    {
        return latitude;
    }


    public double getLongitude()
    {
        return longitude;
    }


    public void setLatitude( double latitude )
    {
        this.latitude = latitude;
    }


    public void setLongitude( double longitude )
    {
        this.longitude = longitude;
    }


    @Override public String toString()
    {
        return "Location{" + "latitude=" + latitude + ", longitude=" + longitude + '}';
    }
}
