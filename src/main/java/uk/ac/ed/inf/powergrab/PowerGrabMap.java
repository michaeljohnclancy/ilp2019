package uk.ac.ed.inf.powergrab;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@JsonDeserialize(using = PowerGrabMap.PowerGrabMapDeserializer.class)
public class PowerGrabMap {

    private static final double LATMIN = 55.942617;
    private static final double LATMAX = 55.946233;
    private static final double LONGMIN = -3.192473;
    private static final double LONGMAX = -3.184319;

    public static final Rectangle2D.Double playArea = new Rectangle2D.Double(LATMIN, LONGMIN, LATMAX - LATMIN, LONGMAX - LONGMIN);


    private LocalDate dateGenerated;
    private List<Station> stationList;
    private Comparator<Position> positionComparator;

    /**
     * Returns a PowerGrabMap given a date.
     * @param date
     * @return new PowerGrabMap instance.
     * @throws IOException
     */
    public static PowerGrabMap getMap(LocalDate date) throws IOException {
        return getMap(date.getDayOfMonth(), date.getMonth().getValue(), date.getYear());
    }

    /**
     * Returns a PowerGrabMap given a day, month and year.
     * @param day Day in the month
     * @param month Month in the year
     * @param year Year
     * @return new PowerGrabMap instance.
     * @throws IOException
     */
    public static PowerGrabMap getMap(int day, int month, int year) throws IOException {
        return getMap(
                new URL(
                        String.format("http://homepages.inf.ed.ac.uk/stg/powergrab/%s/%02d/%02d/powergrabmap.geojson", year, month, day)
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
        this.stationList = Collections.unmodifiableList(stationList);

        positionComparator = new Comparators.EuclideanComparator();
    }

    private void setPositionComparator(Comparator<Position> comparator){
        this.positionComparator = comparator;
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
     * This method returns the closes station to a given entity.
     * It does this by finding the minimum distance using the specified positionComparator (by default, EuclideanComparator).
     *
     * @param position Some Position
     * @return
     */
    public Optional<Station> getNearestStation(Position position){
        return stationList.stream().
                min((station, x) -> positionComparator.compare(position, station.getPosition()));
    }

    public static class PowerGrabMapDeserializer extends StdDeserializer<PowerGrabMap>{

        private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("E MMM dd yyyy");

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
                            new TypeReference<List<Station>>() {}
                            ),
                    LocalDate.parse(jsonNode.get("date-generated").asText(), dateFormatter)
            );
        }
    }
}
