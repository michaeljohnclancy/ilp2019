package uk.ac.ed.inf.powergrab;

import jdk.internal.org.objectweb.asm.TypeReference;

public abstract class Entity {

    Position position;
    double balance;
    double power;

    Entity(GenericEntityBuilder builder){
        position = builder.position;
        balance = builder.balance;
        power = builder.power;

    }

    public Position getPosition(){
        return position;
    }

    public double getPower(){
        return power;
    }

    public void setBalance(double power) {

    }

    public double getBalance(){
        return balance;
    }

    public void setPower(double power) {

    }

    public abstract static class GenericEntityBuilder<T extends GenericEntityBuilder<T>> {
        double latitude;
        double longitude;
        double balance;
        double power;

        Position position;

        public T setLatitude(double latitude){
            this.latitude = latitude;
            return self();
        }

        public T setLongitude(double longitude){
            this.longitude = longitude;
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
