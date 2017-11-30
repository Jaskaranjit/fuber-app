package com.fuber.utils;

import com.fuber.constants.FuberConstants;
import com.mongodb.MongoClient;
import org.junit.Assert;
import org.junit.Test;


/**
 * Test class for {@link MongoConnector}
 * Created by Jaskaranjit on 11/30/17.
 */
public class MongoConnectorTest
{
    /**
     * Tests caching of clients
     */
    @Test public void testGetClient()
    {
        // get first client
        MongoClient client = MongoConnector.getClient( FuberConstants.MONGO_CONNECTION_STRING );
        // get second client
        MongoClient client2 = MongoConnector.getClient( FuberConstants.MONGO_CONNECTION_STRING );
        //Ensure cached client is fetched
        Assert.assertEquals( client, client2 );
    }
}