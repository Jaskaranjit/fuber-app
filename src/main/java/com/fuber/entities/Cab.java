package com.fuber.entities;

import com.fuber.utils.Utils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.DBObject;


/**
 * POJO class for cab details
 * Created by Jaskaranjit on 11/30/17.
 */
public class Cab
{
    private String cabId;
    private Location cabLocation;
    private String color;
    private boolean isAvailable;


    /**
     * default constructor
     */
    public Cab()
    {
        // do nothing
    }


    /**
     * Initialise the {@link Cab}
     * @param cabId cab id
     * @param cabLocation cab location
     * @param color color of the cab
     * @param isAvailable cab availability
     */
    public Cab( String cabId, Location cabLocation, String color, boolean isAvailable )
    {
        this.cabId = cabId;
        this.cabLocation = cabLocation;
        this.color = color;
        this.isAvailable = isAvailable;
    }


    public String getCabId()
    {
        return cabId;
    }


    public void setCabId( String cabId )
    {
        this.cabId = cabId;
    }


    public Location getCabLocation()
    {
        return cabLocation;
    }


    public void setCabLocation( Location cabLocation )
    {
        this.cabLocation = cabLocation;
    }


    public String getColor()
    {
        return color;
    }


    public void setColor( String color )
    {
        this.color = color;
    }


    public boolean isAvailable()
    {
        return isAvailable;
    }


    public void setAvailable( boolean available )
    {
        isAvailable = available;
    }


    /**
     * Converts DBObject to cab object
     * @param object object to be converted
     * @return cab for dbObject
     */
    public static Cab fromDBObject( DBObject object )
    {
        JsonObject jsonObject = (JsonObject) new JsonParser().parse( object.toString() );
        Cab cab = Utils.GSON.fromJson( jsonObject, Cab.class );
        return cab;
    }


    @Override public String toString()
    {
        return "Cab{" + "cabId='" + cabId + '\'' + ", cabLocation=" + cabLocation + ", color='" + color + '\''
            + ", isAvailable=" + isAvailable + '}';
    }
}
