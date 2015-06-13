import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Random;
import java.util.Stack;


public class FractalPlant extends LSystem {

	@Override
	String rule(String variable) {
		StringBuilder out = new StringBuilder();
		for(int i=0; i<variable.length(); i++){
			if( variable.charAt(i) == 'F' )
				out.append("F-[-F+F+F]+[+-F-FFF]X");
			else
				out.append(variable.charAt(i));
		}
		return out.toString();
	}

	@Override
	void draw(Graphics2D g, String shape, int x, int y) {
		Point2D.Double st = new Point2D.Double((double)x,(double)y);
		
		double size = 5.0;
		double delta = 25.0;
		double currentAngle = 90.0;
		
		for(int i=0; i<20; i++){
			double sign;
			if( Math.random() < 0.5 )
				sign = 1.0;
			else sign = -1.0;
			
			delta = randomRange(20, 40.0);
			drawOne(g,shape,x,y,size,sign*delta,randomRange(currentAngle-50.0, currentAngle));
		}
		
		
		
		
	}
	
	void drawOne(Graphics2D g, String shape, int x, int y, double size, double delta, double currentAngle){
		Stack<AnglePosition> stack = new Stack<>();
		stack.add(new AnglePosition(currentAngle, new Point2D.Double((double)x,(double)y)));
		AnglePosition anglePos = null;
		
		Point2D.Double currentPoint = null;
		for(int i=0; i<shape.length(); i++){
			anglePos = stack.peek();
			if( shape.charAt(i) == 'F' ){
				currentPoint = drawLineForward(g, anglePos.getPosition(), size, anglePos.getAngle());
				
			}else if( shape.charAt(i) == '-' )
				currentAngle -= delta;
			else if( shape.charAt(i) == '+' )
				currentAngle += delta;
			else if( shape.charAt(i) == '[' ){
				stack.push(new AnglePosition(currentAngle, currentPoint));
			}else if( shape.charAt(i) == ']' )
				stack.pop();
			
				
		}
	}

	private AnglePosition randomElement(HashSet<AnglePosition> s) {
		for(AnglePosition a : s)
			return a;
		return null;
	}

	@Override
	String startSymbol() {
		return "F";
	}

}
