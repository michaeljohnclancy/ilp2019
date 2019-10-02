package uk.ac.ed.inf.powergrab;


import static uk.ac.ed.inf.powergrab.App.*;

public class Position {

    private static final double R = 0.0003;

    public double latitude;
    public double longitude;


    public Position(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Position nextPosition(Direction direction){
        return new Position(
                this.latitude - (R * direction.sinAngle),
                this.longitude + (R * direction.cosAngle)
        );
    }

    public boolean inPlayArea() {
        return playArea.contains(this.latitude, this.longitude);

    }
}
