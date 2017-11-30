package com.fuber.services;

import com.fuber.dao.CabsDAO;
import com.fuber.dao.UserDAO;
import com.fuber.entities.*;
import com.fuber.exception.FuberException;
import com.fuber.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;


/**
 * Service class for handling customer related services
 * Created by Jaskaranjit on 11/30/17.
 */
public class UserService
{
    private static final Logger LOG = LoggerFactory.getLogger( UserService.class );
    private static CabsDAO cabsDAO = CabsDAO.getInstance();
    private static UserDAO userDAO = UserDAO.getInstance();


    /**
     * private constructor
     */
    private UserService()
    {

    }


    /**
     * Set User Specific Properties
     * @param fuberDB fuber database
     */
    public static void setFuberDB( String fuberDB )
    {
        cabsDAO = CabsDAO.getInstance( fuberDB );
        userDAO = UserDAO.getInstance( fuberDB );
    }


    /**
     * Handles request to get customer history
     * @return list of cabs
     */
    public static List<User> getUsersList( int batchSize, int batchIndex )
    {
        LOG.info( "Fetching customers list" );
        return userDAO.getUsersHistory( batchSize, batchIndex );
    }


    /**
     * Handles request to book a cab
     * @param pickUpLocation pick up location
     * @param cabColor color of the cab
     * @return Nearest available cab, if found else 404
     * @throws FuberException
     */
    public static AssignedCabDetails bookCab( Location pickUpLocation, String cabColor ) throws FuberException
    {
        LOG.info( "Booking a cab for {} location and {} color", pickUpLocation, cabColor );

        // get all available cabs
        List<Cab> availableCabs = cabsDAO.getAllAvailableCabsForColor( cabColor );

        // find the nearest cab
        Cab cab = Utils.assignCab( availableCabs, pickUpLocation );

        if ( cab == null ) {
            return new AssignedCabDetails( "No cabs found! Please try again" );
        }

        // save the user details
        String userId = UUID.randomUUID().toString();
        User user = new User();
        user.setCabId( cab.getCabId() );
        user.setUserId( userId );
        user.setCabColor( cab.getColor() );
        user.setPickUpLocation( pickUpLocation );
        user.setTravelDate( System.currentTimeMillis() );
        user.setTripStatus( TripStatus.ON_GOING );
        userDAO.saveUser( user );

        // update the status of cab to unavailable
        cabsDAO.updateCabAvailability( cab.getCabId(), false );

        return new AssignedCabDetails( "Cab booked successfully", userId, cab );
    }


    /**
     * Handles request to complete the trip
     * @param userId user id for which travel is to be completed
     * @param dropLocation drop location
     * @return trip details on completion
     */
    public static User completeTrip( String userId, Location dropLocation ) throws FuberException
    {
        // fetch user details
        User user = userDAO.getUserById( userId );

        // calculate distance
        double distance = Utils.calculateDistance( user.getPickUpLocation(), dropLocation );

        // calculate time
        double time = Utils.calulateTime( distance );

        // calculate fare
        double fare = Utils.calculateCost( distance, time, user.getCabColor() );

        // update the user object with trip status and fare details
        user.setTripStatus( TripStatus.COMPLETE );
        user.setDropLocation( dropLocation );
        user.setTotalFare( fare );
        user.setTravelTime( time );
        user.setTravelDistance( distance );
        userDAO.saveUser( user );

        // update the cab location and status
        cabsDAO.updateCabAvailability( user.getCabId(), true );
        cabsDAO.updateCabLocation( user.getCabId(), dropLocation );

        return user;
    }


}
