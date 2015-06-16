package flocking;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import aquarium.RandomUtils;

public class FlockingAgent {
	
	private Vector2D velocityVector; 
	private Point position;
	
	public FlockingAgent(){
		position = new Point(RandomUtils.randInt(20, 150), RandomUtils.randInt(20, 150));
//		velocityVector = new Vector2D(new double[]{ RandomUtils.randInt(0, 100),
//				RandomUtils.randInt(0, 100) });
		velocityVector = new Vector2D(new double[]{
			1,1	
		});
	}
	
	public void setVector(int x, int y){
		velocityVector = new Vector2D(new double[]{x,y});
	}
	
	public void move(){
		position.x = (int) (position.x + velocityVector.getX());
		position.y = (int) (position.y + velocityVector.getY());
	}

	//Draws the velocity vector with a oval in the head
	//('Cause arrows are too mainstream)
	public void draw(Graphics g) {
		
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setRenderingHint(
			    RenderingHints.KEY_ANTIALIASING,
			    RenderingHints.VALUE_ANTIALIAS_ON);
		
		
		int radius = 7;
		System.out.println(position);
		
		
		int x2 = (int) (position.x + velocityVector.getX());
		int y2 = (int) (position.y + velocityVector.getY());
		g2.drawLine(position.x, position.y, x2, y2);
		g2.setColor(Color.RED);
		
		//Draws head
		
		g2.fillOval(x2-radius, y2-radius, 2*radius, 2*radius);
		
		//Draws 'body'
		radius *= 2;
		g2.setColor(Color.BLUE);
		g2.fillOval(position.x-radius, position.y-radius, 2*radius, 2*radius);
		
	}

	public void setPosition(int i, int j) {
		position.x = i;
		position.y = j;
		
	}

}
