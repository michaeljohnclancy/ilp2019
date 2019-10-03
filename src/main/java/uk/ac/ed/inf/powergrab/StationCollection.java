package uk.ac.ed.inf.powergrab;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class StationCollection {

    private static final double LATMIN = 55.942617;
    private static final double LATMAX = 55.946233;
    private static final double LONGMIN = -3.192473;
    private static final double LONGMAX = -3.184319;

    public static final Rectangle2D.Double playArea = new Rectangle2D.Double(LATMIN, LONGMIN, LATMAX - LATMIN, LONGMAX - LONGMIN);

    private List<Station> stationList;

    public StationCollection(String filePath) throws IOException{
        this(new File(filePath));
    }

    public StationCollection(File file) throws IOException {
        this(new ObjectMapper().readValue(file, new TypeReference<List<Station>>(){}));
    }

    public StationCollection(List<Station> stationList) {
        this.stationList = Collections.unmodifiableList(stationList);
    }

    public List<Station> getStationList() {
        return this.stationList;
    }
}
