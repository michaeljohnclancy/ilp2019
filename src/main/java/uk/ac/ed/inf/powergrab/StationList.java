package uk.ac.ed.inf.powergrab;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.*;

@JsonDeserialize(using = StationList.StationCollectionDeserializer.class)
public class StationList {

    private List<Station> stationList;

    public StationList(List<Station> stationList){
        this.stationList = stationList;
    }

    public static StationList fromFile(String filePath) throws IOException {
        return fromFile(new File(filePath));
    }

    public static StationList fromFile(File file) throws IOException {
        return new ObjectMapper().readValue(file, StationList.class);
    }

    public Optional<Station> getNearestStation(Agent agent){
        return stationList.stream().
                min((station, x) -> new PositionComparator().compare(agent.getPosition(), station.getPosition()));
    }

    private static class PositionComparator implements Comparator<Position>{
        public int compare(final Position p1, final Position p2){
            return (int) Math.sqrt(
                    Math.pow(p2.latitude - p1.latitude, 2)
                            + Math.pow(p2.longitude - p1.longitude, 2)
            );
        }
    }

    public static class StationCollectionDeserializer extends StdDeserializer<StationList>{

        public StationCollectionDeserializer(){
            this(null);
        }

        protected StationCollectionDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public StationList deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException{
            JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
            return new StationList(new ObjectMapper().readValue(
                    jsonNode.get("features").toString(), new TypeReference<List<Station>>() {}
                    ));
        }
    }
}
