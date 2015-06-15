package fishes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * This genotype attempts to model a function
 * that moves the fish in a 2D space.
 */
public class MovementGenotype {
	
	static final String FISH = "fish", PLANT = "plant", STAY = "stay", NOTHING = "nothing";
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
	
	public List<ProbableMovement> getSortedMovements(){
		List<ProbableMovement> l = new ArrayList<>();
		l.add(new ProbableMovement(FISH, moveToFishProbability));
		l.add(new ProbableMovement(PLANT, moveToPlantProbability));
		l.add(new ProbableMovement(STAY, stayProbability));
		l.add(new ProbableMovement(NOTHING, moveToNothingProbability));
		Collections.sort(l);
		return l;
	}
	
	public class ProbableMovement implements Comparable<ProbableMovement>{
		String type;
		double probability;
		public ProbableMovement(String type, double probability) {
			super();
			this.type = type;
			this.probability = probability;
		}
		
		@Override
		public int compareTo(ProbableMovement o) {
			
			if( probability > o.probability ) return 1;
			else if( probability == o.probability ) return 0;
			else return -1;
		}
		
		
	}
	
	
}
