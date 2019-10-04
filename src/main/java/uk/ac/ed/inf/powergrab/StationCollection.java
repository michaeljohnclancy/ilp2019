package uk.ac.ed.inf.powergrab;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

    private static class PositionComparator implements Comparator<Position>{
        public int compare(final Position p1, final Position p2){
            return (int) Math.sqrt(
                    Math.pow(p2.latitude - p1.latitude, 2)
                            + Math.pow(p2.longitude - p1.longitude, 2)
            );
        }
    }

    public Optional<Station> getNearestStation(Agent agent){
        return stationList.stream().
                min((station, x) -> new PositionComparator().compare(agent.getPosition(), station.getPosition()));
    }

    public static class StationCollectionDeserializer extends StdDeserializer<StationCollection>{

        protected StationCollectionDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public StationCollection deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException{
            JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);

            return new StationCollection(
                    new ObjectMapper().readValue(
                            jsonNode.get("features").toString(), new TypeReference<List<Station>>(){}
                            )
            );

        }
    }
}
