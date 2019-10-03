package uk.ac.ed.inf.powergrab;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

import static java.lang.Double.max;
import static java.lang.Double.min;

@JsonDeserialize(using = Station.StationDeserializer.class)
public class Station {

    private final Position position;
    private double balance;
    private double power;

    private Station(StationBuilder builder){
        this.position = builder.position;
        this.balance = builder.balance;
        this.power = builder.power;
    }

    public void transferBalance(Agent agent){
        double newBalance = balance - (255 - agent.getBalance());
        agent.setBalance(agent.getBalance()+balance);
        balance = newBalance;

        double totalBalance = balance + agent.getBalance();
        balance = max(0, totalBalance - 255.0);
        agent.setBalance(min(255.0, totalBalance));
    }

    private static class StationBuilder{

        private double latitude;
        private double longitude;
        private double balance;
        private double power;

        private Position position;

        private StationBuilder setLatitude(double latitude){
            this.latitude = latitude;
            return this;
        }

        private StationBuilder setLongitude(double longitude){
            this.longitude = longitude;
            return this;
        }

        private StationBuilder setBalance(double balance){
            this.balance = balance;
            return this;
        }

        private StationBuilder setPower(double power){
            this.power = power;
            return this;
        }

        private Station build(){
            this.position = new Position(latitude, longitude);
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
            double coins = (double) properties.get("coins").numberValue();
            double power = (double) properties.get("power").numberValue();

            JsonNode coordinates = jsonNode.get("geometry").get("coordinates");
            double latitude = (double) coordinates.get("latitude").numberValue();
            double longitude = (double) coordinates.get("longitude").numberValue();

            return new Station.StationBuilder()
                        .setBalance(coins)
                        .setPower(power)
                        .setLatitude(latitude)
                        .setLongitude(longitude)
                        .build();
        }
    }
}
