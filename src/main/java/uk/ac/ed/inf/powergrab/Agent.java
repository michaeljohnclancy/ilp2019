package uk.ac.ed.inf.powergrab;

public abstract class Agent extends Entity{

    static final double R = 0.0003;
    private FlightPath positionsVisited;

    public Agent(String identifier, Position position) {
        super(identifier, position);

        power = 250.0;
        coins = 0.0;

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

    public void addCoins(double newCoins){
        double sum = coins + newCoins;
        if (sum < 0){
            throw new IllegalArgumentException("Coin Balance cannot be negative!");
        }
        coins = sum;
    }

    public void givePower(double newPower){
        double sum = power + newPower;
        if (sum < 0){
            throw new IllegalArgumentException("Power cannot be negative!");
        }
        power = sum;
    }


}

