package com.fuber.dao;

import com.fuber.constants.FuberConstants;
import com.fuber.entities.User;
import com.fuber.exception.FuberException;
import com.fuber.utils.MongoConnector;
import com.fuber.utils.Utils;
import com.mongodb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * DAO class for user related operations
 * Created by Jaskaranjit on 11/30/17.
 */
public class UserDAO
{
    private static final Logger LOG = LoggerFactory.getLogger( UserDAO.class );
    private final String fuberDB;
    private final DBCollection collection;


    /**
     * private Constructor for UserDAO
     * @param dbName database name
     */
    private UserDAO( String dbName )
    {
        this.fuberDB = dbName;
        this.collection = MongoConnector.getDB( this.fuberDB ).getCollection( FuberConstants.USERS_COLLECTION );
    }


    /**
     * Fetches a pointer to the specified DB
     * @param dbName Name of the DB to point to
     * @return Instance of the DAO pointing to the specified DB and collection
     */
    public static UserDAO getInstance( String dbName )
    {
        LOG.info( "Instantiating Users DAO with {} db", dbName );
        return new UserDAO( dbName );
    }


    /**
     * Fetches a pointer to the "fuber" DB
     * @return Instance of the DAO pointing to the "fuber" DB
     */
    public static UserDAO getInstance()
    {
        return getInstance( FuberConstants.FUBER_DB );
    }


    /**
     * Get user by user Id
     * @param userId userId
     * @return user corresponding to userId
     * @throws FuberException
     */
    public User getUserById( String userId ) throws FuberException
    {
        LOG.info( "Fetching user for {} userId", userId );
        DBObject user = this.collection.findOne( new BasicDBObject( "_id", userId ) );

        if ( user == null ) {
            throw new FuberException( "Exception while getting the user" );
        }
        return User.fromDBObject( user );
    }


    /**
     * saves/updates the user
     * @param user user to be saved
     * @throws FuberException
     */
    public void saveUser( User user ) throws FuberException
    {
        LOG.info( "Saving user {} in db", user );
        Map map = Utils.GSON.fromJson( Utils.GSON.toJson( user ), HashMap.class );
        map.put( "_id", user.getUserId() );
        DBObject obj = new BasicDBObject( map );
        WriteResult save = this.collection.save( obj );
        if ( save.getN() < 0 ) {
            throw new FuberException( "Exception while saving the user" );
        }
    }


    /**
     * Get users history list
     * @param batchSize batch size
     * @param batchIndex batch index
     * @return list of users
     */
    public List<User> getUsersHistory( int batchSize, int batchIndex )
    {
        LOG.info( "Getting users list for {} batchSize and {} batchIndex", batchSize, batchIndex );
        List<User> users = new ArrayList<>();
        try ( Cursor cursor = this.collection.find().sort( new BasicDBObject( "travelDate", -1 ) ).limit( batchSize )
            .skip( batchIndex * batchSize ) ) {
            while ( cursor.hasNext() ) {
                users.add( User.fromDBObject( cursor.next() ) );
            }
        }

        LOG.info( "Size of user history list : {}", users.size() );
        return users;
    }

}
