package uk.ac.ed.inf.powergrab;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import static uk.ac.ed.inf.powergrab.PowerGrabMap.playArea;

public class Position {

    public double latitude;
    public double longitude;

    public Position(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * This method returns a new Position given a direction. It accesses the precalculated sin and cos values of the given direction.
     * @param direction Direction from the current position to travel in.
     * @return Updated position after moving in given direction.
     */
    public Position nextPosition(Direction direction){
        return new Position(
                latitude - (Agent.R * direction.sinAngle),
                longitude + (Agent.R * direction.cosAngle)
        );
    }

    /**
     * This method calls 'contains' method of the static Rectangle2D object defined in App. This returns a boolean value
     * indicating whether or a given latitude and longitude is within the play area.
     * @return
     */
    public boolean inPlayArea() {
        return playArea.contains(latitude, longitude);
    }

    public boolean equals(Position position){
        return new EqualsBuilder()
                .append(latitude, position.latitude)
                .append(longitude, position.longitude)
                .isEquals();
    }

    public int hashcode(){
        return new HashCodeBuilder(17, 37)
                .append(latitude)
                .append(longitude)
                .toHashCode();
    }

    public double[] toArray(){
        return new double[] {latitude, longitude};
    }

    public double[] toReversedArray(){
        return new double[] {longitude, latitude};
    }
}
