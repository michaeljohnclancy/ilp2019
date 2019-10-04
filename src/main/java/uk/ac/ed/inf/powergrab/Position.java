package uk.ac.ed.inf.powergrab;


import static uk.ac.ed.inf.powergrab.StationCollection.playArea;

public class Position {

    public double latitude;
    public double longitude;

    public Position(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Position nextPosition(Direction direction){
        return new Position(
                latitude - (Agent.R * direction.sinAngle),
                longitude + (Agent.R * direction.cosAngle)
        );
    }

    public boolean inPlayArea() {
        return playArea.contains(latitude, longitude);

    }

    public boolean equals(Position position){
        return position.latitude == latitude
                && position.longitude == longitude;
    }
}
