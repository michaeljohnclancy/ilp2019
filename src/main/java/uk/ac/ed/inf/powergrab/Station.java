package uk.ac.ed.inf.powergrab;

import static java.lang.Double.max;
import static java.lang.Double.min;

public class Station {

    private final Position position;
    private double balance;
    private double power;

    public Station(double latitude, double longitude, double balance, double power){
        this.position = new Position(latitude, longitude);
        this.balance = balance;
        this.power = power;
    }

    public void transferBalance(Agent agent){
        double newBalance =  balance - (255 - agent.getBalance());
        agent.setBalance(agent.getBalance()+balance);
        balance = newBalance;

        double totalBalance = balance + agent.getBalance();
        balance = max(0, totalBalance - 255.0);
        agent.setBalance(min(255.0, totalBalance));
    }
}
