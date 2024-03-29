package uk.ac.ed.inf.powergrab;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import javafx.util.Pair;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static uk.ac.ed.inf.powergrab.Environment.objectMapper;

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
        return objectMapper.readValue(url, PowerGrabMap.class);
    }

    /**
     * Returns a PowerGrabMap given a File object.
     * @param file
     * @return new PowerGrabMap instance.
     * @throws IOException
     */
    public static PowerGrabMap getMap(File file) throws IOException {
        return objectMapper.readValue(file, PowerGrabMap.class);
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
     * This method transfers the nearest stations resources to a given agent, provided that the station is in the interaction range.
      * @param agent
     */
    public void transferFundsIfNearestStationInRange(Agent agent){
            getNearestStationDistancePairIfInRange(agent.getPosition())
                    .ifPresent(stationDoublePair -> stationDoublePair.getKey().transferResourcesTo(agent));
    }

    public Optional<Pair<Station, Double>> getNearestStationDistancePairIfInRange(Position position){
        return getStationDistancePairStream(position)
                .filter(stationDoublePair -> stationDoublePair.getValue() < Station.INTERACTION_RANGE)
                .min(Comparator.comparing(Pair::getValue));
    }

    /**
     * This method returns a stream of Station Distance pairs, where the distance is the Euclidean distance
     * away from the given position.
     * @param position Position reference point for the distances.
     * @return A stream of pairs of stations and their respective distances away from the specified position.
     */
    private Stream<Pair<Station, Double>> getStationDistancePairStream(Position position){
        return stationList.stream()
                .map(station -> new Pair<>(station, getEuclideanDistance(position, station.getPosition())));
    }

    /**
     * <p>This method returns a sorted list of Station Distance pairs. The list is sorted by distance in ascending order.</p>
     * <p>See getStationDistancePairStream.</p>
     * @param position Position reference point for the distances.
     * @return Sorted list of Station Distance pairs.
     */
    public List<Pair<Station, Double>> getSortedStationDistancePairs(Position position){
        return getStationDistancePairStream(position)
                .sorted(Comparator.comparing(Pair::getValue))
                .collect(Collectors.toList());
    }

    public static double getEuclideanDistance(Position p1, Position p2){
        return Math.sqrt(
                Math.pow(p2.latitude - p1.latitude, 2)
                        + Math.pow(p2.longitude - p1.longitude, 2)
        );
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
                    objectMapper.readValue(
                            jsonNode.get("features").toString(),
                            new TypeReference<List<Station>>() {}),
                    LocalDate.parse(jsonNode.get("date-generated").asText(), Environment.dateFormatter)
            );
        }
    }
}
