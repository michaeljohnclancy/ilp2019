package uk.ac.ed.inf.powergrab;

public enum Direction {
    E(0.0), ESE(22.5), SE(45.0), SSE(67.5),
    S(90.0), SSW(112.5), SW(135.0), WSW(157.5),
    W(180.0), WNW(202.5), NW(225.0), NNW(247.5),
    N(270.0), NNE(292.5), NE(315.0), ENE(337.5);
    public final double sinAngle;
    public final double cosAngle;

     Direction(double angle){
        double rads = Math.toRadians(angle);
        this.sinAngle = Math.sin(rads);
        this.cosAngle = Math.cos(rads);
    }
}
