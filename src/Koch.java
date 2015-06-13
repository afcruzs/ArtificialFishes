import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Random;
import java.util.Stack;


public class Koch extends LSystem{
	
		public void draw(Graphics2D g, String shape, int x, int y){
			
			Point2D center = new Point2D.Double((double)x,(double)y);	
			int size = 5;
			double degrees = 0.0;
			double delta = 45.0;
			Stack<Point2D> points = new Stack<>();
			points.push(center);
			double startDelta = 45.0;
			double endDelta = 46.0;
			for(int i=0; i<shape.length(); i++){
				if( shape.charAt(i) == 'F' ){
					Point2D prev = points.peek();
					Point2D p = new Point2D.Double(prev.getX()+size,prev.getY());
					p = rotate(degrees,prev,p);
					g.draw(new Line2D.Double(prev,p));
					points.push(p);
				}else if( shape.charAt(i) == 'B' ){
					Point2D prev = points.peek();
					points.pop();
					Point2D p = new Point2D.Double(prev.getX()+size,prev.getY());
					p = rotate(degrees,prev,p);
					g.draw(new Line2D.Double(prev,p));
					points.push(p);
				}else if( shape.charAt(i) == '+' ){
					degrees += randomRange(startDelta,endDelta);
				}else{
					degrees -= randomRange(startDelta,endDelta);
				}
			}
	
		}
		public String rule(String axiom){
			
			String out  = "";
			for(int i=0; i<axiom.length(); i++){
				if( axiom.charAt(i) == 'F' )
					out += "F-F++F-F";
				else
					out += axiom.charAt(i);
			}
			return out.toString();
		}

		@Override
		String startSymbol() {
			return "F";
		}
	}