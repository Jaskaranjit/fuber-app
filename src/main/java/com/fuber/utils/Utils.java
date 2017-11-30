package com.fuber.utils;

import com.fuber.entities.Cab;
import com.fuber.entities.Location;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Utility class
 * Created by Jaskaranjit on 11/30/17.
 */
public class Utils
{
    public static final Gson GSON = new Gson();


    /**
     * private constructor
     */
    private Utils()
    {

    }


    /**
     * String Util method
     * @param string the string
     * @return true if a string is null or an empty string
     */
    public static boolean isNullOrEmpty( String string )
    {
        return string == null || string.isEmpty();
    }


    /**
     * Assign the cab to the user. The nearest cab is picked and assigned to the user
     * @param cabs list of cabs to choose from
     * @param userLocation user location for reference
     * @return cab to be assigned to user
     */
    public static Cab assignCab( List<Cab> cabs, Location userLocation )
    {
        // If no cabs are found, return null
        if ( cabs.isEmpty() ) {
            return null;
        }

        // sort the cabs via distance and return the nearest cab
        Collections.sort( cabs, new MyComparator( userLocation ) );
        return cabs.get( 0 );

    }


    /**
     * Calculates distance between two locations
     * @param locationOne location one
     * @param locationTwo location two
     * @return distance between 2 points
     */
    public static double calculateDistance( Location locationOne, Location locationTwo )
    {
        double distance = Math.abs( Math.sqrt(
            ( locationOne.getLatitude() - locationTwo.getLatitude() ) * ( locationOne.getLatitude() - locationTwo
                .getLatitude() ) + ( locationOne.getLongitude() - locationTwo.getLongitude() ) * ( locationOne.getLongitude()
                - locationTwo.getLongitude() ) ) );
        return distance;
    }


    /**
     * Calculates the time taken in minutes to travel the distance. Assuming the average speed of the car is 50KM/hr
     * @param distance distance to calculate time for
     * @return time taken in minutes to cover the distance
     */
    public static double calulateTime( double distance )
    {
        double timeInHour = distance / 50;
        return timeInHour * 60;
    }


    /**
     * Calculates the total fare. Fare distribution is 1 dogecoin per minute, and 2 dogecoin per kilometer.
     * For pink color cabs, 5 dogecoins are added
     * @param distance distance travelled
     * @param time time taken to cover the distance
     * @param color color of the cab
     * @return total fare for the trip
     */
    public static double calculateCost( double distance, double time, String color )
    {
        // 2 dogecoin per kilometer and 1 dogecoin per minute
        double totalFare = ( distance * 2 ) + time;

        // add 5 dogecoins for pink cabs
        if ( color.equalsIgnoreCase( "pink" ) ) {
            totalFare += 5;
        }

        return totalFare;

    }


    /**
     * Static class to find the nearest cab by distance
     */
    static class MyComparator implements Comparator<Cab>
    {

        private Location userLocation;


        public MyComparator( Location userLocation )
        {
            this.userLocation = userLocation;
        }


        public int compare( Cab cab1, Cab cab2 )
        {
            double distanceCabOne = Utils.calculateDistance( cab1.getCabLocation(), this.userLocation );
            double distanceCabTwo = Utils.calculateDistance( cab2.getCabLocation(), this.userLocation );

            if ( distanceCabOne > distanceCabTwo ) {
                return 1;
            } else if ( distanceCabOne == distanceCabTwo ) {
                return 0;
            } else if ( distanceCabOne < distanceCabTwo ) {
                return -1;
            }
            return 0;
        }
    }
}
