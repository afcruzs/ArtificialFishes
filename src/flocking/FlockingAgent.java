package flocking;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.Vector;

import aquarium.ConstructFishesArea;
import aquarium.RandomUtils;

public class FlockingAgent {

	static final double SEPARATION_W = 1.5, ALIGN_W = 1.0, COHESION_W = 1.0;
	static Shape fishOutLine = null;
	static {
		fishOutLine = (Shape) ConstructFishesArea.readFile("fishOutLine");
	}
	protected double NEIGHBOR_RAIDUS = 50.0;
	protected double SEPARATION_RAIDUS = 25.0;
	protected double MAX_ENERGY = 10.0;

	protected double MAX_FORCE = 2.0;
	protected double MAX_SPEED = 10.03;

	protected FlockingVector velocity;
	protected FlockingVector location;
	protected FlockingVector acceleration;
	protected double energy;
	protected double mass;

	// protected Color color;

	public static FlockingAgent randomFlockingAgent(Dimension bounds) {
		FlockingAgent agent = new FlockingAgent(new Point(RandomUtils.randInt(
				0, (int) bounds.getWidth()), RandomUtils.randInt(0,
				(int) bounds.getHeight())), new FlockingVector(
				Math.cos(RandomUtils.randDouble(-2 * Math.PI, 2 * Math.PI)),
				Math.sin(RandomUtils.randDouble(-2 * Math.PI, 2 * Math.PI))));

		agent.NEIGHBOR_RAIDUS = 50.0;
		agent.SEPARATION_RAIDUS = 25.0;
		return agent;
	}

	public FlockingAgent(Point position, FlockingVector velocityVector) {
		acceleration = new FlockingVector(1.0, 1.0);
		velocity = velocityVector;
		location = new FlockingVector(position.x, position.y);
		mass = 1.0;
		energy = MAX_ENERGY / 2.0;

	}

	public void sumToEnergy(double delta) {
		energy += delta;
	}

	public int countNeighbors(Vector<FlockingAgent> neighbors, double R) {
		int ctr = 0;
		for (FlockingAgent ag : neighbors) {
			if (distance(ag) < R)
				ctr++;
		}

		return ctr;
	}

	// Acts given a perception
	/*
	 * Acts according to flocking algorithm (Craig Reynolds alg)
	 */
	public void act(Vector<FlockingAgent> neighbors) {
		flockingAlgorithm(neighbors);
		move();

	}

	// Mass?
	void applyForce(FlockingVector v) {
		acceleration = acceleration.add(v).scalarDivide(mass);
	}

	/*
	 * Reynolds' Flocking algorithm
	 */
	public void flockingAlgorithm(Vector<FlockingAgent> neighbors) {

		FlockingVector separation = separationVector(neighbors).scalarMultiply(
				SEPARATION_W);
		FlockingVector align = alignVector(neighbors).scalarMultiply(ALIGN_W);
		FlockingVector cohesion = cohesionVector(neighbors).scalarMultiply(
				COHESION_W);

		applyForce(separation);
		applyForce(align);
		applyForce(cohesion);

	}

	/*
	 * 
	 */
	FlockingVector separationVector(Vector<FlockingAgent> neighbors) {
		FlockingVector steer = new FlockingVector(0.0, 0.0);
		int count = 0;

		for (FlockingAgent agent : neighbors) {
			double d = location.distance(agent.location);
			if (d > 0 && d < SEPARATION_RAIDUS) {
				FlockingVector diff = location.subtract(agent.location);
				diff = diff.normalize().scalarDivide(d);
				steer = steer.add(diff);
				count++;
			}
		}

		if (count > 0) {
			steer = steer.scalarDivide((double) count);
		}

		if (steer.getNorm() > .0) {
			steer = steer.normalize().scalarMultiply(MAX_SPEED)
					.subtract(velocity).limit(MAX_FORCE);
		}

		return steer;
	}

	FlockingVector alignVector(Vector<FlockingAgent> neighbors) {
		FlockingVector sum = new FlockingVector(0.0, 0.0);
		int count = 0;
		for (FlockingAgent agent : neighbors) {
			double d = location.distance(agent.location);
			if (d > 0 && d < NEIGHBOR_RAIDUS) {
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

	// Cohesion part
	FlockingVector cohesionVector(Vector<FlockingAgent> neighbors) {
		FlockingVector sum = new FlockingVector(0.0, 0.0);

		int count = 0;

		for (FlockingAgent agent : neighbors) {
			double d = location.distance(agent.location);
			if (d > 0.0 && d < NEIGHBOR_RAIDUS) {
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

	FlockingVector seek(FlockingVector target) {
		FlockingVector desired = target.subtract(location);

		desired = desired.normalize().scalarMultiply(MAX_SPEED);

		FlockingVector steer = desired.subtract(velocity);
		return steer.limit(MAX_FORCE);
	}

	// Move according to acceleration
	public void move() {
		velocity = velocity.add(acceleration).limit(MAX_SPEED);
		location = location.add(velocity);
		acceleration = acceleration.scalarMultiply(0.0);
	}

	// Draws the velocity vector with a oval in the head
	// ('Cause arrows are too mainstream)
	public void draw(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		int radius = 5;
		Color prev = g2.getColor();
		 g2.fillOval((int) location.getX() - radius, (int) location.getY()
		 - radius, 2 * radius, 2 * radius);
		
		// g2.setColor(Color.GREEN);
		// g2.draw(new Ellipse2D.Double(position.x-NEIGHBOR_RAIDUS,
		// position.y-NEIGHBOR_RAIDUS, 2*NEIGHBOR_RAIDUS, 2*NEIGHBOR_RAIDUS));
		//
		// g2.setColor(Color.RED);
		// g2.draw(new Ellipse2D.Double(position.x-SEPARATION_RAIDUS,
		// position.y-SEPARATION_RAIDUS, 2*SEPARATION_RAIDUS,
		// 2*SEPARATION_RAIDUS));
		//
		// g2.setColor(prev);
		// velocity.draw(g2,getPosition());

		// Draw the actual body... abstract class maybe? Template pattern? :D
		g2.setColor(prev);

	}

	public double distance(FlockingAgent agent) {
		return location.distance(agent.location);
	}

	public Point getPosition() {
		return new Point((int) location.getX(), (int) location.getY());
	}

	public void setPosition(int x, int y) {
		location = new FlockingVector((double) x, (double) y);
	}

}
