import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Random;


public abstract class LSystem {
	
	abstract String rule(String variable);
	
	abstract String startSymbol();
	
	abstract void draw(Graphics2D g, String shape, int x, int y);
	
	public String make(int iterations){
		String shape = startSymbol();
		for(int i=1; i<iterations; i++){
			shape =	rule(shape);
		}
		
		return shape;
	}
	
	protected Point2D.Double drawLineForward(Graphics2D g, Point2D.Double start, double size, double angle){
		Point2D.Double endPoint = rotate(angle, start, pointForward(start, size));
		g.draw(new Line2D.Double(start,endPoint));
		return endPoint;
	}
	
	private Point2D.Double pointForward(Point2D.Double start, double size){
		return new Point2D.Double(start.getX()+size,start.getY());
	}
	
	protected Point2D.Double rotate(double angle, Point2D pivot, Point2D point){
		angle = -angle; //jejej
		Point2D.Double result = new Point2D.Double();
	    AffineTransform rotation = new AffineTransform();
	    double angleInRadians = (angle * Math.PI / 180);
	    rotation.rotate(angleInRadians, pivot.getX(), pivot.getY());
	    rotation.transform(point, result);
	    return result;
	}
	
	protected double randomRange(double start, double end){
		double random = new Random().nextDouble();
		double result = start + (random * (end - start));
		return result;
	}
}
