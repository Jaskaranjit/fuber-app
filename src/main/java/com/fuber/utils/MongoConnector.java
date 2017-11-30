package com.fuber.utils;

import com.fuber.constants.FuberConstants;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


/**
 * Mongo Connection class
 * Created by Jaskaranjit on 11/30/17.
 */
public class MongoConnector
{
    private static final Logger LOG = LoggerFactory.getLogger( MongoConnector.class );
    private static final String MONGO_DEFAULT = FuberConstants.MONGO_CONNECTION_STRING;
    private static final Map<String, MongoClient> CLIENT_MAP = new HashMap<>();
    private static final Map<String, DB> DB_MAP = new HashMap<>();


    /**
     * private constructor
     */
    private MongoConnector()
    {
    }


    /**
     * Returns a {@link MongoClient} for a mongo URI
     * @param mongoConnection MongoConnection String
     * @return Mongo Client
     */
    public static synchronized MongoClient getClient( String mongoConnection )
    {
        if ( !CLIENT_MAP.containsKey( mongoConnection ) ) {
            LOG.info( "Request for a new Client @ {}" + mongoConnection );
            synchronized ( CLIENT_MAP ) {
                if ( !CLIENT_MAP.containsKey( mongoConnection ) ) {
                    CLIENT_MAP.put( mongoConnection, new MongoClient( new MongoClientURI( mongoConnection ) ) );
                }
            }
        }
        return CLIENT_MAP.get( mongoConnection );
    }


    /**
     * Returns the {@link MongoClient} for the URI picked from the config     *
     * @return Mongo Client
     */
    public static MongoClient getClient()
    {
        return getClient( MONGO_DEFAULT );
    }


    /**
     * Returns the {@link DB} for the default {@link MongoClient}     *
     * @param dbName Database name to fetch
     * @return DB Object
     */
    public static DB getDB( String dbName )
    {
        if ( !DB_MAP.containsKey( dbName ) ) {
            LOG.info( "Request for a new DB @ " + dbName );
            synchronized ( DB_MAP ) {
                if ( !DB_MAP.containsKey( dbName ) ) {
                    LOG.info( "Obtaining DB @ " + dbName );
                    DB_MAP.put( dbName, getClient().getDB( dbName ) );
                }
            }
        }
        return DB_MAP.get( dbName );
    }
}
