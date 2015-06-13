package gui;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;


public class TurtleGraphic {
	Point2D.Double coordinate;
	double angle;

	public TurtleGraphic(double x, double y, double angle) {
		super();
		this.coordinate = new Point2D.Double(x,y);
		this.angle = -angle; //JEJEJE :v
	}
	
	public void forward(Graphics2D g, int size){
		Point2D.Double endPoint = new Point2D.Double( coordinate.getX()+size*Math.cos(Math.toRadians(angle)),
				coordinate.getY()+size*Math.sin(Math.toRadians(angle)) );
		g.draw(new Line2D.Double(coordinate,endPoint));
		coordinate = endPoint;
	}
	
	
	public void rotateLeft(double delta){
		angle -= delta;
	}
	
	public void rotateRight(double delta){
		angle += delta;
	}
	
	public void update(AnglePosition ap){
		coordinate = (Point2D.Double) ap.getPosition().clone();
		angle = ap.getAngle();
	}
	
	public AnglePosition getAnglePosition(){
		return new AnglePosition(angle, new Point2D.Double(coordinate.getX(), coordinate.getY()));
	}
	
}
