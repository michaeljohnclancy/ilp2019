package uk.ac.ed.inf.powergrab;

import java.util.Comparator;

public class Comparators {
    public static class EuclideanComparator implements Comparator<Position> {
        public int compare(final Position p1, final Position p2){
            return (int) Math.sqrt(
                    Math.pow(p2.latitude - p1.latitude, 2)
                            + Math.pow(p2.longitude - p1.longitude, 2)
            );
        }
    }
}


