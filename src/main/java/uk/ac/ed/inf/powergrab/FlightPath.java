package uk.ac.ed.inf.powergrab;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class FlightPath {

    private Stack<Position> positions;

    public Position peek(){
        return positions.peek();
    }

    public void push(Position position){
        positions.push(position);
    }

    public ArrayList<Position> getPositions(){
        return new ArrayList<>(positions);
    }

    private static class FlightPathSerializer extends JsonSerializer<FlightPath>{

        @Override
        public void serialize(FlightPath flightPath, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeObjectField("type", "Feature");
            jsonGenerator.writeOmittedField("properties");
            jsonGenerator.writeObjectFieldStart("geometry");
            jsonGenerator.writeObjectField("type", "LineString");
            jsonGenerator.writeObjectField("coordinates", flightPath.getPositions());
            jsonGenerator.writeEndObject();
            jsonGenerator.writeEndObject();

        }
    }
}
