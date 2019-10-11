package uk.ac.ed.inf.powergrab;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;


public class StationTest {

    private static final double DOUBLEPRECISION = 10e-14;

    private Station station;
    private Agent agent;

    @Before
    public void makeObjects(){
        station = new Station(
                "237d−16f8−57e5−67fc−2d3e−1ca1",
                new Position(55.94587364601307, -3.190260653365977),
                -10.2,
                -20.8
        );

        agent = new StatelessAgent(
                "agent0",
                new Position(55.944425, -3.188396)
        );
    }

    @Test
    public void ifStationBuilderIsUsed_thenFieldsAreCorrectlyPopulated(){

        Position position = new Position(55.94587364601307, -3.190260653365977);
        double power = -20.8 ;
        double balance = -10.2;

        assert position.equals(station.getPosition());
        assertDoubleEquals(power, station.getPower());
        assertDoubleEquals(balance, station.getCoins());
    }

    @Test
    public void ifJsonStringMappedToStation_thenStationDeserializerIsUsed_andStationObjectReturned() throws JsonProcessingException {

        String stationJson = "{\"type\":\"Feature\",\n" +
                "   \"properties\":{\n" +
                "      \"id\":\"237d−16f8−57e5−67fc−2d3e−1ca1\",\n" +
                "      \"coins\":\"-10.2\",\n" +
                "      \"power\":\"-20.8\",\n" +
                "      \"marker−symbol\":\"lighthouse\",\n" +
                "      \"marker−color\":\"#003800\"\n" +
                "   },\n" +
                "   \"geometry\":{\n" +
                "      \"type\":\"Point\",\n" +
                "      \"coordinates\":[\n" +
                "         -3.190260653365977,\n" +
                "          55.94587364601307\n" +
                "      ]\n" +
                "   }\n" +
                "}";

        Station actualStation = new ObjectMapper().readValue(stationJson, Station.class);

        assert station.equals(actualStation);
    }

    @Test
    public void ifTransferStationResourcesToAgent_thenAgentAndStationHaveCorrectUpdatedResources(){
        station.transferResourcesTo(agent);

        assertDoubleEquals(station.getCoins(), -10.2);
        assertDoubleEquals(agent.getCoins(), 0.0);

        assertDoubleEquals(station.getPower(), 0.0);
        assertDoubleEquals(agent.getPower(), 229.2);
    }


    public static void assertDoubleEquals(double d1, double d2){
        assert Math.abs(d2-d1) < DOUBLEPRECISION;
    }
}