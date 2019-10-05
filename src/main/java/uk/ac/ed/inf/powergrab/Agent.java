package uk.ac.ed.inf.powergrab;

public class Agent extends Entity{

    static final double R = 0.0003;

    Agent(AgentBuilder builder) {
        super(builder);
    }

    public void move(Direction direction){
         setPosition(getPosition().nextPosition(direction));
    }

    public void setPosition(Position position){
        this.position = position;
    }

    @Override
    public void setBalance(double balance) {
        if (balance < 0){
            throw new IllegalArgumentException("Balance cannot be negative!");
        }
        this.balance = balance;
    }

    @Override
    public void setPower(double power){
        if (power < 0){
            throw new IllegalArgumentException("Power cannot be negative!");
        }
        this.power = power;
    }

    public static class AgentBuilder extends Entity.GenericEntityBuilder<AgentBuilder>{

        public Agent build(){
            return new Agent(this);
        }
    }
}

