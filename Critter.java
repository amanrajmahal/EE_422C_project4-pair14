package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */


import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	public static List<Critter> babies = new java.util.ArrayList<Critter>();
	private boolean isDead;
	private boolean hasMoved;

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;

	// ------------------ Gets and Sets ------------------
	public int getXcoord(){
		return this.x_coord;
	}

	public void setXcoord(int x){
		this.x_coord = x;
	}

	public int getYcoord(){
		return this.y_coord;
	}

	public void setYcoord(int y){
		this.y_coord = y;
	}
	//----------------------------------------------------
	
	protected final void walk(int direction) {
		this.energy = this.energy - Params.walk_energy_cost;
		if(this.energy<=0){
			this.isDead = true;
		}
		if(!this.hasMoved){
			hasMoved = true;
		
		switch(direction){
			case 0:{					// Right
				moveX(1);
			} case 1: {					// Up/Right
				moveX(1);
				moveY(1);
			} case 2: {					// Up
				moveY(1);
			} case 3: {					// Up/Left
				moveX(-1);
				moveY(1);
			} case 4: {					// Left
				moveX(-1);
			} case 5: {					// Down/Left
				moveX(-1);
				moveY(-1);
			} case 6: {					// Down
				moveY(-1);
			} case 7: {					// Down/Right
				moveX(1);
				moveY(-1);
			}
		}
	}}
	
	protected final void run(int direction) {
		
	this.energy = this.energy - Params.run_energy_cost;
	if(this.energy<=0){
		this.isDead = true;
	}
	if(!this.hasMoved){
		hasMoved = true;
		switch(direction){
			case 0:{					// Right
				moveX(2);
			} case 1: {					// Up/Right
				moveX(2);
				moveY(2);
			} case 2: {					// Up
				moveY(2);
			} case 3: {					// Up/Left
				moveX(-2);
				moveY(2);
			} case 4: {					// Left
				moveX(-2);
			} case 5: {					// Down/Left
				moveX(-2);
				moveY(-2);
			} case 6: {					// Down
				moveY(-2);
			} case 7: {					// Down//Right
				moveX(2);
				moveY(-2);
			}
		}
		
	}}

	// --------------------------- Movement ---------------------------
	private void moveX(int x){
		this.x_coord = ((((x_coord + x)%Params.world_width)+Params.world_width)%Params.world_width);
	}

	private void moveY(int x){
		this.y_coord = ((((y_coord + x)%Params.world_height)+Params.world_height)%Params.world_height);
	}

	// ---------------------------------------------------------------

	protected final void reproduce(Critter offspring, int direction) {
		if(this.energy >= Params.min_reproduce_energy){
			offspring.energy = (this.energy/2);					// Offspring energy half parent energy rounded down
			offspring.energy += Params.walk_energy_cost;		// So that we can play offspring without messing up energy
			offspring.x_coord = this.x_coord;					// Child will take location of parent, then "walk"...
			offspring.y_coord = this.y_coord;					// ...to direction indicated.
			babies.add(offspring);							//
			this.energy = ((this.energy/2)+(this.energy%2));	// Parent energy divided by two and rounded up
			offspring.walk(direction);	// Place offspring adjacent to parent						
		} else { return; }										// Parent did not have enough reproduction energy
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		
		Class<?> myCritter = null;
		Object instanceOfMyCritter = null;
		Constructor<?> constructor = null;
		

		try {
			myCritter = Class.forName(myPackage + "."+critter_class_name); 	// Class object of specified name
		} catch (ClassNotFoundException e) {
			throw new InvalidCritterException(critter_class_name);
		}
		
		try {
			constructor = myCritter.getConstructor();		// No-parameter constructor object
			instanceOfMyCritter = constructor.newInstance();
		} catch(Exception e) {
			
		}
		
		Critter me = (Critter)instanceOfMyCritter;
		me.x_coord = getRandomInt(Params.world_width-1);
		me.y_coord = getRandomInt(Params.world_height-1);
		me.energy = Params.start_energy;
		me.isDead = false;
		population.add(me);
		
		

	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		Class<?> myCritter = null;
		try {
			myCritter = Class.forName(myPackage + "."+critter_class_name); 	// Class object of specified name
		} catch (ClassNotFoundException e) {
			throw new InvalidCritterException(critter_class_name);
		}
	
		for(Object obj : population){
			
			if((myCritter.isInstance(obj))) {
				result.add((Critter)obj);
			}
		}
	
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		// Complete this method.
	}
public static void worldTimeStep() throws InvalidCritterException{
        for(Critter obj : population){
            obj.doTimeStep();                                // Perform all individual time steps
        }
        doEncounters();
        updateRestEnergy();
        removeDeadCritters();
        addBabies();
        RefreshAlgae();
        // Need to Refresh Algae Here
    }

    private static void addBabies(){
        population.addAll(babies);
        babies.clear();
    }

    private static void updateRestEnergy(){
        for(Critter obj : population){
            obj.energy -= Params.rest_energy_cost;
             
        }
    }
	// change to private before submission
	public static void doEncounters(){

	// getting critters who can have a possible fight
	for(int i = 0; i< population.size();i++){
			for(int j=i+1; j<population.size();j++){

				if((population.get(i).x_coord == population.get(j).x_coord)&&
					(population.get(i).y_coord == population.get(j).y_coord)&&(i!=j)&&
					(!(population.get(i).isDead) &&(!(population.get(j).isDead)))){
					doFight(population.get(i),population.get(j));
				}
			}
		}
	}

	private static void doFight(Critter a, Critter b){
		int roll_a; int roll_b;
		
		boolean a_wants_to_fight = a.fight(b.toString());
		boolean b_wants_to_fight = b.fight(a.toString());

		if((a_wants_to_fight&&b_wants_to_fight)&&(!a.isDead)&&(!b.isDead)){
			 roll_a = getRandomInt(a.energy);
			 roll_b = getRandomInt(b.energy);
			makeFightHappen(a,b, roll_a,roll_b);				
		}
		if((!a_wants_to_fight)&&(b_wants_to_fight)&&(!a.isDead)&&(!b.isDead)){
			roll_a =0;
			roll_b =getRandomInt(b.energy);
			makeFightHappen(a,b, roll_a,roll_b);
		}
		if((a_wants_to_fight)&&(!b_wants_to_fight)&&(!a.isDead)&&(!b.isDead)){
			roll_a = getRandomInt(a.energy);
			roll_b =0;
			makeFightHappen(a,b, roll_a,roll_b);
		}
		if((!a_wants_to_fight)&&(!b_wants_to_fight)&&(!a.isDead)&&(!b.isDead)){
			roll_a =0;
			roll_b =0;
			makeFightHappen(a,b, roll_a,roll_b);
		}
	}
	private static void makeFightHappen(Critter a, Critter b, int roll_a, int roll_b){

		if((a.x_coord == b.x_coord)&&(a.y_coord==b.y_coord)){

			if(roll_a>=roll_b){
					// i gets half of the energy
					a.energy+= ((b.energy)/2);
					b.energy = -1;
					b.isDead = true;								
				}
				else{
					b.energy += ((a.energy)/2);
					a.energy = -1;
					a.isDead = true;
			}
		}
	}// change from public to private
	private static void RefreshAlgae() throws InvalidCritterException {
        for(int i = 0; i < Params.refresh_algae_count; i++){
            makeCritter("Algae");
        }
    }
	public static void removeDeadCritters() {
		for(int i =0;i<population.size();i++) {
			
			if(population.get(i).isDead==true) {
				population.remove(i);
				i--;
			}			
		}
	}



	
	public static void displayWorld() {
		int width = Params.world_width;
		int height = Params.world_height;
		String [][] world = new String[height+2][width+2];
		for(String[] row :world) {
			Arrays.fill(row, " ");
		}
		world [0][0] = "+";
		world[0][width+1] ="+";
		world[height+1][0] = "+";
		world[height+1][width+1] ="+";
		
		for(int i = 1; i<=width; i++) {
			world [0][i] = "-";
			world [height+1][i] ="-";
		}
		for(int i =1;i<=height; i++) {
			world[i][0]="|";
			world[i][width+1] = "|";
		}
		for(int i = 0; i < population.size(); i++){
            world[population.get(i).y_coord +1][population.get(i).x_coord +1] = population.get(i).toString();
        }
		for(int i=0;i<height+2;i++) {
			for(int j=0;j<width+2;j++) {
				System.out.print(world[i][j]);
			}
			System.out.println("");
		}
		
		
	}
	protected  boolean isLocationFreeWalk(int direction){
		int projected_location_x =this.x_coord;
		int projected_location_y =this.y_coord;
		switch(direction){
			case 0:{					// Right
				projected_location_x =  projectedMoveX(1);
			} case 1: {					// Up/Right
				projected_location_x =  projectedMoveX(1);
				projected_location_y = projectedMoveY(1);
			} case 2: {					// Up
				projected_location_y = projectedMoveY(1);
			} case 3: {					// Up/Left
				projected_location_x =  projectedMoveX(-1);
				projected_location_y = projectedMoveY(1);
			} case 4: {					// Left
				projected_location_x =  projectedMoveX(-1);
			} case 5: {					// Down/Left
				projected_location_x =  projectedMoveX(-1);
				projected_location_y = projectedMoveY(-1);
			} case 6: {					// Down
				projected_location_y = projectedMoveY(-1);
			} case 7: {					// Down/Right
				projected_location_x =  projectedMoveX(1);
				projected_location_y = projectedMoveY(-1);
			}
		}

		return isOccupied(projected_location_x,projected_location_y);

	}

	protected boolean isLocationFreeRun(int direction){

		int projected_location_x = this.x_coord;
		int projected_location_y = this.y_coord;
		switch(direction){
			case 0:{					// Right
				projected_location_x  = projectedMoveX(2);
			} case 1: {					// Up/Right
				projected_location_x  = projectedMoveX(2);
				projected_location_y = projectedMoveY(2);
			} case 2: {					// Up
				projected_location_y = projectedMoveY(2);
			} case 3: {					// Up/Left
				projected_location_x  = projectedMoveX(-2);
				projected_location_y = projectedMoveY(2);
			} case 4: {					// Left
				projected_location_x  = projectedMoveX(-2);
			} case 5: {					// Down/Left
				projected_location_x  = projectedMoveX(-2);
				projected_location_y = projectedMoveY(-2);
			} case 6: {					// Down
				projected_location_y = projectedMoveY(-2);
			} case 7: {					// Down//Right
				projected_location_x  = projectedMoveX(2);
				projected_location_y = projectedMoveY(-2);
			}
		}
		return isOccupied(projected_location_x,projected_location_y);
	}







	private int projectedMoveX(int x){
		int result = ((((x_coord + x)%Params.world_width)+Params.world_width)%Params.world_width);
		return result;
	}

	private int projectedMoveY(int x){
		int result = ((((y_coord + x)%Params.world_height)+Params.world_height)%Params.world_height);
		return result;
	}

	private static boolean isOccupied(int x , int y){
		for(Critter obj : population){
			if((obj.x_coord==x)&&(obj.y_coord==y)&&(!obj.isDead)) return false;
		}
		return true;
	}
	protected void setIsDead(){
		this.isDead = true;
	}

	protected boolean hasMoved(){
		return this.hasMoved;
	}
}
