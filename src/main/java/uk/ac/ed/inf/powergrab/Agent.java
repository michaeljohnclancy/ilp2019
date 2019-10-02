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

}
