package test.uk.ac.ed.inf.powergrab;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ed.inf.powergrab.Position;
import uk.ac.ed.inf.powergrab.Station;



public class StationTest {

    private static final double DOUBLEPRECISION = 10e-15;

    private Station station;

    @Before
    public void makeObjects(){
        station = new Station.StationBuilder()
                                        .setPower(40.763427231356744)
                                        .setBalance(15.655206987957596)
                                        .setPosition(-3.190260653365977, 55.94587364601307)
                                        .build();
    }

    @Test
    public void ifStationBuilderIsUsed_thenFieldsAreCorrectlyPopulated(){

        Position position = new Position(-3.190260653365977, 55.94587364601307);
        double power = 40.763427231356744;
        double balance = 15.655206987957596;


        assert position.equals(station.getPosition());
        assertDoubleEquals(power, station.getPower());
        assertDoubleEquals(balance, station.getBalance());
    }

    @Test
    public void ifJsonStationProvided_thenCorrectObjectIsCreated() throws JsonProcessingException {

        String stationJson = "{\"type\":\"Feature\",\n" +
                "   \"properties\":{\n" +
                "      \"id\":\"237d−16f8−57e5−67fc−2d3e−1ca1\",\n" +
                "      \"coins\":\"15.655206987957596\",\n" +
                "      \"power\":\"40.763427231356744\",\n" +
                "      \"marker−symbol\":\"lighthouse\",\n" +
                "      \"marker−color\":\"#003800\"\n" +
                "   },\n" +
                "   \"geometry\":{\n" +
                "      \"type\":\"Point\",\n" +
                "      \"coordinates\":[\n" +
                "         -3.190260653365977,\n" +
                "         55.94587364601307\n" +
                "      ]\n" +
                "   }\n" +
                "}";

        Station actualStation = new ObjectMapper().readValue(stationJson, Station.class);

        assert station.equals(actualStation);

    }

    private void assertDoubleEquals(double d1, double d2){
        assert Math.abs(d2-d1) < DOUBLEPRECISION;
    }
}