package uk.ac.ed.inf.powergrab;

public class StatelessAgent extends Agent {

    public StatelessAgent(String identifier, Position position) {
        super(identifier, position);
    }

    @Override
    Direction getDirectionEstimate(Environment environment) {
        return null;
    }

}
