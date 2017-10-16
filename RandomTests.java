package assignment4;
import java.util.*;

public class RandomTests {
    public static void main(String[] args) throws InvalidCritterException {
        for(int i = 0; i < 10; i++) {
            Critter.makeCritter("Craig");
        }
            Critter.displayWorld();
            for(int i = 0; i < Critter.population.size(); i++){
                Critter.population.get(i).doTimeStep();
            }
            Critter.displayWorld();
    }
}
