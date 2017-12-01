package com.fuber.resources;

import com.fuber.entities.AssignedCabDetails;
import com.fuber.entities.Location;
import com.fuber.entities.User;
import com.fuber.exception.FuberException;
import com.fuber.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * Resource class for serving user requirements like booking a cab or completing a trip
 * Created by Jaskaranjit on 11/30/17.
 */
@Path ("/v1/user") @Api (value = "Cab user services", description = "Web Services to serve customer bookings") public class UserResource
{
    private static final Logger LOG = LoggerFactory.getLogger( UserResource.class );


    /**
     * Handles request to get customer history
     * @return list of cabs
     */
    @GET @Path ("/") @ApiOperation (value = "Returns customer list", notes = "Returns customer list sorted by travel date", responseContainer = "List", response = User.class) @Produces (MediaType.APPLICATION_JSON) public Response getUsers(
        @ApiParam (value = "Batch Size") @DefaultValue (value = "100") @QueryParam (value = "batchSize") final int batchSize,
        @ApiParam (value = "Batch Index") @DefaultValue (value = "0") @QueryParam (value = "batchIndex") final int batchIndex )
    {
        LOG.info( "Got request to customer history for {} batchIndex and {} batchSize", batchIndex, batchSize );
        return Response.ok().entity( UserService.getUsersList( batchSize, batchIndex ) ).build();
    }


    /**
     * Handles request to book a cab
     * @param getPinkCab color of the cab
     * @param pickUpLocation pick up location
     * @return Nearest available cab, if found else 404
     */
    @POST @Path ("/book/") @ApiOperation (value = "Books a Cab", notes = "Books a cab and returns details of the nearest cab", response = AssignedCabDetails.class) @Produces (MediaType.APPLICATION_JSON) public Response bookCab(
        @ApiParam (value = "Pink Color") @DefaultValue (value = "false") @QueryParam (value = "pinkColor") final boolean getPinkCab,
        @ApiParam (value = "Location", required = true) final Location pickUpLocation )
    {
        LOG.info( "Got request to book a cab for {} location of Pink color : {} ", pickUpLocation, getPinkCab );
        AssignedCabDetails assignedCabDetails = null;
        String cabColor = "";
        if ( getPinkCab ) {
            cabColor = "pink";
        }
        try {
            assignedCabDetails = UserService.bookCab( pickUpLocation, cabColor );
        } catch ( FuberException e ) {
            LOG.error( "Exception while booking a cab", e );
            Response.status( Response.Status.BAD_REQUEST ).build();
        }
        return Response.ok().entity( assignedCabDetails ).build();
    }


    /**
     * Handles request to complete the trip
     * @param userId user id for which travel is to be completed
     * @param dropLocation drop location
     * @return trip details on completion
     */
    @POST @Path ("/complete/{userId}") @ApiOperation (value = "Complete a trip", notes = "Returns the user details on trip completion", response = User.class) @Produces (MediaType.APPLICATION_JSON) public Response completeTrip(
        @ApiParam (value = "userId") @PathParam ("userId") final String userId,
        @ApiParam (value = "Drop Location", required = true) final Location dropLocation )
    {
        LOG.info( "Got request to complete a trip for {} user at {} location", userId, dropLocation );
        User user = null;
        try {
            user = UserService.completeTrip( userId, dropLocation );
        } catch ( FuberException e ) {
            LOG.error( "Exception while completing the trip", e );
            Response.status( Response.Status.BAD_REQUEST ).build();
        }
        return Response.ok().entity( user ).build();
    }
}
