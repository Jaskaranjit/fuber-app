package com.fuber.entities;

/**
 * POJO class for location parameters
 * Created by Jaskaranjit on 11/30/17.
 */
public class Location
{

    private final double latitude;
    private final double longitude;


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


    @Override public String toString()
    {
        return "Location{" + "latitude=" + latitude + ", longitude=" + longitude + '}';
    }
}
