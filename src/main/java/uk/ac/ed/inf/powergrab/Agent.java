package uk.ac.ed.inf.powergrab;

public class Agent extends Entity{

    static final double R = 0.0003;

    Agent(AgentBuilder builder) {
        super(builder);
    }

    public void setBalance(double balance) {
        if (balance < 0) {
            this.balance = 0;
        } else {
            this.balance = balance;
        }
    }

    public void setPosition(Position position) throws Exception {
        if (position.inPlayArea()) {
            this.position = position;
        } else {
            throw new Exception("New position is not within the play area!");
        }
    }

    public static class AgentBuilder extends Entity.GenericEntityBuilder<Station.StationBuilder>{

        public Agent build(){
            this.position = new Position(latitude, longitude);
            return new Agent(this);
        }
    }
}

