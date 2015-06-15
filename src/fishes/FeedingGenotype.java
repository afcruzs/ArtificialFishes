package fishes;

import java.util.Random;

public class FeedingGenotype {
	
	public enum Type{CARNIVORE,OMNIVORE,HERBIVORE};
	protected Type type;
	
	public FeedingGenotype(Type type) {
		super();
		this.type = type;
		
	}

	public void mutate() {
		Random r = new Random();
		int op = r.nextInt(3);
		switch (op) {
		case 0:
			type = Type.CARNIVORE;
			break;
		case 1:
			type = Type.OMNIVORE;
			break;
			
		default:
			type = Type.HERBIVORE;
			break;
		}
	}
	
	
}
