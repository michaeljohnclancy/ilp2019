package uk.ac.ed.inf.powergrab;

public abstract class Agent extends Entity{

    static final double R = 0.0003;

    Agent(GenericEntityBuilder builder) {
        super(builder);
    }

    abstract Direction getDirectionEstimate(Environment environment);

    public void updatePositionAccordingTo(Environment environment){
        move(getDirectionEstimate(environment));
    }

    public void move(Direction direction){
         setPosition(getPosition().nextPosition(direction));
    }

    private void setPosition(Position position){
        this.position = position;
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
}

