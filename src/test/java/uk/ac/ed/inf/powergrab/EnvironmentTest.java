package uk.ac.ed.inf.powergrab;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;

public class EnvironmentTest {

    private Agent agent;
    private PowerGrabMap powerGrabMap;
    private Environment environment;

    @Before
    public void before() throws IOException {

        agent = new StatelessAgent(
                "agent0",
                new Position(55.94387776638809, -3.1864088755508475)
        );

        powerGrabMap = PowerGrabMap.getMap(LocalDate.of( 2019,1, 1));

        environment = new Environment(agent, powerGrabMap);
    }

    @Test
    public void stepTest(){
        environment.step(250);
    }
}
