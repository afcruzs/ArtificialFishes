package plants;
import gui.AnglePosition;
import gui.TurtleGraphic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;
import java.util.Stack;


public class Plant2 extends LSystem {
	
	int depth = 0;
	
	public Plant2(int depth){
		super( (new Random().nextInt(2)==0) ? Color.CYAN.darker() : Color.ORANGE.darker());
		this.depth = depth;
	}
	
	@Override
	String rule(char variable) {
		if(variable == 'F')
			return "|[-F][+F|]";
		return String.valueOf(variable);
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
		double angle = 20.0;
		double delta = 10.0;
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
			else if( shape.charAt(i) == '|' ){
				turtle.forward(g, (depth/2)*size);
			}
		}
	}

}
