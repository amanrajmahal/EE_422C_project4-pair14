package assignment4;

public class Critter2 extends Critter {

	public String toString() { return "2"; }
	
	@Override
	public void doTimeStep() {
		int direction;
		
		if(getEnergy()>=(2*Params.min_reproduce_energy)){
			Critter2 child = new Critter2();
			direction = getRandomInt(7);
			reproduce(child,7);

		}

		if(getEnergy()>(10*Params.run_energy_cost)){
			direction = getRandomInt(7);
			run(direction);
		}
		else if(getEnergy()>(10*Params.walk_energy_cost)){
			direction = getRandomInt(7);
			run(direction);
		}



	}

	@Override
	public boolean fight(String opponent) {
		if(opponent.equals("@")){
			return true;
		}
		if(getEnergy()< Params.min_reproduce_energy){
			

			int dir = getRandomInt(7);
			run(dir);
			return false;				
		}
		return true;
	}

}
