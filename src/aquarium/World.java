package aquarium;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

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
	List<Fish> fishes;
	List<DrawingTreeEntry> plants;
	
	public World(){
		fishes = new ArrayList<>();
		plants = new ArrayList<>();
	}
	
	
	// Cartesian coordinates
	void addTree(LSystem system, int iterations, int x, int y) {
		plants.add(new DrawingTreeEntry(system, system.make(iterations), x, y));
	}
	

	
	void randomFishPopulation(int n){
		while(n-- > 0){
			fishes.add( RandomFishGenerator.randomFish() );		
		}
		
	}
	
	/*
	 * Draws fishes Ohh not surprising...
	 */
	public void drawFishes(Graphics2D g){
		for(Fish fish : fishes){
			fish.draw(g);
		}
	}
	
	/*
	 * Draws plants
	 */
	public void drawPlants(Graphics2D g){
		Dimension dim = Controller.getDimension();
		for(DrawingTreeEntry plant : plants)
			plant.draw(g,(int)dim.getWidth(),(int)dim.getHeight());
	}
	
	public void drawBackground(Graphics2D g){
		System.out.println("No background yet..");
		//JEJEJE :v
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
	}
	
	
	
}
