package assignment4;

public class CritterA extends Critter {

	public String toString() { return "A"; }
	
	@Override
	public void doTimeStep() {
		int direction;
		
		if(getEnergy()>=(2*Params.min_reproduce_energy)){
			CritterA child = new CritterA();
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
			if(isLocationFreeRun(dir)&&(!hasMoved())){
			run(dir);
			return false;
			}
			else{
				
				if((getEnergy() - Params.run_energy_cost)<=0){
					setIsDead();
				}
				return false;
			}		
	}
		return true;
	}

}
