package plants;
import gui.AnglePosition;
import gui.TurtleGraphic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Stack;


public class Bushy extends LSystem {

	@Override
	String rule(char variable) {
		if( variable == 'F' )
			return "FF-[-F+F+F]+[+F-F-F]";

		return variable+"";
	}

	@Override
	String startSymbol() {
		return "F";
	}

	@Override
	public void draw(Graphics2D g, String shape, int x, int y) {
		
		g.setColor( getColor() );
		TurtleGraphic turtle = new TurtleGraphic((double)x, (double)y, 90.0);
		Stack<AnglePosition> stack = new Stack<>();
		int size = 4;
		double angle = 25;
		double delta = 1.5;
		for(int i=0; i<shape.length(); i++){
			if(shape.charAt(i) == 'F')
				turtle.forward(g, size);
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
