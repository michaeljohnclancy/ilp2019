package uk.ac.ed.inf.powergrab;

import java.util.Stack;

public abstract class Agent extends Entity{

    static final double R = 0.0003;
    private FlightPath positionsVisited;

    Agent(GenericEntityBuilder builder) {
        super(builder);
    }

    abstract Direction getDirectionEstimate(Environment environment);

    public void updatePositionAccordingTo(Environment environment){
        move(getDirectionEstimate(environment));
    }

    public void move(Direction direction){
         setPosition(getPosition().nextPosition(direction));
         positionsVisited.push(getPosition());
    }

    @Override
    public Position getPosition(){
        return positionsVisited.peek();
    }

    private void setPosition(Position position){
        positionsVisited.push(position);
    }

    @Override
    public void setBalance(double balance){
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

    public static abstract class AgentBuilder extends GenericEntityBuilder<AgentBuilder>{}
}

