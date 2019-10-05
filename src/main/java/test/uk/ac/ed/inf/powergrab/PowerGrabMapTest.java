package test.uk.ac.ed.inf.powergrab;

import org.junit.Test;
import uk.ac.ed.inf.powergrab.PowerGrabMap;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class PowerGrabMapTest {

    @Test
    public void readStationListFromJson() throws IOException {

        PowerGrabMap powerGrabMap = PowerGrabMap.getMap(new File("/Users/mclancy/IdeaProjects/PowerGrab/.json_cache/2019/01/01/powergrabmap.geojson"));

        //StationList list = (StationList) new ArrayList<Station>();
    }

    @Test
    public void readStationListFromURL() throws IOException {

        PowerGrabMap powerGrabMap = PowerGrabMap.getMap(new URL("http://homepages.inf.ed.ac.uk/stg/powergrab/2020/01/01/powergrabmap.geojson"));

    }
}

