package uk.ac.ed.inf.powergrab;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.sun.javafx.UnmodifiableArrayList;
import jdk.nashorn.internal.parser.JSONParser;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

@JsonDeserialize(using = StationCollection.StationCollectionDeserialiser.class)
public class StationCollection {

    public static final double LATMIN = 55.942617;
    public static final double LATMAX = 55.946233;
    public static final double LONGMIN = -3.192473;
    public static final double LONGMAX = -3.184319;

    public static final Rectangle2D.Double playArea = new Rectangle2D.Double(LATMIN, LONGMIN, LATMAX - LATMIN, LONGMAX - LONGMIN);

    public List<Station> stationList;

    public StationCollection(List<Station> stationList) {
        this.stationList = Collections.unmodifiableList(stationList);
    }

    public static class StationCollectionDeserialiser extends StdDeserializer<StationCollection> {

        public StationCollectionDeserialiser(){
            this(null);
        }

        private StationCollectionDeserialiser(Class<?> vc) {
            super(vc);
        }

        @Override
        public StationCollection deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
            List<Station> stationList = mapper.readValue(jsonParser, new TypeReference<List<Station>>(){});

            return new StationCollection(stationList);
        }
    }
}
