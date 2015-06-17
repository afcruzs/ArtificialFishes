package flocking;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Random;
import java.util.Vector;

import aquarium.RandomUtils;

public class SegregationFlockingAgent extends FlockingAgent {
	private Color color;
	
	public SegregationFlockingAgent(Point position,
			FlockingVector velocityVector) {
		super(position, velocityVector);
		
		int op = new Random().nextInt(4);
		switch (op) {
		case 0:
			color = Color.RED;
			break;
		case 1:
			color = Color.BLUE;
			break;
		case 2:
			color = Color.GREEN;
			break;
		
		case 3:
			color = Color.ORANGE;
			break;
			
		default:
			break;
		}
	}
	
	FlockingVector alignVector(Vector<FlockingAgent> neighbors) {
		FlockingVector sum = new FlockingVector(0.0, 0.0);
		int count = 0;
		for (FlockingAgent agent : neighbors) {
			double d = location.distance(agent.location);
			if (d > 0 && d < NEIGHBOR_RAIDUS && ((SegregationFlockingAgent)agent).color.equals(color)) {
				sum = sum.add(agent.velocity);
				count++;
			}
		}

		if (count > 0) {
			sum = sum.scalarDivide((double) count);
			sum = sum.normalize().scalarMultiply(MAX_SPEED);
			FlockingVector steer = sum.subtract(velocity);
			steer = steer.limit(MAX_FORCE);
			return steer;
		} else {
			return new FlockingVector(0.0, 0.0);
		}
	}
	
	FlockingVector cohesionVector(Vector<FlockingAgent> neighbors) {
		FlockingVector sum = new FlockingVector(0.0, 0.0);

		int count = 0;

		for (FlockingAgent agent : neighbors) {
			double d = location.distance(agent.location);
			if (d > 0.0 && d < NEIGHBOR_RAIDUS && color.equals(((SegregationFlockingAgent)agent).color)) {
				sum = sum.add(agent.location);
				count++;
			}
		}

		if (count > 0) {
			sum = sum.scalarDivide(count);
			return seek(sum); // Steers toward location "friction"
		} else {
			return new FlockingVector(0.0, 0.0);
		}
	}
	
	public void draw(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		int radius = 5;
		Color prev = g2.getColor();
		g2.setColor(color);
		g2.fillOval((int) location.getX() - radius, (int) location.getY()
				- radius, 2 * radius, 2 * radius);

		g2.setColor(prev);

	}

	public static SegregationFlockingAgent randomSegregationFlockingAgent(
			Dimension bounds) {
		SegregationFlockingAgent agent = new SegregationFlockingAgent(new Point(RandomUtils.randInt(0,
				(int) bounds.getWidth()), RandomUtils.randInt(0,
				(int) bounds.getHeight())), new FlockingVector(
				Math.cos(RandomUtils.randDouble(-2 * Math.PI, 2 * Math.PI)),
				Math.sin(RandomUtils.randDouble(-2 * Math.PI, 2 * Math.PI))));
		
		agent.NEIGHBOR_RAIDUS = 50.0;
		agent.SEPARATION_RAIDUS = 25.0;
		return agent;
	}

}
