package uk.ac.ed.inf.powergrab;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * This abstract class represents the bare minimum required to be an entity in the game.
 * Currently there are 2 Entity types: Agent and Station.
 * They both extend this class.
 */
public abstract class Entity {

    String identifier;
    Position position;
    double balance;
    double power;

    Entity(GenericEntityBuilder builder){
        position = builder.position;
        balance = builder.balance;
        power = builder.power;
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

    public double getBalance(){
        return balance;
    }

    abstract void setPower(double power);

    abstract void setBalance(double balance);

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

    /**
     * This class is the builder for any entity type. It uses TypeParameters to ensure each method returns the correct
     * child class at each return, instead of returning an object of type GenericEntityBuilder.
     * @param <T>
     */
    public abstract static class GenericEntityBuilder<T extends GenericEntityBuilder<T>> {

        String identifier;
        Position position;
        double balance;
        double power;

        public T setIdentifier(String identifier){
            this.identifier = identifier;
            return self();
        }

        public T setPosition(double latitude, double longitude) throws IllegalArgumentException {
            this.position = new Position(latitude, longitude);
            if (!this.position.inPlayArea()){
               throw new IllegalArgumentException("Provided coordinates not in play area!");
            }
            return self();
        }

        public T setBalance(double balance){
            this.balance = balance;
            return self();
        }

        public T setPower(double power){
            this.power = power;
            return self();
        }

        public abstract Entity build();

        @SuppressWarnings("unchecked")
        final T self() {
            return (T) this;
        }
    }
}
