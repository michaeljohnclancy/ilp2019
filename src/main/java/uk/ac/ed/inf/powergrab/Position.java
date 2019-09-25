package uk.ac.ed.inf.powergrab;


public class Position {

    private static final double R = 0.0003;

    public double latitude;
    public double longitude;


    public Position(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Position nextPosition(Direction direction){
        double nextLatitude;
        double nextLongitude;

        if (direction.toString().indexOf('S') >= 0) {
            nextLatitude = this.latitude - Math.abs(R * direction.sinAngle);
        } else{
            nextLatitude = this.latitude + Math.abs(R * direction.sinAngle);
        }

        if (direction.toString().indexOf('W') >= 0) {
            nextLongitude = this.longitude - Math.abs(R * direction.cosAngle);
        }else{
            nextLongitude = this.longitude + Math.abs(R * direction.cosAngle);
        }

        System.out.println(String.format("Direction: %s; lat = %s; long = %s;", direction, nextLatitude, nextLongitude));

        return new Position(nextLatitude, nextLongitude);
    }

    public boolean inPlayArea() {
        return ((this.latitude > 55.942617 && this.latitude < 55.946233) &&
                (this.longitude < -3.184319 && this.longitude > -3.192473));
    }
}
