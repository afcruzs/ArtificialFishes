package aquarium;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Vector;

import javax.swing.JOptionPane;

import plants.Bushy;
import plants.CustomStickyTree;
import plants.DrawingTreeEntry;
import plants.LSystem;
import plants.Plant2;
import plants.Plant3;
import plants.StickyTree;
import fishes.Fish;
import fishes.RandomFishGenerator;
import flocking.FlockingAgent;
import flocking.SegregationFlockingAgent;

public class World {

	// Constants that represents what a fish can see

	Vector<FlockingAgent> fishes;
	List<DrawingTreeEntry> plants;

	public World() {
		fishes = new Vector<>();
		plants = new Vector<>();
	}
	
	void initAgents(int N) {
		for (int i = 0; i < N; i++) {
			 SegregationFlockingAgent a1 = RandomFishGenerator.randomFish(Controller.getFishSize());

			 fishes.add(a1);
		}
	}
	
	void singleStep(){
		for(FlockingAgent fish : fishes){
			fish.act(fishes);
			torus(fish);
		}
		
		Controller.callBackOnIteration();
	}
	
	public void iterate(int iterations){
		while(iterations-- > 0){
			singleStep();
		}
	}
	
	static void torus(FlockingAgent ag) {
		
		Dimension dim = Controller.getDimension();
		Point position = ag.getPosition();
		if (position.getX() > dim.getWidth())
			position.x = 0;
		if (position.getY() > dim.getHeight()-50)
			position.y = (int) (dim.getHeight()-50);

		if (position.x <= 0)
			position.x = (int) dim.getWidth();
		if (position.y <= 50)
			position.y = 50;

		ag.setPosition(position.x, position.y);
	}



	// Cartesian coordinates
	void addTree(LSystem system, int iterations, int x, int y) {
		plants.add(new DrawingTreeEntry(system, system.make(iterations), x, y));
	}

	/*
	 * Draws fishes Ohh not surprising...
	 */
	public synchronized void drawFishes(Graphics2D g) {
		for (FlockingAgent fish : fishes) {
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
	}

	/*
	 * Draws the components
	 */
	public void draw(Graphics2D g) {
		drawBackground(g);
		drawFishes(g);
		drawPlants(g);
	}

	public void birth(int numberOfFishes) {
		initAgents(numberOfFishes);
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
	
	int countNeighbors(Fish fish){
		int ctr = 0;
		for(FlockingAgent agent : fishes){
			Fish neigh = (Fish)agent;
			if( agent.distance(fish) <= Fish.NEIGHBOR_RAIDUS  )
				ctr++;
//			else System.out.println(Fish.colorDifference(neigh,fish));
		}
		
		return ctr;
	}
	
	List<Fish> getPartners(final Fish fish){
		List<Fish> partners = new ArrayList<>();
		for(FlockingAgent agent : fishes){
			Fish neigh = (Fish)agent;
			if( agent.distance(fish) <= Fish.NEIGHBOR_RAIDUS  )
				partners.add(neigh);
		}
		
		Collections.sort(partners, new Comparator<Fish>() {

			@Override
			public int compare(Fish o1, Fish o2) {
				double d1 = o1.distance(fish);
				double d2 = o1.distance(fish);
				if(d1 < d2) return -1;
				else if(d1 == d2) return 0;
				else return 1;
			}
		});
		
		if( partners.size() < 2 ) return partners;
		return partners.subList(0, 2);
	}
	
	public void evolutionPopulation(){
		
		int initial = fishes.size();
		
		int MINIMUM_FISHES = (int) Math.ceil((double)initial/10.0);
		Queue<Fish> q = new LinkedList<>();
		Random r = new Random();
		for(FlockingAgent agent : fishes){
			Fish fish = (Fish)agent;
			int n = countNeighbors(fish);
			if( n < MINIMUM_FISHES ){
				double p = (double)n/(double)MINIMUM_FISHES;
				if( Math.random() > p )
					q.add(fish);
			}
			//Evolve the curren population!
			//Offspring
			//Reproduction, mutation
			//NO FITNESS!, small interactions...
		}
		
		while(!q.isEmpty()) fishes.remove(q.poll());
		
		for(FlockingAgent agent : fishes){
			Fish fish = (Fish)agent;
			List<Fish> partners = getPartners(fish);
			q.addAll( fish.mate(partners) );
		}
		
		fishes.clear();
		while(!q.isEmpty()) fishes.add(q.poll());
		JOptionPane.showMessageDialog(null, fishes.size()-initial);
		
		//reaper();
	}

	public Fish[] getFishesArray() {
		Fish array[] = new Fish[fishes.size()];
		for(int i=0; i<fishes.size(); i++){
			array[i] = (Fish) fishes.get(i);
		}
		Arrays.sort(array,new Comparator<Fish>(){

			@Override
			public int compare(Fish o1, Fish o2) {
				
				return o1.getX()-o2.getX();
			}
		});
		return array;
	}

	public Fish getFishIn(Point point) {
		
		for(int i=0; i<fishes.size(); i++)
			if( ((Fish)fishes.get(i)).getBoundingBox().contains(point) ) 
				return (Fish)fishes.get(i);
		
		return null;
	}
}
