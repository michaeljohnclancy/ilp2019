package uk.ac.ed.inf.powergrab;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

import static java.lang.Double.max;

/**
 * This class represents a single station. The constructor is called in the builder, and the builder is used in the deserializer.
 * Json representations of Stations are deserialized according to the deserialize method in StationDeserializer.
 *
 * This deserializer is called by the PowerGrabMap deserializer, which returns a new PowerGrabMap object,
 * containing 50 of these Station objects.
 */
@JsonDeserialize(using = Station.StationDeserializer.class)
public class Station extends Entity{

    public static final double INTERACTION_RANGE = 0.00025;

    private Station(StationBuilder builder) {
        super(builder);
    }

    @Override
    void setPower(double power) {
        this.power = power;
    }

    @Override
    void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * This method will transfer the appropriate monetary balance from this station to a given agent.
     * @param agent Some agent
     */
    public void transferBalanceTo(Agent agent){
        double oldAgentBalance = agent.getBalance();
        agent.setBalance(max(0.0, agent.getBalance() + getBalance()));
        setBalance(getBalance() - (agent.getBalance() - oldAgentBalance));
    }

    /**
     * This method will transfer the appropriate power from this station to a given agent.
     * @param agent Some agent
     */
    public void transferPowerTo(Agent agent){
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

        /**
         * This method parses an individual station JSON structure. It is passed these chunks by the PowerGrabMapDeserializer.
         *
         * @param jsonParser
         * @param deserializationContext
         * @return Station
         * @throws IllegalArgumentException
         * @throws IOException
         */
        @Override
        public Station deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IllegalArgumentException, IOException {
            JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);

            JsonNode properties = jsonNode.get("properties");
            double coins = Double.parseDouble(properties.get("coins").textValue());
            double power = Double.parseDouble(properties.get("power").textValue());

            JsonNode coordinates = jsonNode.get("geometry").get("coordinates");
            double latitude = Double.parseDouble(coordinates.get(1).toString());
            double longitude = Double.parseDouble(coordinates.get(0).toString());

            return new Station.StationBuilder()
                        .setBalance(coins)
                        .setPower(power)
                        .setPosition(latitude, longitude)
                        .build();
        }
    }
}
