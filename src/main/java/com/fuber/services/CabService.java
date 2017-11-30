package com.fuber.services;

import com.fuber.dao.CabsDAO;
import com.fuber.entities.Cab;
import com.fuber.exception.FuberException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;


/**
 * Service class to handle all cab services
 * Created by Jaskaranjit on 11/30/17.
 */
public class CabService
{
    private static final Logger LOG = LoggerFactory.getLogger( CabService.class );
    private static CabsDAO cabsDAO = CabsDAO.getInstance();


    /**
     * private constructor
     */
    private CabService()
    {

    }


    /**
     * Set User Specific Properties
     * @param fuberDB fuber database
     */
    public static void setFuberDB( String fuberDB )
    {
        cabsDAO = CabsDAO.getInstance( fuberDB );
    }


    /**
     * Returns list of cabs
     * @return list of cabs
     */
    public static List<Cab> getCabs()
    {
        LOG.info( "Fetching all cabs" );
        return cabsDAO.getCabs();
    }


    /**
     * Saves a cab
     * @param cab cab to be saved
     * @return cabId if cab saved successfully else null
     */
    public static String saveCab( Cab cab ) throws FuberException
    {
        LOG.info( "Saving {} cab", cab );
        String cabId = cabsDAO.saveCab( cab );
        if ( cabId == null ) {
            throw new FuberException( "Exception while saving the cab" );
        }
        return cabId;
    }

}
