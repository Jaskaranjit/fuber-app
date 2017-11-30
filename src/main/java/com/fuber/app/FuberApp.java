package com.fuber.app;

import com.caffinc.jetter.Api;
import com.fuber.constants.FuberConstants;
import com.fuber.resources.CabResource;
import com.fuber.utils.Utils;
import org.eclipse.jetty.util.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * App class for Fuber Application
 * Created by Jaskaranjit on 11/30/17.
 */
public class FuberApp
{
    private static final Logger LOG = LoggerFactory.getLogger( FuberApp.class );
    private static final int DEFAULT_SERVER_PORT = FuberConstants.SERVICE_PORT;


    /**
     * private constructor
     */
    private FuberApp()
    {

    }


    /**
     * main to start the app
     *
     * @param args arguments
     */
    public static void main( String[] args )
    {
        try {
            // Workaround for resources from JAR files
            Resource.setDefaultUseCaches( false );

            String baseUrl = FuberConstants.BASE_URL;
            String swaggerPath = FuberConstants.DOCS_URL;
            LOG.info( "Swagger URL - " + "localhost:" + DEFAULT_SERVER_PORT + baseUrl + swaggerPath );

            new Api( DEFAULT_SERVER_PORT ).setBaseUrl( baseUrl )
                .addServiceResource( CabResource.class, "Fuber Services", "Resources serving Fuber Application" )
                .addStaticResource( Utils.class.getClassLoader().getResource( "swaggerui" ).toURI().toString(),
                    baseUrl + swaggerPath ).enableCors().start();
        } catch ( Exception e ) {
            LOG.error( "There was an error running Fuber App", e );
        }
    }
}
