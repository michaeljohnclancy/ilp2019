package uk.ac.ed.inf.powergrab;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;
import java.util.Stack;

@JsonSerialize(using = FlightPath.FlightPathSerializer.class)
public class FlightPath {

    private Stack<Position> positions;

    public FlightPath(){
        positions = new Stack<>();
    }

    public Position head(){
        return positions.peek();
    }

    public void push(Position position){
        positions.push(position);
    }

    public boolean equals(FlightPath flightPath){
        return positions.equals(flightPath.positions);
    }

     public static class FlightPathSerializer extends JsonSerializer<FlightPath>{

        @Override
        public void serialize(FlightPath flightPath, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeStartObject();

            jsonGenerator.writeStringField("type", "Feature");

            jsonGenerator.writeObjectFieldStart("properties");
            jsonGenerator.writeEndObject();

            jsonGenerator.writeObjectFieldStart("geometry");
            jsonGenerator.writeStringField("type", "LineString");
            jsonGenerator.writeArrayFieldStart("coordinates");
            for (Position position : flightPath.positions) {
                jsonGenerator.writeArray(position.toReversedArray(), 0, 2);
            }
            jsonGenerator.writeEndArray();
            jsonGenerator.writeEndObject();

            jsonGenerator.writeEndObject();
        }
    }
}
