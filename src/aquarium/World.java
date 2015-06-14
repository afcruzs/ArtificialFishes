package aquarium;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import plants.DrawingTreeEntry;
import fishes.Fish;
import fishes.RandomFishGenerator;

public class World {
	List<Fish> fishes;
	List<DrawingTreeEntry> plants;
	
	public World(){
		fishes = new ArrayList<>();
		plants = new ArrayList<>();
		
		//randomFishPopulation(2);
		
	}
	
	public void randomFishPopulation(int n){
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
		randomFishPopulation(3);
	}
	
	
	
}
