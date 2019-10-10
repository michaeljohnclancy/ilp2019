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
