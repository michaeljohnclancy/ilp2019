package uk.ac.ed.inf.powergrab;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

@JsonDeserialize(using = StationList.StationCollectionDeserializer.class)
public class StationList {

    private List<Station> stationList;

    private StationList(List<Station> stationList){
        this.stationList = Collections.unmodifiableList(stationList);
    }

    public Station get(int i){
        return stationList.get(i);
    }

    public int size(){
        return stationList.size();
    }

    public Optional<Station> getNearestStation(Agent agent){
        return stationList.stream().
                min((station, x) -> new PositionComparator().compare(agent.getPosition(), station.getPosition()));
    }

    public static StationList fromUrl(String url) throws IOException {
        return fromUrl(new URL(url));
    }

    private static StationList fromUrl(URL url) throws IOException {
        return new ObjectMapper().readValue(url, StationList.class);
    }

    public static StationList fromFile(String filePath) throws IOException {
        return fromFile(new File(filePath));
    }

    public static StationList fromFile(File file) throws IOException {
        return new ObjectMapper().readValue(file, StationList.class);
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
                    jsonNode.get("features").toString(),
                    new TypeReference<List<Station>>() {}
                    )
            );
        }
    }
}
