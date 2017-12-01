package com.fuber.dao;

import com.fuber.constants.FuberConstants;
import com.fuber.entities.Cab;
import com.fuber.entities.Location;
import com.fuber.entities.TripStatus;
import com.fuber.entities.User;
import com.fuber.exception.FuberException;
import com.fuber.utils.MongoConnector;
import com.fuber.utils.Utils;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;


/**
 * Test class for {@link UserDAO}
 * Created by Jaskaranjit on 11/30/17.
 */
public class UserDAOTest
{
    private static final Random RANDOM = new Random();
    private static final String TEST_FUBER_DB = "testFuber" + RANDOM.nextInt();
    private static final DBCollection CABS_COLLECTION = MongoConnector.getDB( TEST_FUBER_DB )
        .getCollection( FuberConstants.CABS_COLLECTION );
    private static final DBCollection USERS_COLLECTION = MongoConnector.getDB( TEST_FUBER_DB )
        .getCollection( FuberConstants.USERS_COLLECTION );
    private static final UserDAO USER_DAO = UserDAO.getInstance( TEST_FUBER_DB );


    @Before public void setUp() throws Exception
    {
        // save cabs
        CABS_COLLECTION.save( new BasicDBObject( Utils.toMap( new Cab( "1", new Location( 10, 100 ), "red", true ) ) ) );
        CABS_COLLECTION.save( new BasicDBObject( Utils.toMap( new Cab( "2", new Location( 20, 500 ), "pink", true ) ) ) );
        CABS_COLLECTION.save( new BasicDBObject( Utils.toMap( new Cab( "3", new Location( 100, 400 ), "green", true ) ) ) );

        // save users
        User user = new User();
        user.setCabId( "1" );
        user.setUserId( "1" );
        user.setCabColor( "red" );
        user.setPickUpLocation( new Location( 10, 100 ) );
        user.setTravelDate( System.currentTimeMillis() );
        user.setTripStatus( TripStatus.ON_GOING );
        USERS_COLLECTION.save( new BasicDBObject( Utils.toMap( user ) ) );
    }


    @After public void tearDown() throws Exception
    {
        MongoConnector.getDB( TEST_FUBER_DB ).dropDatabase();
    }


    /**
     * Tests getting user for on going trip
     * @throws Exception
     */
    @Test public void getUserById() throws Exception
    {
        Assert.assertNotNull( USER_DAO.getUserById( "1" ) );

        //save another user with status "COMPLETE"
        User user = new User();
        user.setUserId( "2" );
        user.setTripStatus( TripStatus.COMPLETE );
        USER_DAO.saveUser( user );

        Assert.assertNotNull( USER_DAO.getUserById( "1" ) );
    }


    /**
     * Tests getting user when trip status is complete
     * @throws Exception
     */
    @Test (expected = FuberException.class) public void getUserByIdWhenStatusIsComplete() throws Exception
    {
        //save another user with status "COMPLETE"
        User user = new User();
        user.setUserId( "2" );
        user.setTripStatus( TripStatus.COMPLETE );
        USER_DAO.saveUser( user );

        USER_DAO.getUserById( "2" );
    }


    /**
     * Tests getting user when userId doesn't exist
     * @throws Exception
     */
    @Test (expected = FuberException.class) public void getUserByIdWhenUserIdIsNonExistent() throws Exception
    {
        USER_DAO.getUserById( "nonExistent" );
    }


    /**
     * Tests saving a user
     * @throws Exception
     */
    @Test public void saveUser() throws Exception
    {
        Assert.assertEquals( 1, USER_DAO.getUsersHistory( 10, 0 ).size() );
        User user = new User();
        user.setUserId( "2" );
        user.setTripStatus( TripStatus.COMPLETE );
        USER_DAO.saveUser( user );

        // ensure record is saved
        Assert.assertEquals( 2, USER_DAO.getUsersHistory( 10, 0 ).size() );
    }


    /**
     * Tests getting user history
     * @throws Exception
     */
    @Test public void getUsersHistory() throws Exception
    {
        List<User> usersList = USER_DAO.getUsersHistory( 10, 0 );
        Assert.assertEquals( 1, usersList.size() );
        Assert.assertEquals( "1", usersList.get( 0 ).getCabId() );
        Assert.assertEquals( "1", usersList.get( 0 ).getUserId() );

        // on second index, no records are found
        Assert.assertEquals( 0, USER_DAO.getUsersHistory( 10, 1 ).size() );
    }

}