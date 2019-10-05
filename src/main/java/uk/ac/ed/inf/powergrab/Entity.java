package uk.ac.ed.inf.powergrab;

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

    public double getBalance(){
        return balance;
    }

    public abstract void setPower(double power);

    public abstract void setBalance(double balance);

    public boolean equals(Entity entity) {
        return position.equals(entity.getPosition())
                && balance == entity.getBalance()
                && power == entity.getPower();
    }

    public abstract static class GenericEntityBuilder<T extends GenericEntityBuilder<T>> {

        Position position;
        double balance;
        double power;

        public T setPosition(double latitude, double longitude){
            this.position = new Position(latitude, longitude);
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
