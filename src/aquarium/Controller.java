package aquarium;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Random;

public class Controller {
	static World world = null;
	static View view = null;
	static Thread evolutionThread = null;
	
	public static synchronized void init(){
		if( view ==  null )
			view = getView();
		if( world == null )
			world = getWorld();
		
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
	
	
	
	public static void main(String[] args) {
		run();
	}

	public static void start() {
		world.start();
	}

	public static void repaint() {
		view.repaint();
	}
	
	public static Point randomPointInWorld(){
		Dimension dim = getDimension();
		Random r = new Random();
		return new Point( r.nextInt((int) dim.getWidth()), r.nextInt((int) dim.getHeight()) );
	}

	public static void startEvolution() {
		evolutionThread = new Thread(){
			@Override
			public void run(){
				int it = 0;
				while(true){
					System.out.println("iteration: "+it);
					world.iterate();
					try {
						sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					repaint();
					
					it++;
				}
			}
		};
		
		evolutionThread.start();
	}
	
}
