package evolution;

public interface Evolvable extends Comparable<Evolvable> {
	
	//Returns the fitness value
	double fitness();

	//Crossover
	Evolvable mate(Evolvable evolvable);
	
	
	//Mutates the individual, MUST mutate.
	void mutate();
	
}
