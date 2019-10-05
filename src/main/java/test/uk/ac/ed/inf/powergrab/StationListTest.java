package test.uk.ac.ed.inf.powergrab;

import org.junit.Test;
import uk.ac.ed.inf.powergrab.Station;
import uk.ac.ed.inf.powergrab.StationList;

import java.io.IOException;
import java.util.ArrayList;

public class StationListTest {

    @Test
    public void readStationListFromJson() throws IOException {

        StationList stationList = StationList.fromFile("/Users/mclancy/IdeaProjects/PowerGrab/.json_cache/2019/01/01/powergrabmap.geojson");

        //StationList list = (StationList) new ArrayList<Station>();
    }

    @Test
    public void readStationListFromURL() throws IOException {

        StationList stationList = StationList.fromUrl("http://homepages.inf.ed.ac.uk/stg/powergrab/2020/01/01/powergrabmap.geojson");

    }
}

