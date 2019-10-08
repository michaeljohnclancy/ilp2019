package uk.ac.ed.inf.powergrab;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;

public class FlightPath {

    private List<Position> positions;

    private static class FlightPathSerializer extends JsonSerializer<List<Position>>{

        @Override
        public void serialize(List<Position> positions, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeObjectField("Type", "Feature");
            jsonGenerator.writeOmittedField("properties");
            jsonGenerator.writeObjectFieldStart("geometry");
            jsonGenerator.writeObjectField("type", "LineString");
            jsonGenerator.writeObjectField("coordinates", positions);
            jsonGenerator.writeEndObject();
            jsonGenerator.writeEndObject();

        }
    }
}
