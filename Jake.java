package assignment4;

public class Jake extends Critter {
    private int dir;
    private boolean hasMoved = false;
    public Jake() {

    }

    @Override
    public String toString() {
        return "J";
    }

    @Override
    public void doTimeStep() {
        hasMoved = false;
        /* ----------- walk, run, or rest ------------ */
        dir = Critter.getRandomInt(9);
        if(dir < 8){                // dir < 8 is walk
            walk(dir);              // 0-7 are walking directions
            hasMoved = true;
        }
        else if(dir == 8){          // 8 is run
            dir = Critter.getRandomInt(7);
            run(dir);
            hasMoved = true;
        }                           // 9 is rest
        /* ---------- Reproduction Decision ---------- */
        if(this.getEnergy() >= ((Params.min_reproduce_energy*2)+        // needs enough energy to produce twice...
                Params.rest_energy_cost+                                // ...and rest energy...
                Params.start_energy)){                                  // ...and leftover energy.
                Jake offspring1 = new Jake();
                Jake offspring2 = new Jake();                           // if reproduce, make 2
                reproduce(offspring1, Critter.getRandomInt(7));
                reproduce(offspring2, Critter.getRandomInt(7));
        }
    }

    @Override
    public boolean fight(String oponent) {
        if((oponent.equals("@")) || ((this.getEnergy() >= 100) && !oponent.equals("J"))){ return true; }
        else if(!hasMoved){
            switch (dir) {                // Tries to go back the way it came.
                case 0: { dir = 4; }
                case 1: { dir = 5; }
                case 2: { dir = 6; }
                case 3: { dir = 7; }
                case 4: { dir = 0; }
                case 5: { dir = 1; }
                case 6: { dir = 2; }
                case 7: { dir = 3; }
            }
        }
        return false;
    }
}
