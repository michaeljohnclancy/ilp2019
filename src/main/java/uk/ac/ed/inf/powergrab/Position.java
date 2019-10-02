package uk.ac.ed.inf.powergrab;

import static uk.ac.ed.inf.powergrab.App.playArea;

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
                latitude - (R * direction.sinAngle),
                longitude + (R * direction.cosAngle)
        );
    }

    public boolean inPlayArea() {
        return playArea.contains(latitude, longitude);

    }
}
