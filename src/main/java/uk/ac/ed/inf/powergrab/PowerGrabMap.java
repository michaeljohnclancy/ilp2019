package uk.ac.ed.inf.powergrab;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@JsonDeserialize(using = PowerGrabMap.PowerGrabMapDeserializer.class)
public class PowerGrabMap {

    private LocalDate dateGenerated;
    private List<Station> stationList;
    private Comparator<Position> positionComparator;

    public static PowerGrabMap getMap(LocalDate date) throws IOException {
        return getMap(date.getDayOfMonth(), date.getMonth().getValue(), date.getYear());
    }

    public static PowerGrabMap getMap(int day, int month, int year) throws IOException {
        return getMap(
                new URL(
                        String.format("http://homepages.inf.ed.ac.uk/stg/powergrab/%01d/%01d/%s/powergrabmap.geojson", year, month, day)
                )
        );
    }

    private static PowerGrabMap getMap(URL url) throws IOException {
        return new ObjectMapper().readValue(url, PowerGrabMap.class);
    }

    private static PowerGrabMap getMap(File file) throws IOException {
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

    public Station getStation(int i){
        return stationList.get(i);
    }

    public List<Station> getStations(){
        return stationList;
    }

    public int getNumStations(){
        return stationList.size();
    }

    public Optional<Station> getNearestStation(Entity entity){
        return stationList.stream().
                min((station, x) -> positionComparator.compare(entity.getPosition(), station.getPosition()));
    }

    public static class PowerGrabMapDeserializer extends StdDeserializer<PowerGrabMap>{

        private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("E MMM dd yyyy");

        public PowerGrabMapDeserializer(){
            this(null);
        }

        protected PowerGrabMapDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public PowerGrabMap deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException{
            JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
            return new PowerGrabMap(
                    new ObjectMapper().readValue(
                            jsonNode.get("features").toString(),
                            new TypeReference<List<Station>>() {}
                            ),
                    LocalDate.parse(jsonNode.get("date-generated").toString(), dateFormatter)
            );
        }
    }
}
