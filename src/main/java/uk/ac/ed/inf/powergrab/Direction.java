package uk.ac.ed.inf.powergrab;

public enum Direction {
    /**
     * The sin and cos values will be calculated once at the start of the jvm and stored,
     * available to be accessed by next position.
     */
    E(0.0), ESE(22.5), SE(45.0), SSE(67.5),
    S(90.0), SSW(112.5), SW(135.0), WSW(157.5),
    W(180.0), WNW(202.5), NW(225.0), NNW(247.5),
    N(270.0), NNE(292.5), NE(315.0), ENE(337.5);

    public final double sinAngle;
    public final double cosAngle;

    /**
     * Calculates the sin and cos of each direction's angle and stores them as attributes in the respective direction.
     * e.g. Direction.SSE.sinAngle
     * @param angle The Angle which Direction will describe.
     */
     Direction(double angle){
        double rads = Math.toRadians(angle);
        this.sinAngle = Math.sin(rads);
        this.cosAngle = Math.cos(rads);
    }
}
