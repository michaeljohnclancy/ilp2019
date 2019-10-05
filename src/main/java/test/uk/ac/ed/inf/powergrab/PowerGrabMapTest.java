package test.uk.ac.ed.inf.powergrab;

import org.junit.Test;
import uk.ac.ed.inf.powergrab.PowerGrabMap;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

public class PowerGrabMapTest {

    @Test
    public void ifProvideLocalDateToGetMap_thenPowerGrabMapWith50StationsReturned() throws IOException {

        PowerGrabMap powerGrabMap = PowerGrabMap.getMap(1, 1, 2019);
        assertCorrectAndNumStations50(powerGrabMap, 1, 1, 2019);
    }

    @Test
    public void ifProvideFileObjectToGetMap_thenPowerGrabMapWith50StationsReturned() throws IOException {

        PowerGrabMap powerGrabMap = PowerGrabMap.getMap(new File("/Users/mclancy/IdeaProjects/PowerGrab/.json_cache/2019/01/01/powergrabmap.geojson"));
        assertCorrectAndNumStations50(powerGrabMap, 1, 1, 2019);
    }

    @Test
    public void ifProvideURLObjectToGetMap_thenPowerGrabWith50StationReturned() throws IOException {

        PowerGrabMap powerGrabMap = PowerGrabMap.getMap(new URL("http://homepages.inf.ed.ac.uk/stg/powergrab/2019/01/01/powergrabmap.geojson"));
        assertCorrectAndNumStations50(powerGrabMap, 1, 1, 2019);
    }

    private void assertCorrectAndNumStations50(PowerGrabMap powerGrabMap, int day, int month, int year) {
        assert powerGrabMap.getDateGenerated().equals(LocalDate.of(year, month, day));
        assert powerGrabMap.getNumStations() == 50;
    }

}

