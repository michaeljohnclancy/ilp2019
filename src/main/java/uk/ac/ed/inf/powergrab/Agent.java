package uk.ac.ed.inf.powergrab;

public class Agent {

    private double power;
    private double balance;
    private Position position;

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        if (balance < 0){
            this.balance = 0;
        } else{
            this.balance = balance;
        }
    }

    public double getPower() {
        return this.power;
    }

    public void setPower(double power){
        this.power = power;
    }

    public Position getPosition(){
        return this.position;
    }

    public void setPosition(Position position){
        if (position.inPlayArea()){
        this.position = position;
    } else{
            throw Exception("Cant change Position");

        }

    public


}

public static class IllegalPositionException extends Exception{
        public IllegalPositionException(){
            super(exception);

        }

}
}
