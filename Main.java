package assignment4;
/* CRITTERS Main.java
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

import org.omg.CORBA.DynAnyPackage.Invalid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.awt.List;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    
    static Scanner kb;  // scanner connected to keyboard input, or input file
    private static String inputFile;    // input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;  // if test specified, holds all console output
    private static String myPackage;    // package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;    // if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     * @throws InvalidCritterException 
     */
    public static void main(String[] args){


        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));          
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */
        
        boolean play  = true;
        ArrayList<String> input = new ArrayList<String>();
        
        while(play){
            System.out.print("Critters>");
            input = getInput(kb);
            
            if(validCommand(input)) {
            
            if(input.size()>3){
                System.out.print("error processing:");
                printInput(input);
            }
            else if(input.size()==1){
                switch(input.get(0)){
                    case "quit": {
                        play = false;
                        break;
                    }
                    case "show":{
                        Critter.displayWorld();
                        break;
                    }
                    case "step":{
                        try {
                            Critter.worldTimeStep();
                            break;
                        } catch(InvalidCritterException e) {

                        }
                    }
                    default:{
                        System.out.print("error processing:");
                        printInput(input);
                    }
                }
            }
            else if(input.size()==2){
                switch(input.get(0)){
                    case "seed":{
                        try{
                            long num = Long.parseLong(input.get(1));
                            Critter.setSeed(num);
                            
                        }
                        catch (NumberFormatException e){
                            System.out.print("error processing:");
                            printInput(input);

                        }
                        break;                        
                    }
                    case "step":{
                        try{
                            int num = Integer.parseInt(input.get(1));

                            for( int i = 0;i<num;i++ ){
                                Critter.worldTimeStep();
                            }
                        }
                        catch (NumberFormatException | InvalidCritterException e){
                            System.out.print("error processing:");
                            printInput(input);

                        }
                        break;
                    }
                    case "stats":{
                        Class<?> myCritter = null;
                        Object instanceOfMyCritter = null;
                        Constructor<?> constructor = null;
                        
                        

                    try {
                    myCritter = Class.forName(myPackage + "."+input.get(1));  // Class object of specified name
                    instanceOfMyCritter = myCritter.newInstance(); 
                    Method stats = instanceOfMyCritter.getClass().getMethod("runStats", java.util.List.class);
                    stats.invoke(null, Critter.getInstances(input.get(1)));
                    
                        } catch (ClassNotFoundException | InvalidCritterException | InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
                    System.out.print("error processing:");
                    printInput(input);
                   
                   
                        }
                        break;
                    }
                    default:{
                        System.out.print("error processing:");
                        printInput(input);
                    }
                }
            }
            else if (input.size() == 3){
                switch (input.get(0)) {

                    case "make": {
                     Class<?> myCritter = null;        

                    try {
                    myCritter = Class.forName(myPackage + "."+input.get(1));  // Class object of specified name
                    
                        int num = Integer.parseInt(input.get(2));

                        for( int i = 0;i<num;i++ ){
                            Critter.makeCritter(input.get(1));
                        }
                        
                    
                        
                        } catch (ClassNotFoundException | InvalidCritterException | NumberFormatException e) {
                    System.out.print("error processing:");
                    printInput(input);
                    
                    } 
                        
                    break;
                    }
                default: {
                    System.out.print("error processing:");
                    printInput(input);
                }
                }
            }

        }}
        
        
        
        
        
        /* Write your code above */
        System.out.flush();

    }
    public static void printInput(ArrayList<String> input){
        for(int i =0;i<input.size();i++){
        System.out.print(input.get(i)+" ");
        
    }
        System.out.println("");
    }

    public static ArrayList<String> getInput(Scanner keyboard){
        ArrayList<String> result = new ArrayList<String>();
        //System.out.print("Critters>");
        String input = keyboard.nextLine();    
        String[] arr = input.split(" ");
        
        for(int i =0; i<arr.length;i++){
            result.add(i,arr[i]);
        }
         return result;
    }
    public static void addCritter() throws InvalidCritterException{
        for(int i =0;i<10;i++){
            Critter.makeCritter("Craig");
            Critter.makeCritter("Algae");
            Critter.makeCritter("Critter2");
        }
    }
    public static boolean validCommand(ArrayList<String> input) {
    	ArrayList<String> validInput = new ArrayList<String>();
    	validInput.add("make"); validInput.add("quit"); validInput.add("show"); validInput.add("step");
    	validInput.add("seed");validInput.add("stats");
    	
    	if(!validInput.contains(input.get(0))) {
    		System.out.print("invalid command:");
    		printInput(input);
    		return false;}
    	
    	return true;
    }
    
}
