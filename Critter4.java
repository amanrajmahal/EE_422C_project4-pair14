package assignment4;

public class Critter4 extends Critter {
	private int reproduce;

	Critter4(){
		 reproduce =0;
	}
	/*
	*@Javadoc returns string name of the critter
	*/

	public String toString() { return "4"; }

	/*
	*@javadoc reproduces twice if energy is greater than four times the reproduce energy
	*Runs if energy is four times run_energy cost
	*walks for energy greater than 5 times
	*/

	@Override
	public void doTimeStep() {

		int direction;

		if(getEnergy() > 4*Params.min_reproduce_energy)
		{
			Critter4 child = new Critter4();
			direction = getRandomInt(8);
			reproduce(child,direction);
			Critter4 child2 = new Critter4();
			direction = getRandomInt(8);
			reproduce(child2,direction);
		}
		else if (getEnergy()>4*Params.run_energy_cost)
		{
			direction = getRandomInt(8);
			run(direction);

		}
		else if(getEnergy()> 5 *Params.walk_energy_cost){
			direction = getRandomInt(8);
			walk(direction);
		}


		

	}
	/*
	*@javadoc Fights if algae and if energy is greater than 150, walks otherwise
	*/

	@Override
	public boolean fight(String oponent) {
		
		if(oponent.equals("@")|| getEnergy()>150){
			return true;
		}
		else{
			walk(getRandomInt(8));
			return false;
		}
	}

}
