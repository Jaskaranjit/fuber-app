package com.fuber.constants;

/**
 * Constants class for Fuber App
 * Created by Jaskaranjit on 11/30/17.
 */
public class FuberConstants
{

    public static final String MONGO_CONNECTION_STRING = "mongodb://localhost:27017";

    // api related constants
    public static final String BASE_URL = "/fuber";
    public static final String DOCS_URL = "/docs/";
    public static final int SERVICE_PORT = 8507;

    // database related constants
    public static final String FUBER_DB = "fuberDB";
    public static final String CABS_COLLECTION = "cabs";
    public static final String USERS_COLLECTION = "users";


    /**
     * private constructor
     */
    private FuberConstants()
    {

    }
}
