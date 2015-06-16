package fishes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;

import plants.DrawingTreeEntry;
import DRSystem.ActivatorInhibitorSystem;
import affineTransforms.NonLinearTransform;
import aquarium.Controller;
import aquarium.ObservableEntity;
import aquarium.RandomUtils;
import aquarium.World.EmptyPoint;
import fishes.FeedingGenotype.Type;
import fishes.MorphologyGenotype.BendAction;
import fishes.MorphologyGenotype.BendAction.BendType;
import fishes.MovementGenotype.ProbableMovement;

public class Fish implements ObservableEntity {

	protected ActivatorInhibitorSystem system;
	protected BufferedImage image;
	protected int colours[];
	protected FishGenotype genotype;
	protected int width, height;
	protected int energy;
	protected int age;

	public Fish(BufferedImage fishTemplate, FishGenotype fishGenotype, int x,
			int y, int width, int height) {
		this.image = fishTemplate;
		this.genotype = fishGenotype;
		this.width = width;
		this.height = height;
		colours = new int[256];
		colours[0] = genotype.skinGenotype.color1.getRGB();
		colours[1] = genotype.skinGenotype.color2.getRGB();

		for (int i = 2; i < colours.length; i++)
			colours[i] = new Color(colours[i - 1] | colours[i - 2]).getRGB();

		SkinGenotype sk = genotype.skinGenotype;
		system = new ActivatorInhibitorSystem(sk.s, sk.Da, sk.Db, sk.ra, sk.rb,
				sk.ba, sk.bb, image.getWidth(), image.getHeight(), x, y,
				sk.iterations);

		for (BendAction ba : genotype.morphologyGenotype.bends) {
			if (ba.type.equals(BendType.VERTICAL))
				verticalBend(ba.offset);
			else
				horizontalBend(ba.offset);
		}

		energy = genotype.maximumLevelOfEnergy;
		age = 0;
	}

	public Point getUbication() {
		return new Point(system.getX(), system.getY());
	}

	@Deprecated
	public Fish(Color c1, Color c2, double s, double da, double db, double ra,
			double rb, double ba, double bb, int x, int y, int iterations) {
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
	
	
	public Rectangle getVisionBoundingBox(){
		Point coord = getUbication();
		int range = getVisionRange();
		coord.x -= range;
		coord.y -= range;
		Dimension dim = getSize();
		dim.setSize(dim.getWidth()+range, dim.getHeight()+range);
		
		
		return new Rectangle(coord, dim);
	}

	public void draw(Graphics2D g) {
		// system.step();
		
		Rectangle bbox = getVisionBoundingBox();
		g.draw(bbox);
		system.draw(g, image, colours, width, height);
	}

	public int getVisionRange() {
		return genotype.visionRange;
	}

	@Override
	public Rectangle getBoundingBox() {
		return new Rectangle(getUbication(), new Dimension(image.getWidth(),
				image.getHeight()));
	}

	public void decreaseEnergy() {
		energy--;
	}

	private Type getType() {
		return genotype.feedingGenotype.type;
	}

	public List<Fish> act(List<ObservableEntity> observableObjects) {

		ArrayList<Fish> fishes = new ArrayList<>();
		ArrayList<DrawingTreeEntry> plants = new ArrayList<>();
		for (ObservableEntity oe : observableObjects)
			if (oe instanceof Fish)
				fishes.add((Fish) oe);
			else if (oe instanceof DrawingTreeEntry)
				plants.add((DrawingTreeEntry) oe);
		
		
		List<Fish> offspring = new Vector<Fish>();
//		if( age >= genotype.reproductionAge )
//			offspring = mate(fishes);

		if (getType().equals(FeedingGenotype.Type.CARNIVORE)
				|| getType().equals(FeedingGenotype.Type.OMNIVORE))
			eatFishes(fishes);

		if (getType().equals(FeedingGenotype.Type.OMNIVORE)
				|| getType().equals(FeedingGenotype.Type.HERBIVORE))
			eatPlants(plants);

		move(observableObjects);
		
		return offspring;
	}

	List<Fish> mate(List<Fish> fishes) {
		
		if(fishes.size() > 0) System.err.println(fishes.size()+" To mate..");
		
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

	void eatPlants(List<DrawingTreeEntry> plants) {
		if (plants.isEmpty())
			return;
		System.out.println("Eating plants...");
		increaseEnergy(1);
	}

	void eatFishes(List<Fish> fishes) {
		if (fishes.isEmpty())
			return;
		Random r = new Random();
		Fish dam = fishes.get(r.nextInt(fishes.size()));

		dam.energy = 0;
		System.out.println("Eating Fishes...");
		increaseEnergy(dam.genotype.maximumLevelOfEnergy);
	}

	private void increaseEnergy(int plus) {
		energy = Math.min(genotype.maximumLevelOfEnergy, energy + plus);
	}

	void move(List<ObservableEntity> observableObjects) {
		int fishes = 0, plants = 0, nothing = 0;
		for (ObservableEntity oe : observableObjects) {
			if (oe instanceof Fish)
				fishes++;
			else if (oe instanceof DrawingTreeEntry)
				plants++;
			else if (oe instanceof EmptyPoint)
				nothing++;
		}

		List<ProbableMovement> movements = genotype.movementGenotype
				.getSortedMovements();

		Queue<Integer> toDelete = new LinkedList<>();
		for (int i = 0; i < movements.size(); i++) {
			ProbableMovement pm = movements.get(i);
			if (fishes == 0 && pm.type.equals(MovementGenotype.FISH))
				toDelete.add(i);
			else if (plants == 0 && pm.type.equals(MovementGenotype.PLANT))
				toDelete.add(i);
			else if (nothing == 0 && pm.type.equals(MovementGenotype.NOTHING))
				toDelete.add(i);
		}

		while (!toDelete.isEmpty())
			movements.remove(toDelete.poll());

		double acm[] = new double[movements.size()];

		acm[0] = movements.get(0).probability;
		for (int i = 1; i < acm.length; i++)
			acm[i] = acm[i - 1] + movements.get(i).probability;

		double roulette = Math.random();
		int i;
		for (i = 0; i < acm.length; i++) {
			if (roulette <= acm[i])
				break;
		}

		if (movements.get(i).type.equals(MovementGenotype.STAY))
			return;
		decreaseEnergy();
		Collections.shuffle(observableObjects);
		for (ObservableEntity oe : observableObjects) {
			if ((oe instanceof Fish)
					&& movements.get(i).type.equals(MovementGenotype.FISH)) {
				doMove(oe.getUbication());
				break;
			} else if ((oe instanceof DrawingTreeEntry)
					&& movements.get(i).type.equals(MovementGenotype.PLANT)) {
				doMove(oe.getUbication());
				break;
			} else if ((oe instanceof EmptyPoint)
					&& movements.get(i).type.equals(MovementGenotype.NOTHING)) {
				doMove(oe.getUbication());
				break;
			}
		}
	}

	void doMove(final Point p) {
		ArrayList<Point> tmp = new ArrayList<>();
		tmp.add(new Point(p.x - 1, p.y));
		tmp.add(new Point(p.x - 1, p.y + 1));
		tmp.add(new Point(p.x - 1, p.y - 1));

		tmp.add(new Point(p.x, p.y - 1));
		tmp.add(new Point(p.x, p.y + 1));
		tmp.add(new Point(p.x, p.y));

		tmp.add(new Point(p.x + 1, p.y));
		tmp.add(new Point(p.x + 1, p.y + 1));
		tmp.add(new Point(p.x + 1, p.y - 1));

		Collections.sort(tmp, new Comparator<Point>() {

			@Override
			public int compare(Point o1, Point o2) {
				double d1 = o1.distance(p);
				double d2 = o1.distance(p);
				if (d1 < d2)
					return -1;
				else if (d1 > d2)
					return 1;
				else
					return 0;
			}

		});

		// Performs movement..
		Point target = tmp.get(0);

		Dimension dim = Controller.getDimension();
		if (target.x > dim.getWidth())
			target.x = 0;
		if (target.y > dim.getHeight())
			target.y = 0;
		if (target.x < 0)
			target.x = (int) (dim.getWidth());
		if (target.y < 0)
			target.y = (int) (dim.getHeight());

		system.setX(target.x);
		system.setY(target.y);

	}

	public boolean isAlive() {
		return energy > 0;
	}

	public void increaseAge() {
		age++;
	}

	public void setWidth(int i) {
		width = i;
	}
	
	public void setHeight(int h){
		height = h;
	}

}
