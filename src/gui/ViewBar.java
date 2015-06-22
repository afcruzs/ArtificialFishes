package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.print.attribute.standard.JobHoldUntil;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

import aquarium.Controller;

public class ViewBar extends JMenuBar {
	JMenuItem startSimulation;
	JMenuItem pauseSimulation;
	JMenuItem stopSimulation;
	JMenuItem fishes;
	
	
	
	public ViewBar(){
		addSimulationItems();
		
		addSettingsItems();
		
	}
	
	


	private void addSettingsItems() {
		JMenu settings = new JMenu( "Settings" );
		JMenuItem edit = new JMenuItem( "Edit" );
		edit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.openEditFrame();
			}
		});
		settings.add(edit);
		add(settings);
	}




	private void addSimulationItems() {
		JMenu simulation = new JMenu( "Simulation" );
		
		startSimulation = new JMenuItem("Start");
		pauseSimulation = new JMenuItem("Pause");
		stopSimulation = new JMenuItem("Stop");
		fishes = new JMenuItem("Fishes");
		
		pauseSimulation.setEnabled(false);
		stopSimulation.setEnabled(false);
		
		startSimulation.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.startEvolution();
				pauseSimulation.setEnabled(true);
				stopSimulation.setEnabled(true);
				startSimulation.setEnabled(false);
				
			}
		});
		
		pauseSimulation.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				startSimulation.setName("Resume");
				startSimulation.setEnabled(true);
				Controller.repaint();
				Controller.pauseSimulation();
				
			}
		});
		
		stopSimulation.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.stopSimulation();
			}
		});
		
		fishes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.openFishesVisualizer();
			}
		});
		
		
		add(simulation);
		simulation.add(startSimulation);
		simulation.add(new JSeparator());
		simulation.add(pauseSimulation);
		simulation.add(new JSeparator());
		simulation.add(stopSimulation);
		simulation.add(new JSeparator());
		simulation.add(fishes);
	}




	public void onFinishedSimulation() {
		startSimulation.setEnabled(true);
		pauseSimulation.setEnabled(false);
		stopSimulation.setEnabled(false);
	}

}
