package test.uk.ac.ed.inf.powergrab;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ed.inf.powergrab.Agent;
import uk.ac.ed.inf.powergrab.PowerGrabMap;
import uk.ac.ed.inf.powergrab.Station;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.NoSuchElementException;

public class PowerGrabMapTest {

    private Station expectedStation;
    private Agent inBoundsAgent;
    private Agent outOfBoundsAgent;
    private PowerGrabMap powerGrabMap;

    @Before
    public void before() throws IOException {
        expectedStation = new Station.StationBuilder()
                .setPosition(55.94386776638809, -3.1864088755508475)
                .setPower(79.57136572604678)
                .setBalance(31.66170339928509)
                .build();

        inBoundsAgent = new Agent.AgentBuilder()
                .setPosition(55.94387776638809, -3.1864088755508475)
                .setPower(35.5)
                .setBalance(15.0)
                .build();

        outOfBoundsAgent = new Agent.AgentBuilder()
                .setPosition(55.94356776638809, -3.1864088755508475)
                .setPower(35.5)
                .setBalance(15.0)
                .build();

        powerGrabMap = PowerGrabMap.getMap(LocalDate.of( 2019,1, 1));
    }

    @Test
    public void ifProvideLocalDateToGetMap_thenPowerGrabMapWith50StationsReturned() throws IOException {
        PowerGrabMap powerGrabMap = PowerGrabMap.getMap(LocalDate.of( 2019,1, 1));
        assertCorrectAndNumStations50(powerGrabMap, 2019, 1, 1);
    }

    @Test
    public void ifProvideFileObjectToGetMap_thenPowerGrabMapWith50StationsReturned() throws IOException {
        PowerGrabMap powerGrabMap = PowerGrabMap.getMap(new File("/Users/mclancy/IdeaProjects/PowerGrab/.json_cache/2019/01/01/powergrabmap.geojson"));
        assertCorrectAndNumStations50(powerGrabMap, 2019, 1, 1);
    }

    @Test
    public void ifProvideURLObjectToGetMap_thenPowerGrabWith50StationReturned() throws IOException {
        PowerGrabMap powerGrabMap = PowerGrabMap.getMap(new URL("http://homepages.inf.ed.ac.uk/stg/powergrab/2019/01/01/powergrabmap.geojson"));
        assertCorrectAndNumStations50(powerGrabMap, 2019, 1, 1);
    }

    private void assertCorrectAndNumStations50(PowerGrabMap powerGrabMap, int year, int month, int day) {
        assert powerGrabMap.getDateGenerated().equals(LocalDate.of(year, month, day));
        assert powerGrabMap.getNumStations() == 50;
    }

    @Test
    public void ifAgentInRangeOfStation_thenNearestStationIsReturned() {
       assert powerGrabMap.getNearestStationIfWithinRange(inBoundsAgent).equals(expectedStation);
    }

    @Test(expected = NoSuchElementException.class)
    public void ifAgentIsNotInRangeToClosestStation_thenNoSuchElementExceptionIsThrown(){
        powerGrabMap.getNearestStationIfWithinRange(outOfBoundsAgent);

    }
}

