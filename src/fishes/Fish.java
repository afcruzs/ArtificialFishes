package fishes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.MediaSize.Other;

import DRSystem.ActivatorInhibitorSystem;
import affineTransforms.NonLinearTransform;
import aquarium.ColorUtils;
import aquarium.ObservableEntity;
import aquarium.RandomUtils;
import fishes.FeedingGenotype.Type;
import fishes.MorphologyGenotype.BendAction;
import fishes.MorphologyGenotype.BendAction.BendType;
import flocking.FlockingAgent;
import flocking.FlockingVector;
import flocking.SegregationFlockingAgent;

public class Fish extends SegregationFlockingAgent implements ObservableEntity {

	protected ActivatorInhibitorSystem system;
	protected BufferedImage image;
	protected int colours[];
	protected FishGenotype genotype;
	protected int width, height;
	private boolean iterated;
	private boolean predator;
	private int energy;
	public Fish(BufferedImage fishTemplate, FishGenotype fishGenotype, int x,
			int y, int width, int height) {
		super(new Point(x,y), new FlockingVector(RandomUtils.randInt(-300, 300), RandomUtils.randInt(-300, 300)) );
		
		//System.err.println(NEIGHBOR_RAIDUS);
		this.image = fishTemplate;
		this.genotype = fishGenotype;
		this.width = width;
		this.height = height;
		
		colours = new int[256];
		colours[0] = genotype.skinGenotype.color1.getRGB();
		colours[1] = genotype.skinGenotype.color2.getRGB();

		for (int i = 2; i < colours.length; i++)
			colours[i] = i % 5 == (i - 1) % 3 ? colours[0]
					: i % 7 == 1 ? colours[1] : i % 17 == 7 ? ColorUtils
							.blend(new Color(colours[i - 1]),
									new Color(colours[i - 2])).brighter()
							.getRGB() : ColorUtils
							.blend(new Color(colours[i - 1]),
									new Color(colours[i - 2])).darker()
							.getRGB();

		SkinGenotype sk = genotype.skinGenotype;
		system = new ActivatorInhibitorSystem(sk.s, sk.Da, sk.Db, sk.ra, sk.rb,
				sk.ba, sk.bb, width, height, x, y,
				sk.iterations);

		for (BendAction ba : genotype.morphologyGenotype.bends) {
			if (ba.type.equals(BendType.VERTICAL))
				verticalBend(ba.offset);
			else
				horizontalBend(ba.offset);
		}

		iterated = false;
		predator = Math.random() <= 0.08;
	//	iterateSystem();
		system.step();
		paintFish();
		
		energy = 10;
	//	system.averageColor = ColorUtils.blend(new Color(colours[0]), new Color(colours[1]));
	}
	
	public void paintFish(){
		system.paintFish(image,colours);
	}

	public Point getUbication() {
		return new Point(system.getX(), system.getY());
	}

	@Deprecated
	public Fish(Color c1, Color c2, double s, double da, double db, double ra,
			double rb, double ba, double bb, int x, int y, int iterations) {
		super(new Point(x,y), new FlockingVector(10, 10) );
		colours = new int[256];
		colours[0] = c1.getRGB();
		colours[1] = c2.getRGB();
		for (int i = 2; i < colours.length; i++) {
			colours[i] = new Color(colours[i - 1] | colours[i - 2]).getRGB();
		}
		image = null;
		try {
			image = ImageIO.read(new File("fish0.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int rows = image.getWidth(), cols = image.getHeight();
		system = new ActivatorInhibitorSystem(s, da, db, ra, rb, ba, bb, rows,
				cols, x, y, iterations);

	}

	public void horizontalBend(int offset) {
		image = NonLinearTransform.bendHorizontal(image, offset);
		updateSystem();
	}

	private void updateSystem() {
		int rows = image.getWidth(), cols = image.getHeight();
		system = system.lightCopy(rows, cols);
	}

	public void verticalBend(int offset) {
		image = NonLinearTransform.bendVertical(image, offset);
		updateSystem();
	}

	public int areaRows() {
		return system.getRows();
	}

	public int areaCols() {
		return system.getCols();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Dimension getSize() {
		return new Dimension(getWidth(), getHeight());
	}

	public void setX(int x) {
		system.setX(x);
	}

	public void setY(int y) {
		system.setY(y);
	}

	public int getX() {
		return system.getX();
	}

	public int getY() {
		return system.getY();
	}
	
	

	
	public void act( Vector<FlockingAgent> neighbors ){
		super.act(neighbors);
		Point position = getPosition();
		system.setX(position.x);
		system.setY(position.y);
	}
	
	public void drawInCorner(Graphics g){
		iterateSystem();
		g.drawImage(image, 0, 0, null);
		g.drawString(energy+"", 10, 10);
	}
	
	@Override
	public void draw(Graphics g) {
		draw((Graphics2D)g);
	}

	public void draw(Graphics2D g) {
		system.draw(g, image, colours, width, height);
		if( predator ){
			g.setColor(Color.RED);
			g.drawRect(system.getX(), system.getY(), 30, 30);
			
		}
	}


	@Override
	public Rectangle getBoundingBox() {
		return new Rectangle(getUbication(), new Dimension(width, height));
	}

	public void decreaseEnergy() {
		energy--;
	}

	private Type getType() {
		return genotype.feedingGenotype.type;
	}

	
	
	public static double colorDifference(Fish f1, Fish f2){
//		//Set size equal ...
//		double sum = 0.0;
//		int rows = Math.min(f1.image.getHeight(),f2.image.getHeight())-1;
//		int cols = Math.min(f1.image.getWidth(),f2.image.getWidth())-1;
////		System.out.println(rows+" "+cols);
////		System.out.println(f1.image.getHeight()+" "+f1.image.getWidth());
////		System.out.println(f2.image.getHeight()+" "+f2.image.getWidth());
//		int cnt = 0;
//		for(int i=0; i<cols; i++){
//			for(int j=0; j<rows; j++){
//				if( ColorUtils.isTransparent(f1.image.getRGB(i,j)) || ColorUtils.isTransparent(f1.image.getRGB(i,j)) )
//					continue;
//				cnt++;
//				Color e1 = null,e2 = null;
//				e1 = new Color(f1.image.getRGB(i,j));
//				e2 = new Color(f2.image.getRGB(i,j));
//				sum += ColorUtils.ColourDistance(e1, e2);
//				
//			}
//		}
//		
//		return sum/(cnt);
//		
//		double d1 = ColorUtils.ColourDistance(f1.getColor1(), f2.getColor1());
//		double d2 = ColorUtils.ColourDistance(f1.getColor1(), f2.getColor2());
//		return (d1+d2)/2.0;
		return ColorUtils.ColourDistance(f1.system.averageColor, f2.system.averageColor);
	}

	public List<Fish> mate(List<Fish> fishes) {
		
		//if(fishes.size() > 0) System.err.println(fishes.size()+" To mate..");
		
		List<Fish> children = new ArrayList<Fish>();
		for (Fish coup : fishes) {
			
			//Crossover
			FishGenotype[] gens = FishGenotype.crossover(genotype,
					coup.genotype);
			
			//Mutation
			FishGenotype.mutate(gens[0]);
			FishGenotype.mutate(gens[1]);
			
			//Point p1 = Controller.randomPointInWorld();
			Point p1 = new Point(300,300);
			children.add(new Fish(image, gens[0], p1.x, p1.y
					+ RandomUtils.randInt(-10, 10), width, height));
			
			//p1 = Controller.randomPointInWorld();
			children.add(new Fish(image, gens[1], p1.x,p1.y
					+ RandomUtils.randInt(-10, 10), width, height));
		}

		return children;

	}



	public void setWidth(int i) {
		width = i;
	}
	
	public void setHeight(int h){
		height = h;
	}

	public Color getColor1() {
		return genotype.skinGenotype.color1;
	}
	
	public Color getColor2() {
		return genotype.skinGenotype.color2;
	}

	@Override
	public boolean segregationFunction(SegregationFlockingAgent agent) {
		boolean other = ((Fish)agent).isPredator();
		if( isPredator() && !other ) return false;
		if( !isPredator() && other ) return true;
		return colorDifference(this, (Fish)agent) >= 0.4;
	}

	public void iterateSystem() {
		if( !iterated )
			system.iterateSystem();
		iterated = true;
		paintFish();
	}

	public int getImageWidth() {
		return image.getWidth();
	}
	
	public int getImageHeight(){
		return image.getHeight();
	}
	
	public String toString(){
		return genotype.toString();
	}

	public boolean isPredator() {
		return predator;
	}

	public int getEnergy() {
		return energy;
	}

}
