package evolution;

import java.util.List;


public class EvolutionaryAlgorithm<T extends Population> {
	private T initialPopulation;
	private Life<T> life;
	private PopulationFactory factory;
	
	private SelectionOperator selection;
	private MutationOperator mutation;
	private CrossoverOperator crossover;
	
	public EvolutionaryAlgorithm(T initialPopulation, 
			Life<T> life, PopulationFactory factory, 
			SelectionOperator selectionOperator,
			MutationOperator mutationOperator,
			CrossoverOperator crossoverOperator) {
		
		super();
		this.initialPopulation = initialPopulation;
		this.life = life;
		this.factory = factory;
		this.mutation = mutationOperator;
		this.crossover = crossoverOperator;
		this.selection = selectionOperator;
	}
	
	
	
	//Returns the 'optimal' population finded by the evolutionary algorithm
	@SuppressWarnings("unchecked")
	public Population evolve(int iterations, TerminationCondition condition){
		T population = (T) initialPopulation.clone();
		T bestPopulation = (T) population.clone();
		
		boolean out = false;
		while(iterations-- > 0 && !out){
			
			life.live(population); //'Lives', i.e Iterates a new cycle in the individuals life, in order to measure fitness
			   //This can be omitted and measure fitness directly from the genotype.
			
			//System.out.println(population.size() + " " + population.getBestIndividual().fitness());
			
			
			List<Evolvable> currentPopulation = selection.selectIndividuals(population); //Selection,TODO: to be improved by composition...
			
			currentPopulation = crossover.mateIndividuals(currentPopulation); //Reproduction,TODO: to be improved by composition...
			
			mutation.mutateIndividuals(currentPopulation); //Mutation ,TODO: to be improved by composition...
			
			population = (T) factory.createPopulation(currentPopulation); //Creates new Population
			
			
			
			for(Evolvable ind : population.individuals()) //If some individual is optimal we are done.
				if( condition.isOptimal(ind) ){
					out = true;
					break;
				}
			
			if(population.getBestIndividual().fitness() > bestPopulation.getBestIndividual().fitness()){
				bestPopulation = (T) population.clone();
			}
		}
		
		return bestPopulation;
	}
	
	/*
	 * Be careful, if the condition is not carefully implemented,
	 * it may never converge...
	 */
	public Population evolve(TerminationCondition condition){
		return evolve(Integer.MAX_VALUE,condition);
	}
	
	public Evolvable evolveBestIndividual(int iterations, TerminationCondition condition){
		return evolve(iterations,condition).getBestIndividual();
	}
	
	public Evolvable evolveBestIndividual(TerminationCondition condition){
		return evolve(Integer.MAX_VALUE,condition).getBestIndividual();
	}
	
	
	public Evolvable evolveBestIndividual(int iterations){
		return evolve(iterations, new TerminationCondition() {
			
			@Override
			public boolean isOptimal(Evolvable evolvable) {
				return false;
			}
		}).getBestIndividual();
	}

}
