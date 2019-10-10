package uk.ac.ed.inf.powergrab;

import java.awt.geom.Rectangle2D;

public class Position {


    private static final double LATMIN = 55.942617;
    private static final double LATMAX = 55.946233;
    private static final double LONGMIN = -3.192473;
    private static final double LONGMAX = -3.184319;

    private static final Rectangle2D.Double playArea = new Rectangle2D.Double(LATMIN, LONGMIN, LATMAX-LATMIN, LONGMAX-LONGMIN);

    private static final double R = 0.0003;

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
                latitude - (R * direction.sinAngle),
                longitude + (R * direction.cosAngle)
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
}
