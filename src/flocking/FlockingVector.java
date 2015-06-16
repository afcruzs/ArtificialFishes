package flocking;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

@SuppressWarnings("serial")
public class FlockingVector extends Vector2D {

	public FlockingVector(double x, double y) {
		super(x,y);
	}
	
	public FlockingVector() {
		super(1,1);
	}
	
	public FlockingVector(Vector2D v){
		super(v.getX(),v.getY());
	}
	public FlockingVector limit(double max){
		if( this.getNorm() > max )
			return new FlockingVector(normalize().scalarMultiply(max));
		else
			return this;
	}
	
	public FlockingVector add(FlockingVector v){
		return new FlockingVector(super.add(v));
	}
	
	public FlockingVector subtract(FlockingVector v){
		return new FlockingVector(super.subtract(v));
	}
	
	public FlockingVector scalarMultiply(double sc){
		return new FlockingVector(super.scalarMultiply(sc));
	}
	
	public FlockingVector scalarDivide(double sc){
		return scalarMultiply(1.0/sc);
	}
	
	public FlockingVector normalize(){
		if( getNorm() == 0.0 ) return this;
		return new FlockingVector(super.normalize());
	}

	public void draw(Graphics2D g2, Point startPoint){
		
		g2.setRenderingHint(
			    RenderingHints.KEY_ANTIALIASING,
			    RenderingHints.VALUE_ANTIALIAS_ON);
		
		
		int radius = 7;
		
		int x2 = (int) (startPoint.x + getX());
		int y2 = (int) (startPoint.y + getY());
		
		g2.setColor(Color.RED);
		
		//Draws head
		
		//g2.fillOval(x2-radius, y2-radius, 2*radius, 2*radius);
		
		g2.setColor(Color.black);
		g2.drawLine(startPoint.x, startPoint.y, x2+10, y2+10);
		
//		//Draws 'body'
//		radius *= 2;
//		g2.setColor(Color.BLUE);
//		g2.fillOval(startPoint.x-radius, startPoint.y-radius, 2*radius, 2*radius);
//		
	}

}
