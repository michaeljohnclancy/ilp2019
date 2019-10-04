package test.uk.ac.ed.inf.powergrab;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ed.inf.powergrab.Position;
import uk.ac.ed.inf.powergrab.Station;



public class StationTest {

    public static final double DOUBLEPRECISION = 10e-15;

    Station station;

    @Before
    public void makeObjects(){
        station = new Station.StationBuilder()
                                        .setPower(-74.62785781303182)
                                        .setBalance(-123.76819100883893)
                                        .setPosition(-3.190260653365977, 55.94587364601307)
                                        .build();
    }

    @Test
    public void ifStationBuilderIsUsed_thenFieldsAreCorrectlyPopulated(){

        Position position = new Position(-3.190260653365977, 55.94587364601307);
        double power = -74.62785781303182;
        double balance = -123.76819100883893;


        assert position.equals(station.getPosition());
        assertDoubleEquals(power, station.getPower());
        assertDoubleEquals(balance, station.getBalance());
    }

    public void assertDoubleEquals(double d1, double d2){
        assert Math.abs(d2-d1) < DOUBLEPRECISION;
    }





}