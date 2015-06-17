package evolution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import evolution.NumericalOptimizationTest.NumericalPopulation.EvolvableSolution;

public class NumericalOptimizationTest {
	
	static int MAX = 50;
	
	static double f(double x){
		return -Math.pow(x, 6.0) -3*Math.pow(x,3.0)+x+1.0;
	}
	
	public static void main(String[] args) {
		
		NumericalPopulation initialPopulation = new NumericalPopulation();
		
		Random rand = new Random();
		
		for(int i=0; i<500; i++){
			initialPopulation.add(new EvolvableSolution(rand.nextDouble()) );
		}
		
		EvolutionaryAlgorithm<NumericalPopulation> algo 
		= new EvolutionaryAlgorithm<>(initialPopulation, 
				new Life<NumericalPopulation>(){ public void live(NumericalPopulation p){} }, 
				new PopulationFactory() {
					
					@Override
					public Population createPopulation(List<Evolvable> currentPopulation) {
						NumericalPopulation pop = new NumericalPopulation();
						for( Evolvable ee : currentPopulation )
							pop.add((EvolvableSolution) ee);
						
						return pop;
						
					}
				}, selection, mutation, crossover );
		
		
		System.out.println("Answer: " + algo.evolveBestIndividual(10,new TerminationCondition() {
			
			@Override
			public boolean isOptimal(Evolvable evolvable) {
				return false;
			}
		}));
	}
	
	static class NumericalPopulation implements Population{
		
		List<Evolvable> population;
		EvolvableSolution best;

		public NumericalPopulation(){
			population = new ArrayList<>();
			best = null;
		}
		
		public void add(EvolvableSolution t){
			
			population.add( t );
			
			if( best == null ) best = t;
			else if( t.fitness() > best.fitness() )
				best = t;
		}
		
		@Override
		public Population clone() {
			NumericalPopulation np = new NumericalPopulation();
			
			for(Evolvable es : population)
				np.add(((EvolvableSolution) es).clone());
				
			return np;
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
			return best;
		}
		
		static class EvolvableSolution implements Evolvable{
			double x;
			public EvolvableSolution(double x) {
				this.x = x;
			}
			@Override
			public int compareTo(Evolvable o) {
				if( fitness() > o.fitness() )
					return 1;
				else if( fitness() < o.fitness() )
					return -1;
				else
					return 0;
			}

			@Override
			public double fitness() {
				return f(x);
			}

			@Override
			public Evolvable mate(Evolvable evolvable) {
				return new EvolvableSolution( (x-((EvolvableSolution)evolvable).x) );
			}

			@Override
			public void mutate() {
				x += new Random().nextGaussian();
			}
			
			public String toString(){
				return x+" ["+fitness()+"]";
			}
			
			public EvolvableSolution clone(){
				return new EvolvableSolution(x);
			}
		}
		
	}
	
	static SelectionOperator selection = new SelectionOperator() {
		
		@Override
		public List<Evolvable> selectIndividuals(Population population) {
			List<Evolvable>  ans =  Operators.selectIndividuals(population);
			if( ans.size() > MAX ) Collections.sort(ans);
			while( ans.size() > MAX ) ans.remove(ans.get(0));
			return ans;
		
		}
	};
	
	static CrossoverOperator crossover = new CrossoverOperator() {
		
		@Override
		public List<Evolvable> mateIndividuals(List<Evolvable> currentPopulation) {
			return Operators.mateIndividuals(currentPopulation);
		}
	};
	
	static MutationOperator mutation = new MutationOperator() {
		
		@Override
		public void mutateIndividuals(List<Evolvable> currentPopulation) {
			Operators.mutateIndividuals(currentPopulation);
		}
	};
}
