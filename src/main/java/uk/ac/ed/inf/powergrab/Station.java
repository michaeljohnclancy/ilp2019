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

    public void transferBalance(Agent agent){
        double oldAgentBalance = agent.getBalance();
        agent.setBalance(max(0.0, agent.getBalance() + balance));
        balance -= (agent.getBalance() - oldAgentBalance);
    }

    public void transferPower(Agent agent){
        double oldAgentPower = agent.getPower();
        agent.setPower(max(0.0, agent.getPower() + power));
        power -= (agent.getPower() - oldAgentPower);
    }

    @Override
    public void setPower(double power) {
        this.power = power;
    }

    @Override
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public static final class StationBuilder extends Entity.GenericEntityBuilder<StationBuilder>{

        @Override
        public Station build() {
            return new Station(this);
        }
    }

    public static class StationDeserializer extends StdDeserializer<Station>{

        private StationDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public Station deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);

            JsonNode properties = jsonNode.get("properties");
            double coins = (double) properties.get("coins").numberValue();
            double power = (double) properties.get("power").numberValue();

            JsonNode coordinates = jsonNode.get("geometry").get("coordinates");
            double latitude = (double) coordinates.get("latitude").numberValue();
            double longitude = (double) coordinates.get("longitude").numberValue();

            return new Station.StationBuilder()
                        .setBalance(coins)
                        .setPower(power)
                        .setPosition(latitude, longitude)
                        .build();
        }
    }
}
