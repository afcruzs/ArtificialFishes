package fishes;

import java.util.ArrayList;
import java.util.List;

/*
 * This genotype attempts to model a function
 * that moves the fish in a 2D space.
 */
public class MovementGenotype {
	List<Double> probabilities;
	
	public MovementGenotype(){
		probabilities = new ArrayList<>();
	}
	
	public void addProbability(double prob){
		probabilities.add(prob);
	}
}
