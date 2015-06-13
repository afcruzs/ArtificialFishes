package plants;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Random;


public abstract class LSystem {
	
	abstract String rule(char variable);
	
	abstract String startSymbol();
	
	public abstract void draw(Graphics2D g, String shape, int x, int y);
	
	private Color color;
	
	public LSystem(){
		color = (new Random().nextInt(2) == 0 ) ? Color.GREEN : Color.GREEN.darker();
	}
	
	public LSystem(Color color){
		this.color = color;
	}
	
	public String iterate(String symbol){
		StringBuilder out = new StringBuilder();
		for(int i=0; i<symbol.length(); i++)
			out.append( rule(symbol.charAt(i)) );
		
		return out.toString();
	}
	
	public String make(int iterations){
		String shape = startSymbol();
		for(int i=1; i<iterations; i++){
			shape =	iterate(shape);
		}
		
		return shape;
	}
	
	protected Color getColor(){
	
		return color;

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
