package flocking;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Vector;

public class FlockingAgent {
	public int NEIGHBOR_RAIDUS = 50;
	public int SEPARATION_RAIDUS = 6;
	public int MAX_FORCE = 5;
	
	static double SEPARATION_W = 2.0, ALIGN_W = 1.0, COHESION_W = 0.4;
	
	protected FlockingVector velocityVector; 
	protected Point position;
	
	public FlockingAgent(Point position, FlockingVector velocityVector){
		this.position = position;
		this.velocityVector = velocityVector.limit(MAX_FORCE);
	}
	
	protected double getVelocityNorm(){
		return velocityVector.getX()*velocityVector.getY();
	}
	
	public void setVector(int x, int y){
		velocityVector = new FlockingVector(x,y);
	}
	
	public FlockingVector getPositionVector(){
		return new FlockingVector( position.x, position.y );
	}
	
	void addToLocation( FlockingVector v ){
		FlockingVector tmp = v.add(getPositionVector());
		position.x = (int) tmp.getX();
		position.y = (int) tmp.getY();
	}
	
	//Acts given a perception
	/*
	 * Acts according to flocking algorithm (Craig Reynolds alg)
	 */
	public void act( Vector<FlockingAgent> neighbors ){
		FlockingVector acceleration = flockingAlgorithm(neighbors);
		velocityVector = velocityVector.add(acceleration).limit(MAX_FORCE);
		move();
		
	}
	
	public FlockingVector flockingAlgorithm( Vector<FlockingAgent> neighbors ){
		FlockingVector cohesion = cohesionVector(neighbors).scalarMultiply(COHESION_W);
		FlockingVector align = alignVector(neighbors).scalarMultiply(ALIGN_W);
		FlockingVector separation = separationVector(neighbors).scalarMultiply(SEPARATION_W);
		
//		System.out.println(cohesion);
//		System.out.println(align);
//		System.out.println(separation);
//		System.out.println();
		
		
		return cohesion.add(align).add(separation);
	}
	
	
	
	FlockingVector separationVector( Vector<FlockingAgent>neighbors ){
		FlockingVector vector = new FlockingVector();
		int ctr = 0;
		for( FlockingAgent neigh : neighbors ){
			double d = neigh.position.distance(position);
			if( d <= SEPARATION_RAIDUS && d > 0.0 ){
				vector = vector.add( getPositionVector().subtract(neigh.getPositionVector()) ).normalize().scalarDivide(d);
			//	vector = vector.add( neigh.getPositionVector().scalarMultiply(-1.0) );
				ctr++;
			}
		}
		
		if( ctr > 0 )
			vector = vector.scalarDivide(ctr);
		
		return vector.limit(MAX_FORCE);
	}
	
	FlockingVector alignVector( Vector<FlockingAgent> neighbors ){
		FlockingVector vector = new FlockingVector();
		for( FlockingAgent neigh : neighbors ){
			double d = neigh.distance(this); 
			if( d > 0.0 && d < NEIGHBOR_RAIDUS )
				vector = vector.add(neigh.velocityVector);
		}
		
		if(neighbors.size() > 0)
			vector = vector.scalarDivide(neighbors.size());
		
		return vector.limit(MAX_FORCE);
		
	}
	
	//Cohesion part
	FlockingVector cohesionVector( Vector<FlockingAgent> neighbors ){
		FlockingVector vector = new FlockingVector();
		
		
		for( FlockingAgent neigh : neighbors ){
			double d = neigh.distance(this); 
			if( d > 0.0 && d < NEIGHBOR_RAIDUS )
				vector = vector.add( neigh.getPositionVector() );
		}
		
		if(neighbors.size() > 0)
			return friction( vector.scalarDivide(neighbors.size()) ).limit(MAX_FORCE);
		else
			return vector.limit(MAX_FORCE);
	}
	
	FlockingVector friction(FlockingVector v){
		FlockingVector vector = v.subtract(getPositionVector());
		double d = v.getNorm();
		if(d > 0){
			v.normalize();
			
			double xd = 150.0;
			if(d < xd)
				vector.scalarMultiply(MAX_FORCE*(d/xd));
			else
				vector.scalarMultiply(MAX_FORCE);
			
			vector = vector.subtract(velocityVector);
			vector.limit(MAX_FORCE);
		}else{
			vector = new FlockingVector(1,1);
		}
		
		return vector;
			
	}
	
	public void move(){
		position.x = (int) (position.x + velocityVector.getX());
		position.y = (int) (position.y + velocityVector.getY());
	}

	//Draws the velocity vector with a oval in the head
	//('Cause arrows are too mainstream)
	public void draw(Graphics g) {
		
		Graphics2D g2 = (Graphics2D)g;
		int radius = 5;
		g2.fillOval(position.x-radius, position.y-radius, 2*radius, 2*radius);
		
		Color prev = g2.getColor();
//		g2.setColor(Color.GREEN);
//		g.drawOval(position.x-NEIGHBOR_RAIDUS, position.y-NEIGHBOR_RAIDUS, 2*NEIGHBOR_RAIDUS, 2*NEIGHBOR_RAIDUS);
//		
//		g2.setColor(Color.RED);
//		g.drawOval(position.x-SEPARATION_RAIDUS, position.y-SEPARATION_RAIDUS, 2*SEPARATION_RAIDUS, 2*SEPARATION_RAIDUS);
//		
		g2.setColor(prev);
		velocityVector.draw(g2,position);
		//Draw the actual body... abstract class maybe? Template pattern? :D
		g2.setColor(prev);
		
	}

	public void setPosition(int i, int j) {
		position.x = i;
		position.y = j;
		
	}

	public double distance(FlockingAgent agent) {
		return position.distance(agent.position);
	}

	public Point getPosition() {
		return position;
	}

}
