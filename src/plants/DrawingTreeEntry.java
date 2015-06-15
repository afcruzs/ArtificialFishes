package plants;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import aquarium.ObservableEntity;

public class DrawingTreeEntry implements ObservableEntity{
	LSystem system;
	String production;
	int x,y;
	public DrawingTreeEntry(LSystem system, String production, int x, int y) {
		super();
		this.system = system;
		this.production = production;
		this.x = x;
		this.y = y;
	}
	public void draw(Graphics2D g2, int width, int height) {
		system.draw(g2, production, x,height-y);
	}
	
	public Point getUbication(){
		return new Point(x,y);
	}
	@Override
	public Rectangle getBoundingBox() {
		
		return new Rectangle(getUbication(),new Dimension(10,10)); //Is not clear what is the dimension... :(
	}
	
}