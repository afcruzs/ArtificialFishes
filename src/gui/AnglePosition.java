package gui;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;


public class AnglePosition {
	private double angle;
	private Point2D.Double position;
	
	
	public AnglePosition(double angle, Double position) {
		super();
		this.angle = angle;
		this.position = position;
	}


	public double getAngle() {
		return angle;
	}


	public Point2D.Double getPosition() {
		return position;
	}
	
	
	
}
