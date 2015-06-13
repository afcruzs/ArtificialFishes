package plants;

import java.awt.Graphics2D;

public class DrawingTreeEntry{
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
	
}