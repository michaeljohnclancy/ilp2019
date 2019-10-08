package uk.ac.ed.inf.powergrab;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@JsonSerialize(using = Environment.EnvironmentSerializer.class)
public class Environment {

    public static DateTimeFormatter dateFormatter = java.time.format.DateTimeFormatter.ofPattern("E MMM dd yyyy");
    public static ObjectMapper objectMapper;

    private final List<Agent> agents;
    private final PowerGrabMap powerGrabMap;

    public Environment(List<Agent> agents, PowerGrabMap powerGrabMap) {
        this.agents = agents;
        this.powerGrabMap = powerGrabMap;
    }

    public void step(){
        agents.forEach(powerGrabMap::transferFundsIfNearestStationInRange);
    }

    public void writeToJSON(File file) throws IOException {
        objectMapper.writeValue(file, this);
    }

    public List<Agent> getAgents(){
        return agents;
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
            for (Station station : environment.powerGrabMap.getStations()) {
                jsonGenerator.writeObject(station);
            }
            for (Agent agent : environment.getAgents()){
                jsonGenerator.writeObject(agent.getFlightPath());
            }
            jsonGenerator.writeEndObject();
        }
    }

}
