package assignment4;

public class Buffalo extends Critter{
    public Buffalo() {

    }

    @Override
    public String toString() {
        return "B";
    }

    @Override
    public void doTimeStep() {
        walk(2);                     // always runs straight up
        if(this.getEnergy() > Params.start_energy){
            Buffalo NewGuy = new Buffalo();
            reproduce(NewGuy, 6);   // produce offspring behind
        }
    }

    @Override
    public boolean fight(String oponent) {
        return true;
    }
}
