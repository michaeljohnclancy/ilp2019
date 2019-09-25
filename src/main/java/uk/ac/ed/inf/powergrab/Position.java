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
                this.latitude + getDistance(direction, true),
                this.longitude + getDistance(direction, false));
    }

    public double getDistance(Direction direction, boolean isLatitude){
            return ((direction.
                    toString().
                    indexOf(isLatitude ? 'S' : 'W') >= 0) ? -1 : 1
            ) * Math.abs(R * (isLatitude ? direction.sinAngle : direction.cosAngle));
    }

    public boolean inPlayArea() {
        return playArea.contains(this.latitude, this.longitude);

    }
}
