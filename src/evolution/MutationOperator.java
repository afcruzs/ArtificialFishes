package evolution;

import java.util.List;

public interface MutationOperator {
	void mutateIndividuals(List<Evolvable> currentPopulation);
}
