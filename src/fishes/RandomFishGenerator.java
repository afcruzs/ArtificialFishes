package fishes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import aquarium.Controller;
import aquarium.RandomUtils;
import fishes.FeedingGenotype.Type;
import fishes.MorphologyGenotype.BendAction.BendType;

public class RandomFishGenerator {
	
	
	
	private  static Color randomColor(){
		Random rand = new Random();
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		return new Color(r,g,b);
	}
	
	private static Type randomType(){
		Random rand = new Random();
		int x = rand.nextInt(3);
		switch (x) {
			case 0:
				return Type.CARNIVORE;
			case 1:
				return Type.HERBIVORE;
			
			case 2:
				return Type.OMNIVORE;
			
			default:
				throw new IllegalArgumentException("There is no more types of feeding");
		}
	}
	
	static BufferedImage scaleImage(BufferedImage before, int width, int height){
		int w = before.getWidth();
		int h = before.getHeight();
		
		
		double ratioH = (double)height/(double)h;
		double ratioW = (double)width/(double)w;
				
		
		BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		AffineTransform at = new AffineTransform();
		System.out.println(ratioH + " " + ratioW);
		at.scale(ratioH, ratioW);
		AffineTransformOp scaleOp = 
		   new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		after = scaleOp.filter(before, after);
		
		return after;
	}
	
	
	
	static BendType randomBendType(){
		return new Random().nextBoolean() ? BendType.HORIZONTAL : BendType.VERTICAL;
	}
	
	static MovementGenotype randomMovementGenotype(){
		Random r = new Random();
		double a = (double)r.nextInt(100), b = (double)r.nextInt(100), c = (double)r.nextInt(100), d = (double)r.nextInt(100);
		double N = a+b+c+d;
		
		return new MovementGenotype( a/N , b/N, c/N, d/N );
		
	}
	
	public static Fish randomFish(int sz) {
		Random rand = new Random();
		
		SkinGenotype skinGenotype = new SkinGenotype( RandomUtils.randDouble(10.0,30.0), RandomUtils.randDouble(10.0,30.0), RandomUtils.randDouble(10.0,30.0), 
				RandomUtils.randDouble(10.0,30.0), RandomUtils.randDouble(10.0,30.0), RandomUtils.randDouble(10.0,30.0), RandomUtils.randDouble(10.0,30.0),
				randomColor(), randomColor(), rand.nextInt(200));
		
		FeedingGenotype feedingGenotype = new FeedingGenotype(randomType());
		
		MorphologyGenotype morphologyGenotype = new MorphologyGenotype();
		morphologyGenotype.addBendAction( randomBendType() , ( rand.nextBoolean() ? -1 : 1 )*rand.nextInt(50) );
		
		MovementGenotype movementType = randomMovementGenotype();
		
		int reproductionAge = RandomUtils.randInt(1, 10);
		int maximumLevelOfEnergy = RandomUtils.randInt(1, 150);
		int visionRange = RandomUtils.randInt(1, 7);
		
		FishGenotype fishGenotype = new FishGenotype(skinGenotype, feedingGenotype,
				morphologyGenotype, movementType, reproductionAge,
				maximumLevelOfEnergy, visionRange);
		
		BufferedImage fishTemplate = null;
		try {
			fishTemplate = ImageIO.read(new File("fish0.png"));
		} catch (IOException e) { e.printStackTrace(); } 
		
		int width = RandomUtils.randInt(sz/2,sz);
		int height = RandomUtils.randInt(sz/2,sz);
	//	Dimension dim = Controller.getDimension();
		Dimension dim = new Dimension(500,500);
		int x = RandomUtils.randInt(10,dim.width);
		int y = RandomUtils.randInt(10,dim.height);
		return new Fish(fishTemplate, fishGenotype, x, y, width, height);
	}
}
