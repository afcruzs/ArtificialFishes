package aquarium;

import fishes.Fish;
import fishes.RandomFishGenerator;
import flocking.FlockingAgent;
import gui.EditFrame;
import gui.FishesVisualizer;
import gui.View;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Random;

import javax.swing.ListModel;
import javax.swing.SwingUtilities;

public class Controller {
	static World world = null;
	static View view = null;
	static Thread evolutionThread = null;
	private static boolean paused = false;
	private static boolean finished = false;
	private static int numberOfGenerations;
	private static int numberOfIterationsPerGeneration;
	private static int populationSize;
	private static long sleepTime;
	private static int fishSize;
	private static boolean animation = true;
	
	public static void main(String[] args) {
		run();
	}

	
	public static synchronized void init(){
		if( view ==  null )
			view = getView();
		if( world == null )
			world = getWorld();
		numberOfGenerations = 50;
		numberOfIterationsPerGeneration = 400;
		populationSize = 10;
		sleepTime = 0;
		fishSize = 30;
		
	}
	
	public synchronized static World getWorld(){
		if( world == null )
			world = new World();
		return world;
	}
	
	public synchronized static View getView(){
		if( world == null )
			view = new View();
		return view;
	}
	
	public static void run(){
		init();
		view.setTitle("Artificial Life - Aquarium");
		
	}
	
	public static void draw(Graphics2D g){
		getWorld().draw(g);
	}

	public static Dimension getDimension() {
		return getView().getSize();
	}
	
	public static void repaint() {
		view.repaint();
	}
	
	static synchronized boolean getPaused(){
		return paused;
	}
	
	static synchronized void setPaused(boolean b){
		paused = b;
	}
	
	static void updateTitle(){
		view.setTitle( "Generation: " + String.valueOf(world.getCurrentGeneration()) + 
				      " Iteration: "+String.valueOf(world.getCurrentIteration())  +
				      " Population size: " + String.valueOf(world.getPopulationSize()) );
	}

	public static void callBackOnIteration(){
		
		if(getFinished()) return;
		while(getPaused());
		
		
		updateTitle();
		if( !animation ) return;
		view.repaint();
		
		try {
			Thread.sleep(getSleepTimeVal());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}

	public static void startEvolution() {
		
		if(paused){
			setPaused(false);
			return;
		}
		world.birth(populationSize);
		evolutionThread = new Thread(){
			@Override
			public void run(){
				for(int i=0; i<numberOfGenerations; i++){
					System.out.println(world.getPopulationSize());
					world.iterate(numberOfIterationsPerGeneration);
					world.evolutionPopulation();
				}
				
				onFinishedSimulation();
			}
		};
		
		evolutionThread.start();
	}


	public static void pauseSimulation() {
		setPaused(true);
	}


	public static void openEditFrame() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new EditFrame();
			}
		});
	}


	public static int getPopulation() {
		return populationSize;
	}


	public static int getGenerations() {
		return numberOfGenerations;
	}


	public static int getIterations() {
		return numberOfIterationsPerGeneration;
	}


	public static void setPopulationSize(int parseInt) {
		populationSize = parseInt;
	}
	
	public static void setAnimation(boolean b){
		animation = b;
	}


	public static void setNumberOfGenerationsPerIteration(int parseInt) {
		numberOfGenerations = parseInt;
	}


	public static void setNumberOfIterations(int parseInt) {
		numberOfIterationsPerGeneration = parseInt;
	}

	public static synchronized long getSleepTimeVal() {
		return sleepTime;
	}

	public static long getSleepTime() {
		return sleepTime;
	}
	
	public static synchronized void setSleepTime(long l){
		sleepTime = l;
	}
	
	public static void onFinishedSimulation(){
		view.onFinishedSimulation();
		setFinished(false);
		setPaused(false);
	}
	
	public static synchronized void setFinished(boolean g){
		finished = g;
	}
	
	public static synchronized boolean getFinished(){
		return finished;
	}


	public static void stopSimulation() {
		setFinished(true);
	}


	public static int getFishSize() {
		return fishSize;
	}


	public static void setFishSize(int parseInt) {
		FlockingAgent.setSeparationRadius( ((double)parseInt)*0.9 );
		fishSize = parseInt;
	}


	public static Fish[] getFishesArray() {
//		Fish[] f = {RandomFishGenerator.randomFish(30)};
//		return f;
		return world.getFishesArray();
	}


	public static void openFishesVisualizer() {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new FishesVisualizer();
			}
		});
	}


	public static Fish getFishIn(Point point) {
		return world.getFishIn(point);
	}


	public static boolean getAnimation() {
		return animation;
	}
	
}
