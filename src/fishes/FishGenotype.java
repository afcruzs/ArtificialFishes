package fishes;

public class FishGenotype {
	SkinGenotype skinGenotype;
	FeedingGenotype feedingGenotype;
	MorphologyGenotype morphologyGenotype;
	MovementGenotype movementGenotype;
	
	int reproductionAge;
	int maximumLevelOfEnergy;
	int visionRange;
	
	public FishGenotype(SkinGenotype skinGenotype,
			FeedingGenotype feedingGenotype,
			MorphologyGenotype morphologyGenotype,
			MovementGenotype movementGenotype, int reproductionAge,
			int maximumLevelOfEnergy, int visionRange) {
	
		super();
		this.skinGenotype = skinGenotype;
		this.feedingGenotype = feedingGenotype;
		this.morphologyGenotype = morphologyGenotype;
		this.movementGenotype = movementGenotype;
		this.reproductionAge = reproductionAge;
		this.maximumLevelOfEnergy = maximumLevelOfEnergy;
		this.visionRange = visionRange;
	}
	
	
}
