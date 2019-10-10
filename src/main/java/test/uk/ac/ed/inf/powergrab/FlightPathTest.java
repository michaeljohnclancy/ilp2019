package test.uk.ac.ed.inf.powergrab;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ed.inf.powergrab.Direction;
import uk.ac.ed.inf.powergrab.FlightPath;
import uk.ac.ed.inf.powergrab.Position;

import static uk.ac.ed.inf.powergrab.Environment.objectMapper;

public class FlightPathTest {

    private FlightPath flightPath;
    private Position p1;

    @Before
    public void before(){
        flightPath = new FlightPath();
        p1 = new Position(55.944425,-3.188396);
        flightPath.push(p1);

    }

    @Test
    public void ifNewPositionPushedToFlightPath_thenPositionAtHead(){
        assert p1.equals(flightPath.head());
    }

    @Test
    public void ifExamplePositionPushedToFlightPath_thenExpectedJsonIsProduced() throws JsonProcessingException {

        String expectedJson = "{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[-3.188396,55.944425],[-3.1882811949702905,55.944147836140246]]}}";

        flightPath.push(p1.nextPosition(Direction.SSE));
        String actualJson = objectMapper.writeValueAsString(flightPath);

        Assert.assertEquals(actualJson, expectedJson);
    }
}
