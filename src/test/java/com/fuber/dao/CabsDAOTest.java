package com.fuber.dao;

import com.fuber.constants.FuberConstants;
import com.fuber.entities.Cab;
import com.fuber.entities.Location;
import com.fuber.utils.MongoConnector;
import com.fuber.utils.Utils;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;


/**
 * Test class for {@link CabsDAO}
 * Created by Jaskaranjit on 11/30/17.
 */
public class CabsDAOTest
{
    private static final Random RANDOM = new Random();
    private static final String TEST_FUBER_DB = "testFuber" + RANDOM.nextInt();
    private static final DBCollection CABS_COLLECTION = MongoConnector.getDB( TEST_FUBER_DB )
        .getCollection( FuberConstants.CABS_COLLECTION );
    private static final CabsDAO CABS_DAO = CabsDAO.getInstance( TEST_FUBER_DB );


    @Before public void setUp() throws Exception
    {
        CABS_COLLECTION.save( new BasicDBObject( Utils.toMap( new Cab( "1", new Location( 10, 100 ), "red", true ) ) ) );
        CABS_COLLECTION.save( new BasicDBObject( Utils.toMap( new Cab( "2", new Location( 20, 500 ), "pink", false ) ) ) );
        CABS_COLLECTION.save( new BasicDBObject( Utils.toMap( new Cab( "3", new Location( 100, 400 ), "green", true ) ) ) );
    }


    @After public void tearDown() throws Exception
    {
        MongoConnector.getDB( TEST_FUBER_DB ).dropDatabase();
    }


    /**
     * Tests saving a cab
     * @throws Exception
     */
    @Test public void saveCab() throws Exception
    {
        Assert.assertEquals( 3, CABS_DAO.getCabs( false ).size() );
        Cab cab = new Cab( "4", new Location( 100, 400 ), "green", true );
        CABS_DAO.saveCab( cab );
        Assert.assertEquals( 4, CABS_DAO.getCabs( false ).size() );

    }


    /**
     * Tests getting the cabs
     * @throws Exception
     */
    @Test public void getCabs() throws Exception
    {
        // get all cabs
        Assert.assertEquals( 3, CABS_DAO.getCabs( false ).size() );
        // get available cabs
        Assert.assertEquals( 2, CABS_DAO.getCabs( true ).size() );
    }


    /**
     * Tests cab availability for a given color
     * @throws Exception
     */
    @Test public void getAllAvailableCabsForColor() throws Exception
    {
        Assert.assertEquals( 1, CABS_DAO.getAllAvailableCabsForColor( "red" ).size() );
        Assert.assertEquals( 0, CABS_DAO.getAllAvailableCabsForColor( "pink" ).size() );
        // if no color is specified, return all available cabs
        Assert.assertEquals( 2, CABS_DAO.getAllAvailableCabsForColor( "" ).size() );
    }


    /**
     * Tests cab availability updation
     * @throws Exception
     */
    @Test public void updateCabAvailability() throws Exception
    {
        Cab cab = new Cab( "4", new Location( 10, 100 ), "green", true );
        CABS_DAO.saveCab( cab );

        cab = Cab.fromDBObject( CABS_COLLECTION.findOne( new BasicDBObject( "_id", "4" ) ) );
        Assert.assertTrue( cab.isAvailable() );

        //update the availability
        CABS_DAO.updateCabAvailability( cab.getCabId(), false );

        cab = Cab.fromDBObject( CABS_COLLECTION.findOne( new BasicDBObject( "_id", "4" ) ) );
        Assert.assertFalse( cab.isAvailable() );
    }


    /**
     * Tests cab location updation
     * @throws Exception
     */
    @Test public void updateCabLocation() throws Exception
    {

        Cab cab = new Cab( "4", new Location( 10, 100 ), "green", true );
        CABS_DAO.saveCab( cab );

        cab = Cab.fromDBObject( CABS_COLLECTION.findOne( new BasicDBObject( "_id", "4" ) ) );
        Assert.assertEquals( 100.0, cab.getCabLocation().getLongitude(), 0.01 );
        Assert.assertEquals( 10.0, cab.getCabLocation().getLatitude(), 0.01 );

        //update the availability
        CABS_DAO.updateCabLocation( cab.getCabId(), new Location( 20, 200 ) );

        cab = Cab.fromDBObject( CABS_COLLECTION.findOne( new BasicDBObject( "_id", "4" ) ) );
        Assert.assertEquals( 200.0, cab.getCabLocation().getLongitude(), 0.01 );
        Assert.assertEquals( 20.0, cab.getCabLocation().getLatitude(), 0.01 );
    }

}