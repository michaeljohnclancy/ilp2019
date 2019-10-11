package uk.ac.ed.inf.powergrab;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * This abstract class represents the bare minimum required to be an entity in the game.
 * Currently there are 2 Entity types: Agent and Station.
 * They both extend this class.
 */
public abstract class Entity {

    private String identifier;
    private Position position;
    double coins;
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

    public String getIdentifier(){
        return identifier;
    }

    public Position getPosition(){
        return position;
    }

    public double getPower(){
        return power;
    }

    public double getCoins(){
        return coins;
    }

    public boolean equals(Entity entity) {
        return new EqualsBuilder()
                .append(identifier, entity.getIdentifier())
                .append(coins, entity.getCoins())
                .append(power, entity.getPower())
                .isEquals()
        && position.equals(entity.getPosition());
    }

    public int hashcode(){
        return new HashCodeBuilder()
                .append(identifier)
                .append(position)
                .append(coins)
                .append(power)
                .toHashCode();
    }
}
