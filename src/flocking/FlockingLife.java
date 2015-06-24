package flocking;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import aquarium.RandomUtils;
import evolution.Evolvable;
import evolution.Life;
import flocking.EvovableFlockingPopulation.EvovableFlockingIndividual;

public class FlockingLife implements Life<EvovableFlockingPopulation> {
	
	private int iterations;
	private int width, height;
	private Vector<FlockingAgent> agents;
	private JPanel drawingPanel;
	private Shape badShape;
	private List<Shape> eaters;
	
	public FlockingLife(JPanel drawingPanel, int width, int height) {
		iterations = 500;
		this.drawingPanel = drawingPanel;
		this.width = width;
		this.height = height;
		eaters = new ArrayList<Shape>();
		for(int i=0; i<10; i++)
			eaters.add( new Ellipse2D.Double(RandomUtils.randDouble(0, 600), RandomUtils.randDouble(0, 600), 50.0, 50.0) );
		
	}
	
	void torus(FlockingAgent ag) {
		Point position = ag.getPosition();
		if (position.getX() > width)
			position.x = 0;
		if (position.getY() > height-50)
			position.y = height-50;

		if (position.x <= 0)
			position.x = width;
		if (position.y <= 50)
			position.y = 50;

		ag.setPosition(position.x, position.y);
	}
	
	void updateAgents(Collection<Evolvable> tmp){
		agents = new Vector<>();
		for(Evolvable e : tmp) agents.add((FlockingAgent) e);
	}
	
	@Override
	public void live(EvovableFlockingPopulation population) {
	//	JOptionPane.showMessageDialog(null, "Accept to see...");
		updateAgents(population.individuals());
		
		for(int i=0; i<iterations; i++){
			//EvolutionaryFlockingDemo.setTitle(i+"");
			for( FlockingAgent agent : agents){
				agent.act(agents);
				torus(agent);
			}
			
			List<FlockingAgent> l = new ArrayList<FlockingAgent>();
			
			for( FlockingAgent agent : agents){
				Point p = agent.getPosition();
				int sz = agent.countNeighbors(agents, 50.0);
				for( Shape sh : eaters ){
					if( sh.contains(p.x,p.y) && sz > 10 ){
						break;
					}
				}
			}
			
			
			
			
			try {
				EvolutionaryFlockingDemo.go.sleep(1);
				
			} catch (InterruptedException e) { e.printStackTrace(); }
			drawingPanel.repaint(); //Push
		}
		
		population.clear();
		double maxm = -600.0;
		for( FlockingAgent agent : agents ){
			population.addIndividual((EvovableFlockingIndividual) agent);
		}
		
	//	System.out.println("The max: " + maxm);
			
	}
	
	public void draw(Graphics g){
		for(Shape sh : eaters){
			((Graphics2D) g).draw(sh);
		}
		for( FlockingAgent agent : agents ){
			agent.draw(g);
		}
	}
}
