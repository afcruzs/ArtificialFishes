package aquarium;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import plants.Bushy;
import plants.CustomStickyTree;
import plants.DrawingTreeEntry;
import plants.LSystem;
import plants.Plant2;
import plants.Plant3;
import plants.StickyTree;
import fishes.Fish;
import fishes.RandomFishGenerator;

public class World {

	// Constants that represents what a fish can see
	static final String FISH = "fish", PLANT = "plant", NOTHING = "nothing";

	List<Fish> fishes;
	List<DrawingTreeEntry> plants;

	public World() {
		fishes = new ArrayList<>();
		plants = new ArrayList<>();
	}

	/*
	 * Performs an iteration of the fishes's world
	 */
	public void iterate() {
		for (Fish fish : fishes) {
			fish.move( getObservableObjects(fish)  );
			fish.decreaseEnergy();
		}
	}

	public List<ObservableEntity> getObservableObjects(Fish fish) {
		Point coord = fish.getUbication();
		int range = fish.getVisionRange();
		coord.x -= range;
		coord.y -= range;
		Dimension dim = fish.getSize();
		dim.setSize(dim.getWidth() + range, dim.getHeight() + range);

		Rectangle bbox = new Rectangle(coord, dim);

		ArrayList<ObservableEntity> l = new ArrayList<>();

		for (Fish f : fishes) {
			if (fish.equals(f))
				continue;
			if (bbox.contains(f.getUbication()))
				l.add(f);
		}

		for (DrawingTreeEntry plant : plants) {
			if (bbox.contains(plant.getUbication()))
				l.add(plant);
		}

		// Getting nowhere areas... A random approach

		Rectangle[] areas = GeometryUtils.difference(bbox,
				new Rectangle(fish.getUbication(), fish.getSize()));
		int idx ,ctr=0,x,y;
		Random r = new Random();
		
		int maxm = (fish.getHeight()*fish.getWidth());
		while( ctr++ < maxm && areas.length > 0 ){
			idx = r.nextInt(areas.length);
			x = (int) (areas[idx].getX() + ( r.nextBoolean() ? 1.0 : -1.0 )* 50 * Math.random());
			y = (int) (areas[idx].getY() + ( r.nextBoolean() ? 1.0 : -1.0 )* 50 * Math.random());
			Point tmp = new Point(x,y);
			boolean flag = true;
			for( ObservableEntity oe : l ){
				if( oe.getBoundingBox().contains(tmp)  ){
					flag = false;
					break;
				}
			}
			
			if(flag){
				l.add(new EmptyPoint(x, y));
				break;
			}
		}
		return l;
	}

	// Cartesian coordinates
	void addTree(LSystem system, int iterations, int x, int y) {
		plants.add(new DrawingTreeEntry(system, system.make(iterations), x, y));
	}

	void randomFishPopulation(int n) {
		while (n-- > 0) {
			fishes.add(RandomFishGenerator.randomFish());
		}

	}

	/*
	 * Draws fishes Ohh not surprising...
	 */
	public void drawFishes(Graphics2D g) {
		for (Fish fish : fishes) {
			fish.draw(g);
		}
	}

	/*
	 * Draws plants
	 */
	public void drawPlants(Graphics2D g) {
		Dimension dim = Controller.getDimension();
		for (DrawingTreeEntry plant : plants)
			plant.draw(g, (int) dim.getWidth(), (int) dim.getHeight());
	}

	public void drawBackground(Graphics2D g) {
		System.out.println("No background yet..");
		// JEJEJE :v
	}

	/*
	 * Draws the components
	 */
	public void draw(Graphics2D g) {
		drawBackground(g);
		drawFishes(g);
		drawPlants(g);
	}

	public void start() {
		randomFishPopulation(10);
		// Left side
		addTree(new Plant3(), 7, 50, 8);
		addTree(new Plant3(), 7, 200, 10);
		addTree(new StickyTree(), 7, 400, 0);

		// 'corals'
		addTree(new Plant2(8), 8, 550, 0);
		addTree(new Plant2(8), 8, 650, 0);
		addTree(new Plant2(8), 8, 750, 0);
		addTree(new Plant2(8), 8, 850, 0);
		addTree(new Plant2(8), 8, 950, 0);

		// Right side
		addTree(new CustomStickyTree(), 7, 1150, 0);
		addTree(new Bushy(), 5, 1250, 0);
		addTree(new Bushy(), 5, 1350, 0);
		
		Controller.startEvolution();
	}

	/*
	 * This inner class represents an empty point that is "observable" from the
	 * fish, this is in order to let the fish be able to move where there is
	 * nothing.
	 */
	public class EmptyPoint implements ObservableEntity {
		private int x, y;

		public EmptyPoint(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public Point getUbication() {
			return new Point(x,y);
		}

		@Override
		public Rectangle getBoundingBox() {
			return new Rectangle(getUbication(),new Dimension(1,1));
		}

	}

}
