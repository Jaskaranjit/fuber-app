package com.fuber.services;

import com.fuber.constants.FuberConstants;
import com.fuber.entities.*;
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
 * Test class for {@link UserService}
 * Created by Jaskaranjit on 11/30/17.
 */
public class UserServiceTest
{
    private static final Random RANDOM = new Random();
    private static final String TEST_FUBER_DB = "testFuber" + RANDOM.nextInt();
    private static final DBCollection CABS_COLLECTION = MongoConnector.getDB( TEST_FUBER_DB )
        .getCollection( FuberConstants.CABS_COLLECTION );
    private static final DBCollection USERS_COLLECTION = MongoConnector.getDB( TEST_FUBER_DB )
        .getCollection( FuberConstants.USERS_COLLECTION );


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

        // set test properties
        UserService.setFuberDB( TEST_FUBER_DB );
    }


    @After public void tearDown() throws Exception
    {
        MongoConnector.getDB( TEST_FUBER_DB ).dropDatabase();
    }


    /**
     * Tests getting user data
     * @throws Exception
     */
    @Test public void getUsersList() throws Exception
    {
        List<User> usersList = UserService.getUsersList( 10, 0 );
        Assert.assertEquals( 1, usersList.size() );
        Assert.assertEquals( "1", usersList.get( 0 ).getCabId() );
        Assert.assertEquals( "1", usersList.get( 0 ).getUserId() );

        // on second index, no records are found
        Assert.assertEquals( 0, UserService.getUsersList( 10, 1 ).size() );
    }


    /**
     * Tests booking a cab
     * @throws Exception
     */
    @Test public void bookCab() throws Exception
    {
        AssignedCabDetails assignedCabDetails = UserService.bookCab( new Location( 10, 100 ), "" );
        Assert.assertNotNull( assignedCabDetails.getUserId() );
        Assert.assertEquals( "Cab booked successfully", assignedCabDetails.getMessage() );
        Assert.assertEquals( "1", assignedCabDetails.getCabDetails().getCabId() );

        // ensure user details are saved. One record is inserted while setup, other is inserted in booking process
        Assert.assertEquals( 2, USERS_COLLECTION.count() );
    }


    /**
     * Tests booking of a pink cab
     * @throws Exception
     */
    @Test public void bookPinkCab() throws Exception
    {
        AssignedCabDetails assignedCabDetails = UserService.bookCab( new Location( 10, 100 ), "pink" );
        Assert.assertNotNull( assignedCabDetails.getUserId() );
        Assert.assertEquals( "Cab booked successfully", assignedCabDetails.getMessage() );
        Assert.assertEquals( "2", assignedCabDetails.getCabDetails().getCabId() );

        // ensure user details are saved. One record is inserted while setup, other is inserted in booking process
        Assert.assertEquals( 2, USERS_COLLECTION.count() );
    }


    /**
     * Tests booking a cab
     * @throws Exception
     */
    @Test public void bookCabWhenNoCabsAreFound() throws Exception
    {
        // remove all cabs
        MongoConnector.getDB( TEST_FUBER_DB ).dropDatabase();

        AssignedCabDetails assignedCabDetails = UserService.bookCab( new Location( 10, 100 ), "" );
        Assert.assertNull( assignedCabDetails.getUserId() );
        Assert.assertNull( assignedCabDetails.getCabDetails() );
        Assert.assertEquals( "No cabs found! Please try again", assignedCabDetails.getMessage() );
    }


    /**
     * Tests completing a trip
     * @throws Exception
     */
    @Test public void completeTrip() throws Exception
    {
        User user = UserService.completeTrip( "1", new Location( 10, 200 ) );
        Assert.assertEquals( 100, user.getTravelDistance(), 0.01 );
        Assert.assertEquals( 120, user.getTravelTime(), 0.01 );
        Assert.assertEquals( 320, user.getTotalFare(), 0.01 );

    }


    /**
     * Tests completing a trip when trip status for user is complete. Exception is thrown
     * @throws Exception
     */
    @Test (expected = FuberException.class) public void completeTripWhenStatusIsComplete() throws Exception
    {
        // save users
        User user = new User();
        user.setUserId( "2" );
        user.setTripStatus( TripStatus.COMPLETE );
        USERS_COLLECTION.save( new BasicDBObject( Utils.toMap( user ) ) );

        UserService.completeTrip( "2", new Location( 10, 200 ) );
    }


    /**
     * Tests completing a trip when userId is non existent
     * @throws Exception
     */
    @Test (expected = FuberException.class) public void completeTripWhenUserIdIsNonExistent() throws Exception
    {
        UserService.completeTrip( "nonExistentUser", new Location( 0, 0 ) );
    }

}