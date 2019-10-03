package uk.ac.ed.inf.powergrab;

public class Agent {

    static final double R = 0.0003;

    private double power;
    private double balance;
    private Position position;

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        if (balance < 0) {
            this.balance = 0;
        } else {
            this.balance = balance;
        }
    }

    public double getPower() {
        return this.power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) throws Exception {
        if (position.inPlayArea()) {
            this.position = position;
        } else {
            throw new Exception("New position is not within the play area!");
        }
    }
}

