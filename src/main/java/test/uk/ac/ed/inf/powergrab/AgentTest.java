package test.uk.ac.ed.inf.powergrab;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ed.inf.powergrab.*;

public class AgentTest {

    private StatelessAgent agent0;

    @Before
    public void before(){
        agent0 = new StatelessAgent(
                "agent0",
                new Position(55.944425,-3.188396)
        );
    }

    @Test
    public void ifAgentCreated_thenBalanceIs0_andPowerIs250(){
        StationTest.assertDoubleEquals(agent0.getBalance(), 0.0);
        StationTest.assertDoubleEquals(agent0.getPower(), 250.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ifNegativeBalanceIsSet_thenIllegalArgumentExceptionThrown(){
        agent0.setPower(0);
    }

    @Test
    public void ifAgentMovesSouthThenNorth_thenAll3LocationsInFlightPath(){

        Position p1 = new Position(55.944425,-3.188396);
        FlightPath expectedFlightPath = new FlightPath();

        expectedFlightPath.push(p1);
        expectedFlightPath.push(p1.nextPosition(Direction.S));
        expectedFlightPath.push(p1.nextPosition(Direction.S).nextPosition(Direction.N));

        agent0.move(Direction.S);
        agent0.move(Direction.N);

        assert agent0.getFlightPath().equals(expectedFlightPath);
    }
}
