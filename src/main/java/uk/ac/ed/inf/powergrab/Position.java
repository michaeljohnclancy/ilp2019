package uk.ac.ed.inf.powergrab;


import java.awt.geom.Rectangle2D;

public class Position {

    private static final double LATMIN = 55.942617;
    private static final double LATMAX = 55.946233;
    private static final double LONGMIN = -3.192473;
    private static final double LONGMAX = -3.184319;

    public static final Rectangle2D.Double playArea = new Rectangle2D.Double(LATMIN, LONGMIN, LATMAX - LATMIN, LONGMAX - LONGMIN);

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
