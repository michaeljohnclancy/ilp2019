package uk.ac.ed.inf.powergrab;

public class Agent extends Entity{

    static final double R = 0.0003;

    Agent(AgentBuilder builder) {
        super(builder);
    }

    public void move(Direction direction){
        position = position.nextPosition(direction);
    }

    @Override
    public void setBalance(double balance) {
        if (balance < 0){
            throw new IllegalArgumentException("Balance cannot be negative!");
        }
        this.balance = balance;
    }

    public static class AgentBuilder extends Entity.GenericEntityBuilder<AgentBuilder>{

        public Agent build(){
            this.position = new Position(latitude, longitude);
            return new Agent(this);
        }
    }
}

