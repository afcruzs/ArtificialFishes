package plants;
import gui.AnglePosition;
import gui.TurtleGraphic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Stack;


public class Plant3 extends LSystem{

	@Override
	String rule(char variable) {
		if( variable == 'X' )
			return "[+X]F[-X]+FX";
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
		int n = 3;
		double angle = 25.7;
		double delta = 10;
		TurtleGraphic turtle = new TurtleGraphic((double)x, (double)y, 90.0);
		Stack<AnglePosition> stack = new Stack<>();
		g.setColor( getColor() );
		for(int i=0; i<shape.length(); i++){
			if(shape.charAt(i) == 'F')
				turtle.forward(g, n);
			else if( shape.charAt(i) == '+' )
				turtle.rotateRight( randomRange(angle-delta, angle+delta) );
			else if( shape.charAt(i) == '-' )
				turtle.rotateLeft( randomRange(angle-delta, angle+delta) );
			else if( shape.charAt(i) == '[' )
				stack.add( turtle.getAnglePosition() );
			else if( shape.charAt(i) == ']' )
				turtle.update( stack.pop() );
		}
		
	}

}
