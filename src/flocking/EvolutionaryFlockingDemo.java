package flocking;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import evolution.CrossoverOperator;
import evolution.EvolutionaryAlgorithm;
import evolution.Evolvable;
import evolution.MutationOperator;
import evolution.Operators;
import evolution.Population;
import evolution.PopulationFactory;
import evolution.SelectionOperator;
import evolution.TerminationCondition;
import flocking.EvovableFlockingPopulation.EvovableFlockingIndividual;

public class EvolutionaryFlockingDemo {
	static JFrame mainFrame;
	static JPanel panel = null;
	static FlockingLife life = null;
	static EvolutionaryAlgorithm<EvovableFlockingPopulation> algorithm = null;
	
	static Thread go = new Thread() {
		@Override
		public void run() {
			System.out.println( algorithm.evolve(new TerminationCondition() {
				
				@Override
				public boolean isOptimal(Evolvable evolvable) {
					return evolvable.fitness() >= 100.0;
				}
				
			}) );
//			EvovableFlockingPopulation initialPopulation = new EvovableFlockingPopulation();
//			for(int i=0; i<100; i++) initialPopulation.addIndividual(EvovableFlockingIndividual.randomIndividual(panel.getSize()));
//			life.live(initialPopulation);
		}
	};

	static void initLife() {
		life = new FlockingLife(panel,600,600);
		int T = 100;
		EvovableFlockingPopulation initialPopulation = new EvovableFlockingPopulation();
		for(int i=0; i<T; i++) initialPopulation.addIndividual(EvovableFlockingIndividual.randomIndividual(panel.getSize()));
		algorithm = new EvolutionaryAlgorithm<EvovableFlockingPopulation>(initialPopulation, life, new PopulationFactory() {
			
			@Override
			public Population createPopulation(List<Evolvable> currentPopulation) {
				EvovableFlockingPopulation pop = new EvovableFlockingPopulation();
				
				for(Evolvable ev : currentPopulation)
					pop.addIndividual((EvovableFlockingIndividual) ev);
				
				return pop;
			}
		}, selection, mutation, crossover);
	}
	
	static void init() {

		initPanel();
		mainFrame = new JFrame("Evolutionary Flocking demo :)");
		assert panel != null;
		mainFrame.add(panel);
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(600, 600);
	}
	
	static void initPanel() {
		panel = new JPanel() {
			{
				add(new JButton("Move") {
					{
						addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								initLife();
								go.start();
							}

							
						});
					}
				});
			}

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				if(life != null)
					life.draw(g);
			}
		};
	}
	
	public static void main(String[] args) {
		init();
	}

	public static void setTitle(String string) {
		mainFrame.setTitle(string);
	}
	
	static int MAX = 50;
	static SelectionOperator selection = new SelectionOperator() {
		
		@Override
		public List<Evolvable> selectIndividuals(Population population) {
			List<Evolvable>  ans =  Operators.selectIndividuals(population);
			if( ans.size() > MAX ) Collections.sort(ans);
			while( ans.size() > MAX ) ans.remove(ans.get(0));
			return ans;
		
		}
	};
	
	static CrossoverOperator crossover = new CrossoverOperator() {
		
		@Override
		public List<Evolvable> mateIndividuals(List<Evolvable> currentPopulation) {
			List<Evolvable> ans = Operators.mateIndividuals(currentPopulation);
			
			if( ans.size() > MAX ) Collections.sort(ans);
			while( ans.size() > MAX ) ans.remove(ans.get(0));
			return ans;
		}
	};
	
	static MutationOperator mutation = new MutationOperator() {
		
		@Override
		public void mutateIndividuals(List<Evolvable> currentPopulation) {
			Operators.mutateIndividuals(currentPopulation);
		}
	};
	
}
