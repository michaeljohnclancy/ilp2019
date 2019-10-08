package uk.ac.ed.inf.powergrab;

public class StatelessAgent extends Agent {

    StatelessAgent(StatelessAgentBuilder builder) {
        super(builder);
    }

    @Override
    Direction getDirectionEstimate(Environment environment) {
        return null;
    }

    public static class StatelessAgentBuilder extends GenericEntityBuilder<StatelessAgentBuilder>{

        @Override
        public StatelessAgent build() {
            return new StatelessAgent(this);
        }
    }

}
