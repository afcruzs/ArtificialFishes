package evolution;

import java.util.List;

public interface SelectionOperator {
	List<Evolvable> selectIndividuals(Population population);
}
