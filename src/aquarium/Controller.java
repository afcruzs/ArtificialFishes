package aquarium;

import java.awt.Dimension;
import java.awt.Graphics2D;

public class Controller {
	static World world = null;
	static View view = null;
	
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
		repaint();
	}

	public static void repaint() {
		view.repaint();
	}
}
