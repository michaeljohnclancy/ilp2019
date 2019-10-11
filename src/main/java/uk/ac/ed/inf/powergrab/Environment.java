package uk.ac.ed.inf.powergrab;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@JsonSerialize(using = Environment.EnvironmentSerializer.class)
public class Environment {

    public static DateTimeFormatter dateFormatter = java.time.format.DateTimeFormatter.ofPattern("E MMM dd yyyy");
    public static ObjectMapper objectMapper = new ObjectMapper();
    public static Random randomGenerator = new Random(12345);

    private Agent agent;
    private PowerGrabMap powerGrabMap;

    public Environment(Agent agent, PowerGrabMap powerGrabMap) {
        this.agent = agent;
        this.powerGrabMap = powerGrabMap;
    }

    public void step(int numSteps){
        int i = 0;
        while (i < numSteps && agent.power > 0){
           step();
           i++;
        }
    }

    public void step(){
        agent.moveAndGatherResources(powerGrabMap);
    }

    public void toJson(File file) throws IOException {
        objectMapper.writeValue(file, this);
    }

    public Agent getAgent() {
        return agent;
    }

    public PowerGrabMap getMap(){
        return powerGrabMap;
    }

    public static final class EnvironmentSerializer extends JsonSerializer<Environment>{

        @Override
        public void serialize(Environment environment, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException{
            jsonGenerator.writeObjectField("type", "FeatureCollection");
            jsonGenerator.writeObjectField("date_generated", environment.powerGrabMap.getDateGenerated().format(dateFormatter));

            jsonGenerator.writeArrayFieldStart("features");

            jsonGenerator.writeObject(environment.getAgent().getFlightPath());

            for (Station station : environment.powerGrabMap.getStations()) {
                jsonGenerator.writeObject(station);
            }

            jsonGenerator.writeEndArray();
        }
    }
}
