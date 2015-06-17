package evolution;

public interface Life<T extends Population> {
	//The population 'lives', this method MUST
	//modify the population reference itself.
	void live(T population);
}
