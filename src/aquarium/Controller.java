package aquarium;

import java.awt.Dimension;
import java.awt.Graphics2D;

public class Controller {
	static World world = null;
	static View view = null;
	
	public static synchronized void init(){
		if( world == null )
			world = new World();
		if( view ==  null )
			view = new View();
	}
	
	public static void run(){
		init();
		view.setTitle("Artificial Life - Aquarium");
		
	}
	
	public static void draw(Graphics2D g){
		world.draw(g);
	}

	public static Dimension getDimension() {
		return view.getSize();
	}
	
	
	
	public static void main(String[] args) {
		run();
	}
}
