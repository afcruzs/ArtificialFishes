package fishes;

import java.util.ArrayList;
import java.util.List;

/*
 * This genotype attempts to model a function
 * that moves the fish in a 2D space.
 */
public class MovementGenotype {
	double moveToFishProbability, moveToPlantProbability, stayProbability, moveToNothingProbability;

	public MovementGenotype(double moveToFishProbability,
			double moveToPlantProbability, double stayProbability,
			double moveToNothingProbability) {
		super();
		this.moveToFishProbability = moveToFishProbability;
		this.moveToPlantProbability = moveToPlantProbability;
		this.stayProbability = stayProbability;
		this.moveToNothingProbability = moveToNothingProbability;
	}
	
	
}
