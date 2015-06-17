package evolution;

import java.util.List;

public interface PopulationFactory {

	Population createPopulation(List<Evolvable> currentPopulation);

}
