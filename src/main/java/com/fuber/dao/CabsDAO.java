package com.fuber.dao;

import com.fuber.constants.FuberConstants;
import com.fuber.entities.Cab;
import com.fuber.entities.Location;
import com.fuber.utils.MongoConnector;
import com.fuber.utils.Utils;
import com.mongodb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


/**
 * DAO class for cab related operations
 * Created by Jaskaranjit on 11/30/17.
 */
public class CabsDAO
{
    private static final Logger LOG = LoggerFactory.getLogger( CabsDAO.class );
    private final String fuberDB;
    private final DBCollection collection;


    /**
     * private Constructor for CabsDAO
     * @param dbName database name
     */
    private CabsDAO( String dbName )
    {
        this.fuberDB = dbName;
        this.collection = MongoConnector.getDB( this.fuberDB ).getCollection( FuberConstants.CABS_COLLECTION );
    }


    /**
     * Fetches a pointer to the specified DB
     * @param dbName Name of the DB to point to
     * @return Instance of the DAO pointing to the specified DB and collection
     */
    public static CabsDAO getInstance( String dbName )
    {
        LOG.info( "Instantiating Cabs DAO with {} db", dbName );
        return new CabsDAO( dbName );
    }


    /**
     * Fetches a pointer to the "fuber" DB
     * @return Instance of the DAO pointing to the "fuber" DB
     */
    public static CabsDAO getInstance()
    {
        return getInstance( FuberConstants.FUBER_DB );
    }


    /**
     * Saves a cab instance to the database
     * @param cab cab to be saved
     * @return mongo document Id for cab data if data was saved else null
     */
    public String saveCab( Cab cab )
    {
        LOG.info( "Saving cab {} in db", cab );
        String id;
        if ( cab.getCabId() != null ) {
            id = cab.getCabId();
        } else {
            id = UUID.randomUUID().toString();
            cab.setCabId( id );
        }
        Map map = Utils.GSON.fromJson( Utils.GSON.toJson( cab ), HashMap.class );
        map.put( "_id", id );
        DBObject obj = new BasicDBObject( map );
        WriteResult save = this.collection.save( obj );
        if ( save.getN() > 0 ) {
            return id;
        } else {
            return null;
        }
    }


    /**
     * Returns all cabs
     * availabilityStatus return all cabs if status is set to false else return available cabs
     * @return list of all cabs
     */
    public List<Cab> getCabs( boolean availabilityStatus )
    {
        LOG.info( "Getting all cabs" );
        List<Cab> cabs = new ArrayList<>();
        BasicDBObject findQuery = new BasicDBObject();
        if ( availabilityStatus ) {
            findQuery.append( "isAvailable", availabilityStatus );
        }
        try ( Cursor cursor = this.collection.find( findQuery ) ) {
            while ( cursor.hasNext() ) {
                cabs.add( Cab.fromDBObject( cursor.next() ) );
            }
        }
        LOG.info( "Size of cabsList : {}", cabs.size() );
        return cabs;
    }


    /**
     * Get List of all available cabs. If color is provided, filter with color
     * @param color color of the cab
     * @return list of available cabs satisfying color criteria
     */
    public List<Cab> getAllAvailableCabsForColor( String color )
    {
        LOG.info( "Getting all available cabs of {} color", color );
        List<Cab> cabs = new ArrayList<>();

        BasicDBObject findQuery = new BasicDBObject( "isAvailable", true );
        if ( !Utils.isNullOrEmpty( color ) ) {
            findQuery.append( "color", color );
        }

        try ( Cursor cursor = this.collection.find( findQuery ) ) {
            while ( cursor.hasNext() ) {
                cabs.add( Cab.fromDBObject( cursor.next() ) );
            }
        }
        LOG.info( "Size of available cabsList : {}", cabs.size() );
        return cabs;
    }


    /**
     * updates availability for cab
     * @param cabId cabId to be updated
     * @param availability availability status
     */
    public void updateCabAvailability( String cabId, boolean availability )
    {
        LOG.info( "Updating cab availability of {} to {}", cabId, availability );
        this.collection.update( new BasicDBObject( "_id", cabId ),
            new BasicDBObject( "$set", new BasicDBObject( "isAvailable", availability ) ) );
    }


    /**
     * updates location for cab
     * @param cabId cabId to be updated
     * @param location cab location
     */
    public void updateCabLocation( String cabId, Location location )
    {
        LOG.info( "Updating cab location of {} to {}", cabId, location );
        Map locationMap = Utils.GSON.fromJson( Utils.GSON.toJson( location ), HashMap.class );
        this.collection.update( new BasicDBObject( "_id", cabId ),
            new BasicDBObject( "$set", new BasicDBObject( "cabLocation", locationMap ) ) );
    }
}
