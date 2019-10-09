package uk.ac.ed.inf.powergrab;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Optional;

/**
 * This abstract class represents the bare minimum required to be an entity in the game.
 * Currently there are 2 Entity types: Agent and Station.
 * They both extend this class.
 */
public abstract class Entity {

    private String identifier;
    Position position;
    double balance;
    double power;

    public Entity(String identifier, Position position){
        this.identifier = identifier;
        setPosition(position);
    }

    private void setPosition(Position position) throws IllegalArgumentException {
        if (!position.inPlayArea()){
            throw new IllegalArgumentException("Provided coordinates not in play area!");
        }
        this.position = position;

    }

    abstract void setPower(double power);

    abstract void setBalance(double balance);

    public String getIdentifier(){
        return identifier;
    }

    public double getPower(){
        return power;
    }

    public double getBalance(){
        return balance;
    }

    public Position getPosition(){
        return position;
    }

    public boolean equals(Entity entity) {
        return new EqualsBuilder()
                .append(identifier, entity.getIdentifier())
                .append(balance, entity.getBalance())
                .append(power, entity.getPower())
                .isEquals()
        && position.equals(entity.getPosition());
    }

    public int hashcode(){
        return new HashCodeBuilder()
                .append(identifier)
                .append(position)
                .append(balance)
                .append(power)
                .toHashCode();
    }
}
