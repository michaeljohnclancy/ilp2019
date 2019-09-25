package uk.ac.ed.inf.powergrab;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class App {

    public static final double LATMIN = 55.942617;
    public static final double LATMAX = 55.946233;
    public static final double LONGMIN = -3.192473;
    public static final double LONGMAX = -3.184319;

    public static final Rectangle2D.Double playArea = new Rectangle2D.Double(LATMIN, LONGMIN, LATMAX-LATMIN, LONGMAX-LONGMIN);
}
