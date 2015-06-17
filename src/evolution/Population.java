package evolution;

import java.util.Collection;

public interface Population extends Cloneable {

	Population clone();

	Collection<Evolvable> individuals();
	
	int size();

	Evolvable getBestIndividual();
}
