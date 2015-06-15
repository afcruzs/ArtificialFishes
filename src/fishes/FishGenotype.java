package fishes;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import fishes.MorphologyGenotype.BendAction;

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

	static FishGenotype[] crossover(FishGenotype gen1, FishGenotype gen2) {
		Object[] geneticCode1 = createRawGeneticCode(gen1);
		Object[] geneticCode2 = createRawGeneticCode(gen2);
		int pivot = new Random().nextInt(geneticCode1.length);

		Object[] offspring1 = new Object[geneticCode1.length];
		Object[] offspring2 = new Object[geneticCode1.length];

		for (int i = 0; i < pivot; i++) {
			offspring1[i] = geneticCode1[i];
			offspring2[i] = geneticCode2[i];
		}

		for (int i = pivot; i < geneticCode1.length; i++) {
			offspring1[i] = geneticCode2[i];
			offspring2[i] = geneticCode1[i];
		}

		return new FishGenotype[] { decodeGeneticCode(offspring1),
				decodeGeneticCode(offspring2) };
	}

	@SuppressWarnings("unchecked")
	static FishGenotype decodeGeneticCode(Object[] code) {

		return new FishGenotype(

		new SkinGenotype((Double) code[13], (Double) code[8], (Double) code[9],
				(Double) code[11], (Double) code[12], (Double) code[4],
				(Double) code[5], (Color) code[6], (Color) code[7],
				(Integer) code[10]),

		new FeedingGenotype((FeedingGenotype.Type) code[3]),

		new MorphologyGenotype((List<BendAction>) code[14]),

		new MovementGenotype((Double) code[15], (Double) code[17],
				(Double) code[18], (Double) code[16]),

		(Integer) code[0], (Integer) code[1], (Integer) code[2]);
	}

	static Object[] createRawGeneticCode(FishGenotype geno) {
		Object[] code = new Object[19];

		code[0] = geno.reproductionAge;
		code[1] = geno.maximumLevelOfEnergy;
		code[2] = geno.visionRange;
		code[3] = geno.feedingGenotype.type;
		code[4] = geno.skinGenotype.ba;
		code[5] = geno.skinGenotype.bb;
		code[6] = geno.skinGenotype.color1;
		code[7] = geno.skinGenotype.color2;
		code[8] = geno.skinGenotype.Da;
		code[9] = geno.skinGenotype.Db;
		code[10] = geno.skinGenotype.iterations;
		code[11] = geno.skinGenotype.ra;
		code[12] = geno.skinGenotype.rb;
		code[13] = geno.skinGenotype.s;
		code[14] = geno.morphologyGenotype.bends;
		code[15] = geno.movementGenotype.moveToFishProbability;
		code[16] = geno.movementGenotype.moveToNothingProbability;
		code[17] = geno.movementGenotype.moveToPlantProbability;
		code[18] = geno.movementGenotype.stayProbability;
		return code;
	}

	public static void mutate(FishGenotype fishGenotype) {
		Random r = new Random();
		/*
		 * SkinGenotype skinGenotype; FeedingGenotype feedingGenotype;
		 * MorphologyGenotype morphologyGenotype; MovementGenotype
		 * movementGenotype;
		 * 
		 * int reproductionAge; int maximumLevelOfEnergy; int visionRange;
		 */
		int op = r.nextInt(7);
		switch (op) {
			case 0:
				fishGenotype.skinGenotype.mutate();
				break;
			case 1:
				fishGenotype.feedingGenotype.mutate();
				break;
	
			case 2:
				fishGenotype.morphologyGenotype.mutate();
				break;
	
			case 3:
				fishGenotype.movementGenotype.mutate();
				break;
	
			case 5:
	
				fishGenotype.reproductionAge += r
						.nextInt(fishGenotype.reproductionAge) * r.nextGaussian();
				break;
			case 6:
				fishGenotype.maximumLevelOfEnergy += r
						.nextInt(fishGenotype.maximumLevelOfEnergy) * r.nextGaussian();
				break;
				
			default :
				fishGenotype.visionRange += r
						.nextInt(fishGenotype.visionRange) * r.nextGaussian();
				break;
		}
	}

}
