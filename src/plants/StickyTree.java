package plants;
import gui.AnglePosition;
import gui.TurtleGraphic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.Stack;


public class StickyTree extends LSystem {
	protected double angle;
	protected double delta;
	protected int size;
	
	public StickyTree() {
		angle = 25.0;
		size = 2;
		delta = 10.0;
	}
	@Override
	String rule(char variable) {
		if( variable == 'X' )
			return "F-[[X]+X]+F[+FX]-X";
		if( variable == 'F' )
			return "FF";
		
		return variable+"";
	}

	@Override
	String startSymbol() {
		return "X";
	}

	@Override
	public void draw(Graphics2D g, String shape, int x, int y) {
		
		g.setColor( getColor() );
		TurtleGraphic turtle = new TurtleGraphic((double)x, (double)y, 90.0);
		Stack<AnglePosition> stack = new Stack<>();
		for(int i=0; i<shape.length(); i++){
			if(shape.charAt(i) == 'F')
				turtle.forward(g, size);
			else if( shape.charAt(i) == '+' )
				turtle.rotateRight(randomRange(angle-delta, angle+delta));
			else if( shape.charAt(i) == '-' )
				turtle.rotateLeft(randomRange(angle-delta, angle+delta));
			else if( shape.charAt(i) == '[' )
				stack.add( turtle.getAnglePosition() );
			else if( shape.charAt(i) == ']' )
				turtle.update( stack.pop() );
		}
	}

}
