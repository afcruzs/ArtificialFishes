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
import fishes.FeedingGenotype.Type;

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
	
	/**
	 * Returns a pseudo-random number between min and max, inclusive.
	 * The difference between min and max can be at most
	 * <code>Integer.MAX_VALUE - 1</code>.
	 *
	 * @param min Minimum value
	 * @param max Maximum value.  Must be greater than min.
	 * @return Integer between min and max, inclusive.
	 * @see java.util.Random#nextInt(int)
	 */
	public static int randInt(int min, int max) {

	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	
	public static double randDouble(double start, double end) {

		double random = new Random().nextDouble();
		return start + (random * (end - start));
	}
	
	public static Fish randomFish() {
		Random rand = new Random();
		
		SkinGenotype skinGenotype = new SkinGenotype( randDouble(10.0,30.0), randDouble(10.0,30.0), randDouble(10.0,30.0), 
				randDouble(10.0,30.0), randDouble(10.0,30.0), randDouble(10.0,30.0), randDouble(10.0,30.0),
				randomColor(), randomColor(), rand.nextInt(200));
		
		FeedingGenotype feedingGenotype = new FeedingGenotype(randomType());
		
		MorphologyGenotype morphologyGenotype = new MorphologyGenotype();
		
		MovementGenotype movementType = new MovementGenotype();
		
		int reproductionAge = rand.nextInt(10);
		int maximumLevelOfEnergy = rand.nextInt(30);
		int visionRange = rand.nextInt(7);
		
		FishGenotype fishGenotype = new FishGenotype(skinGenotype, feedingGenotype,
				morphologyGenotype, movementType, reproductionAge,
				maximumLevelOfEnergy, visionRange);
		
		BufferedImage fishTemplate = null;
		try {
			fishTemplate = ImageIO.read(new File("fish0.png"));
		} catch (IOException e) { e.printStackTrace(); } 
		
		int width = randInt(50,100);
		int height = randInt(50,100);
		Dimension dim = Controller.getDimension();
		//Dimension dim = new Dimension(300,300);
		int x = randInt(10,dim.width);
		int y = randInt(10,dim.height);
		return new Fish(fishTemplate, fishGenotype, x, y, width, height);
	}
}
