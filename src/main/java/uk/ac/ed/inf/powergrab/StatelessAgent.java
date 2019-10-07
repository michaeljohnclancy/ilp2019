package uk.ac.ed.inf.powergrab;

public class StatelessAgent extends Agent {

    StatelessAgent(GenericEntityBuilder builder) {
        super(builder);
    }

    @Override
    Direction getDirectionEstimate(Environment environment) {
        return null;

    }


}
