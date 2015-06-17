package evolution;

import java.util.List;

public interface CrossoverOperator {
	List<Evolvable> mateIndividuals(List<Evolvable> currentPopulation);
}
