public enum Direction {
    N(0.0), NNE(22.5), NE(45), NEE(67.5),
    E(90), EES(112.5), ES(135.0), ESS(157.5),
    S(180), SSW(202.5), SW(225.0), SWW(247.5),
    W(270.0), WWN(292.5), WN(315.0), WNN(337.5);

    public final double sinAngle;
    public final double cosAngle;

    private Direction(double angle){
        double rads = Math.toRadians(angle);
        this.sinAngle = Math.sin(rads);
        this.cosAngle = Math.cos(rads);
    }
}
