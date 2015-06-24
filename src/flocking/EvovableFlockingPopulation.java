package flocking;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import evolution.Evolvable;
import evolution.Population;

public class EvovableFlockingPopulation implements Population {

	private List<Evolvable> population;
	private Evolvable bestIndividualSoFar;

	public EvovableFlockingPopulation() {
		population = new ArrayList<Evolvable>();
		bestIndividualSoFar = null;
	}

	public void addIndividual(EvovableFlockingIndividual individual) {
		population.add(individual);
		if (bestIndividualSoFar == null
				|| bestIndividualSoFar.fitness() < individual.fitness())

			bestIndividualSoFar = individual;
	}
	
	public void clear(){
		population.clear();
		bestIndividualSoFar = null;
	}

	@Override
	public Population clone() {
		EvovableFlockingPopulation pop = new EvovableFlockingPopulation();

		for (Evolvable efp : population)
			pop.addIndividual((EvovableFlockingIndividual) efp); // Safe cast

		return pop;
	}

	@Override
	public Collection<Evolvable> individuals() {
		return population;
	}

	@Override
	public int size() {
		return population.size();
	}

	@Override
	public Evolvable getBestIndividual() {
		return bestIndividualSoFar;
	}

	public static class EvovableFlockingIndividual extends FlockingAgent implements Evolvable {
		
		public static EvovableFlockingIndividual randomIndividual(Dimension bounds){
			FlockingAgent ag = randomFlockingAgent(bounds);
			return new EvovableFlockingIndividual(ag.getPosition(), ag.velocity);
		}

		public EvovableFlockingIndividual(Point position,
				FlockingVector velocityVector) {

			super(position, velocityVector);
		}

		@Override
		public double fitness() {
			return 1.0;
		}
		
		double[] rawCode(){
			return new double[]{ NEIGHBOR_RAIDUS, SEPARATION_RAIDUS };
		}

		
		//TODO: Add more parameters in mating?
		@Override
		public Evolvable mate(Evolvable evolvable) {
			if( !(evolvable instanceof EvovableFlockingIndividual) )
				throw new IllegalArgumentException("Cant mate two different classes of individuals");
			
			EvovableFlockingIndividual partner = (EvovableFlockingIndividual)evolvable;
			
			double[] code = rawCode();
			double[] partnerCode = partner.rawCode();
			
			EvovableFlockingIndividual child = new EvovableFlockingIndividual(
					new Point((int)location.getX(),(int)location.getY()), velocity);;
			if( new Random().nextBoolean() ){
				child.NEIGHBOR_RAIDUS = code[0];
				child.SEPARATION_RAIDUS = code[1];
			}else{
				child.NEIGHBOR_RAIDUS = partnerCode[0];
				child.SEPARATION_RAIDUS = partnerCode[1];
			}
			
			return child;
			
//			EvovableFlockingIndividual partner = (EvovableFlockingIndividual)evolvable;
//			
//			double[] code = rawCode();
//			double[] partnerCode = partner.rawCode();
//			
//			Random rand = new Random();
//			int pivot = rand.nextInt(code.length);
//			double newCode[] = new double[code.length];
//			
//			for(int i=0; i<pivot; i++) 
//				newCode[i] = code[i];
//			
//			for(int i=pivot+1; i<code.length; i++)
//				newCode[i] = partnerCode[i];
//				
//			
//			int newX = (int) ((location.getX()+partner.location.getX())/2);
//			int newY = (int) ((location.getY()+partner.location.getY())/2);
//			EvovableFlockingIndividual child = new EvovableFlockingIndividual(
//					new Point((int)location.getX(),(int)location.getY()), velocity);
//			child.energy = 5.0;
//			child.NEIGHBOR_RAIDUS = newCode[0];
//			child.SEPARATION_RAIDUS = newCode[1];
//			
//			return child;
		}

		@Override
		public void mutate() {
			double [] code = rawCode();
			Random rand = new Random();
			code[ rand.nextInt(code.length) ] += rand.nextGaussian(); //Gaussian operator (?)
		}

		@Override
		public int compareTo(Evolvable o) {
			if( fitness() < o.fitness() )
				return 1;
			else if( fitness() > o.fitness() )
				return -1;
			else
				return 0;
		}

	}
	
	
	public String toString(){
		return "Population size: " + size();
	}

}
