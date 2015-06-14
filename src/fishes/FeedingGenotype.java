package fishes;

public class FeedingGenotype {
	
	public enum Type{CARNIVORE,OMNIVORE,HERBIVORE};
	protected Type type;
	
	public FeedingGenotype(Type type) {
		super();
		this.type = type;
		
	}
	
	
}
