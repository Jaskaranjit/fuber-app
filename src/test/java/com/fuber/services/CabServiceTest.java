package com.fuber.services;

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
 * Test class for {@link CabService}
 * Created by Jaskaranjit on 11/30/17.
 */
public class CabServiceTest
{
    private static final Random RANDOM = new Random();
    private static final String TEST_FUBER_DB = "testFuber" + RANDOM.nextInt();
    private static final DBCollection CABS_COLLECTION = MongoConnector.getDB( TEST_FUBER_DB )
        .getCollection( FuberConstants.CABS_COLLECTION );


    @Before public void setUp() throws Exception
    {
        CABS_COLLECTION.save( new BasicDBObject( Utils.toMap( new Cab( "1", new Location( 10, 100 ), "red", true ) ) ) );
        CABS_COLLECTION.save( new BasicDBObject( Utils.toMap( new Cab( "2", new Location( 20, 500 ), "pink", false ) ) ) );
        CABS_COLLECTION.save( new BasicDBObject( Utils.toMap( new Cab( "3", new Location( 100, 400 ), "green", true ) ) ) );

        // set test properties
        CabService.setFuberDB( TEST_FUBER_DB );
    }


    @After public void tearDown() throws Exception
    {
        MongoConnector.getDB( TEST_FUBER_DB ).dropDatabase();
    }


    /**
     * Tests getting all cabs
     * @throws Exception
     */
    @Test public void getCabs() throws Exception
    {
        Assert.assertEquals( 3, CabService.getCabs( false ).size() );
        Assert.assertEquals( 2, CabService.getCabs( true ).size() );

        // drop the collection
        MongoConnector.getDB( TEST_FUBER_DB ).dropDatabase();

        Assert.assertEquals( 0, CabService.getCabs( false ).size() );
    }


    /**
     * Tests saving a cab
     * @throws Exception
     */
    @Test public void saveCab() throws Exception
    {
        Assert.assertEquals( 3, CabService.getCabs( false ).size() );

        Cab cab = new Cab( "4", new Location( 109, 190 ), "red", true );
        CabService.saveCab( cab );

        Assert.assertEquals( 4, CabService.getCabs( false ).size() );
    }

}