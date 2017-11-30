package com.fuber.utils;

import com.fuber.entities.Cab;
import com.fuber.entities.Location;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * Test class for Utils class
 * Created by Jaskaranjit on 11/30/17.
 */
public class UtilsTest
{

    /**
     * Tests whether string is empty or null
     * @throws Exception
     */
    @Test public void isNullOrEmpty() throws Exception
    {
        Assert.assertTrue( Utils.isNullOrEmpty( "" ) );
        Assert.assertTrue( Utils.isNullOrEmpty( null ) );
        Assert.assertFalse( Utils.isNullOrEmpty( "some test string" ) );

    }


    /**
     * Tests assigning of nearest cab
     * @throws Exception
     */
    @Test public void assignCab() throws Exception
    {
        // only available cabs are fed to the function
        List<Cab> cabs = new ArrayList<>();
        cabs.add( new Cab( "1", new Location( 10, 100 ), "red", true ) );
        cabs.add( new Cab( "2", new Location( 20, 500 ), "pink", true ) );
        cabs.add( new Cab( "3", new Location( 100, 400 ), "green", true ) );
        cabs.add( new Cab( "4", new Location( 109, 190 ), "red", true ) );

        Assert.assertEquals( "2", Utils.assignCab( cabs, new Location( 20, 520 ) ).getCabId() );
        Assert.assertEquals( "3", Utils.assignCab( cabs, new Location( 200, 400 ) ).getCabId() );
        Assert.assertEquals( "1", Utils.assignCab( cabs, new Location( 10, 100 ) ).getCabId() );
    }


    /**
     * Tests calculating distance between 2 points
     * @throws Exception
     */
    @Test public void calculateDistance() throws Exception
    {
        Location locationOne = new Location( 10, 100 );
        Location locationTwo = new Location( 10, 200 );

        Assert.assertEquals( 100, Utils.calculateDistance( locationOne, locationTwo ), 0.01 );
    }


    /**
     * Tests calculating travel time
     * @throws Exception
     */
    @Test public void calculateTime() throws Exception
    {
        // time(min) = distance(km)/speed(km/hr) * 60
        Assert.assertEquals( 120, Utils.calculateTime( 100 ), 0.01 );
    }


    /**
     * Tests fare calculation when car is of any color other than pink
     * @throws Exception
     */
    @Test public void calculateCost() throws Exception
    {
        Assert.assertEquals( 250, Utils.calculateCost( 100, 50, "red" ), 0.01 );
    }


    /**
     * Tests fare calculation when car is of pink color
     * @throws Exception
     */
    @Test public void calculateCostWhenCabColorIsPink() throws Exception
    {
        Assert.assertEquals( 255, Utils.calculateCost( 100, 50, "pink" ), 0.01 );
    }

}