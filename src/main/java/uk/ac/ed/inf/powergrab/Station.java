package uk.ac.ed.inf.powergrab;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

import static java.lang.Double.max;

@JsonDeserialize(using = Station.StationDeserializer.class)
public class Station extends Entity{

    Station(StationBuilder builder) {
        super(builder);
    }

    @Override
    public void setPower(double power) {
        this.power = power;
    }

    @Override
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void transferBalance(Agent agent){
        double oldAgentBalance = agent.getBalance();
        agent.setBalance(max(0.0, agent.getBalance() + getBalance()));
        setBalance(getBalance() - (agent.getBalance() - oldAgentBalance));
    }

    public void transferPower(Agent agent){
        double oldAgentPower = agent.getPower();
        agent.setPower(max(0.0, agent.getPower() + getPower()));
        setPower(getPower() - (agent.getPower() - oldAgentPower));
    }

    public static final class StationBuilder extends Entity.GenericEntityBuilder<StationBuilder>{

        @Override
        public Station build() {
            return new Station(this);
        }
    }

    public static class StationDeserializer extends StdDeserializer<Station>{

        public StationDeserializer(){
            this(null);
        }

        private StationDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public Station deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);

            JsonNode properties = jsonNode.get("properties");
            double coins = Double.parseDouble(properties.get("coins").textValue());
            double power = Double.parseDouble(properties.get("power").textValue());

            JsonNode coordinates = jsonNode.get("geometry").get("coordinates");
            double latitude = Double.parseDouble(coordinates.get(0).toString());
            double longitude = Double.parseDouble(coordinates.get(1).toString());

            return new Station.StationBuilder()
                        .setBalance(coins)
                        .setPower(power)
                        .setPosition(latitude, longitude)
                        .build();
        }
    }
}
