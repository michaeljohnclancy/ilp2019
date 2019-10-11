package uk.ac.ed.inf.powergrab;

import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

public class PowerGrabMapTest {

    private Station station;
    private Agent inBoundsAgent;
    private Agent outOfBoundsAgent;
    private PowerGrabMap powerGrabMap;

    @Before
    public void before() throws IOException {
        station = new Station(
                "05e1-42fc-54e6-663b-6336-2659",
                new Position(55.94386776638809, -3.1864088755508475),
                79.57136572604678,
                31.66170339928509
        ) ;

        inBoundsAgent = new StatelessAgent(
                "agent0",
                new Position(55.94387776638809, -3.1864088755508475)
        );

        outOfBoundsAgent = new StatelessAgent(
                "agent1",
                new Position(55.94356776638809, -3.1864088755508475)
        );

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
    public void ifAgentInRangeOfStation_thenCorrectStationIsSelected(){
        List<Pair<Station, Double>> orderedStationsByDistance = powerGrabMap.getSortedStationDistancePairs(inBoundsAgent.getPosition());
        assert station.equals(
                orderedStationsByDistance.get(0).getKey()
        );
    }

    @Test
    public void ifAgentInRangeOfStation_thenStationBalanceAndPowerTransferredToAgent() {
        powerGrabMap.transferFundsIfNearestStationInRange(inBoundsAgent);
        assert inBoundsAgent.getPower() == 115.07136572604678;
        assert inBoundsAgent.getCoins() == 46.66170339928509;
    }

//    @Test(expected = NoSuchElementException.class)
//    public void ifAgentIsNotInRangeToClosestStation_thenNoSuchElementExceptionIsThrown(){
//        powerGrabMap.getNearestStationIfWithinRange(outOfBoundsAgent);
//
//    }
}

