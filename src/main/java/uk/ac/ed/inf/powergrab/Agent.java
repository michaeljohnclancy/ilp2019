package uk.ac.ed.inf.powergrab;

public abstract class Agent extends Entity{

    static final double R = 0.0003;
    private FlightPath positionsVisited;

    public Agent(String identifier, Position position) {
        super(identifier, position);

        setBalance(0.0);
        setPower(250.0);

        positionsVisited = new FlightPath();
        setPosition(position);
    }

    public abstract void moveAndGatherResources(PowerGrabMap powerGrabMap);

    public void move(Direction direction){
         setPosition(getPosition().nextPosition(direction));
    }

    public FlightPath getFlightPath(){
        return positionsVisited;
    }

    @Override
    public Position getPosition(){
        return positionsVisited.head();
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

}

