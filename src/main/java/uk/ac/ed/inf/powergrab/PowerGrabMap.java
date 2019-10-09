package uk.ac.ed.inf.powergrab;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import javafx.util.Pair;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

@JsonDeserialize(using = PowerGrabMap.PowerGrabMapDeserializer.class)
public class PowerGrabMap {

    private static final double LATMIN = 55.942617;
    private static final double LATMAX = 55.946233;
    private static final double LONGMIN = -3.192473;
    private static final double LONGMAX = -3.184319;

    public static final Rectangle2D.Double playArea = new Rectangle2D.Double(LATMIN, LONGMIN, LATMAX - LATMIN, LONGMAX - LONGMIN);

    private LocalDate dateGenerated;
    private List<Station> stationList;

    /**
     * Returns a PowerGrabMap given a day, month and year.
     * @return new PowerGrabMap instance.
     * @throws IOException
     */
    public static PowerGrabMap getMap(LocalDate date) throws IOException {
        return getMap(
                new URL(
                        String.format("http://homepages.inf.ed.ac.uk/stg/powergrab/%s/%02d/%02d/powergrabmap.geojson", date.getYear(), date.getMonth().getValue(), date.getDayOfMonth())
                )
        );
    }

    /**
     * Returns a PowerGrabMap given a URL object.
     * @param url URL href.
     * @return new PowerGrabMap instance.
     * @throws IOException
     */
    public static PowerGrabMap getMap(URL url) throws IOException {
        return new ObjectMapper().readValue(url, PowerGrabMap.class);
    }

    /**
     * Returns a PowerGrabMap given a File object.
     * @param file
     * @return new PowerGrabMap instance.
     * @throws IOException
     */
    public static PowerGrabMap getMap(File file) throws IOException {
        return new ObjectMapper().readValue(file, PowerGrabMap.class);
    }

    private PowerGrabMap(List<Station> stationList, LocalDate dateGenerated){
        this.dateGenerated = dateGenerated;
        this.stationList = stationList;
    }

    public LocalDate getDateGenerated(){
        return dateGenerated;
    }

    /**
     * Returns the station at index i.
     * @param i
     * @return new Station instance
     */
    public Station getStation(int i){
        return stationList.get(i);
    }

    /**
     * Returns all stations
     * @return List of Stations
     */
    public List<Station> getStations(){
        return stationList;
    }

    public int getNumStations(){
        return stationList.size();
    }

    /**
     * This method returns the nearest station to a given agent if within the interaction range,
     * by calling getListSortedByDistanceFrom and getting the first element in that list.
     * @param agent Agent to calculate distance from
     * @return Optional returns a station or Optional.empty() if not within range.
     */
    public void transferFundsIfNearestStationInRange(Agent agent) throws NoSuchElementException {
        getStreamOfPairsSortedByDistanceFrom(agent.getPosition())
                .filter(stationDoublePair -> stationDoublePair.getValue() < Station.INTERACTION_RANGE)
                .map(Pair::getKey)
                .findFirst()
                .ifPresent(station -> station.transferPowerTo(agent));
    }

    /**
     * This method returns a stream of Stations, sorted by distance from the given entity
     * @param entity Entity to calculate distance from
     * @return Station stream
     */
    public Stream<Pair<Station, Double>> getStreamOfPairsSortedByDistanceFrom(Position position){
        return stationList.stream()
                .map(station -> new Pair<>(station, getEuclideanDistance(position, station.getPosition())))
                .sorted(Comparator.comparing(Pair::getValue)
                );
    }

    public static double getEuclideanDistance(Position p1, Position p2){
        return Math.sqrt(
                Math.pow(p2.latitude - p1.latitude, 2)
                        + Math.pow(p2.longitude - p1.longitude, 2)
        );
    }

    public static class PowerGrabMapSerializer extends JsonSerializer<PowerGrabMap>{

        @Override
        public void serialize(PowerGrabMap powerGrabMap, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {


        }
    }

    public static class PowerGrabMapDeserializer extends StdDeserializer<PowerGrabMap>{

        public PowerGrabMapDeserializer(){
            this(null);
        }

        protected PowerGrabMapDeserializer(Class<?> vc) {
            super(vc);
        }

        /**
         * This method is used by the jackson ObjectMapper to deserialize a given json file.
         * It finds the features ArrayNode of the JSON structure, and deserializes each one according to the StationDeserializer.
         *
         * An example of using this:
         * PowerGrabMap pgMap = new ObjectMapper().readValue(SOME_JSON_STRING_OR_URL_OR_FILE, PowerGrabMap.class);
         *
         * The annotation at the top of the PowerGrabMap class tells jackson to deserialize according to this method.
         * @param jsonParser
         * @param deserializationContext
         * @return new PowerGrabMap instance.
         * @throws IOException
         */
        @Override
        public PowerGrabMap deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException{
            JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
            return new PowerGrabMap(
                    new ObjectMapper().readValue(
                            jsonNode.get("features").toString(),
                            new TypeReference<List<Station>>() {}),
                    LocalDate.parse(jsonNode.get("date-generated").asText(), Environment.dateFormatter)
            );
        }
    }
}
