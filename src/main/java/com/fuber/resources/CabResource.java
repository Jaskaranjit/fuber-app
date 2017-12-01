package com.fuber.resources;

import com.fuber.entities.Cab;
import com.fuber.exception.FuberException;
import com.fuber.services.CabService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * Resource class for handling cab related operations
 * Created by Jaskaranjit on 11/30/17.
 */
@Path ("/v1/cabs") @Api (value = "Cab Details", description = "Web Services to serve cab details") public class CabResource
{
    private static final Logger LOG = LoggerFactory.getLogger( CabResource.class );


    /**
     * Handles request to get all cabs
     * @return list of cabs
     */
    @GET @Path ("/") @ApiOperation (value = "Returns all cabs", notes = "Returns all cabs", responseContainer = "List", response = Cab.class) @Produces (MediaType.APPLICATION_JSON) public Response getCabs(
        @ApiParam (value = "Only available Cabs") @DefaultValue (value = "true") @QueryParam (value = "availableCabsOnly") final boolean availableCabsOnly )
    {
        LOG.info( "Got request to fetch all cabs" );
        return Response.ok().entity( CabService.getCabs( availableCabsOnly ) ).build();

    }


    /**
     * Handles request to add a new cab
     * @return list of cabs
     */
    @POST @Path ("/") @ApiOperation (value = "Add a new cab", notes = "Saves a new Cab") @Produces (MediaType.APPLICATION_JSON) public Response saveCab(
        @ApiParam (value = "Cab to be saved", required = true) Cab cab )
    {
        LOG.info( "Got request to save a new cab {}", cab );
        try {
            CabService.saveCab( cab );
        } catch ( FuberException e ) {
            LOG.error( "Exception while saving the cab", e );
            Response.status( Response.Status.BAD_REQUEST ).build();
        }
        return Response.status( Response.Status.CREATED ).build();

    }


}
