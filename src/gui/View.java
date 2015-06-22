package gui;


import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import aquarium.Controller;

@SuppressWarnings("serial")
public class View extends JFrame {
	
	private ViewBar menuBar;
	
	class AquariumPanel extends JPanel{
		@Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			Controller.draw((Graphics2D)g);
		}
	}
	
	private AquariumPanel aquariumPanel;
	
	public View(){
		aquariumPanel = new AquariumPanel();
		menuBar = new ViewBar();

		add(menuBar,BorderLayout.NORTH );
		add(aquariumPanel);
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setExtendedState( getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setSize(500, 500);
	}

	public void onFinishedSimulation() {
		menuBar.onFinishedSimulation();
	}
	
}
