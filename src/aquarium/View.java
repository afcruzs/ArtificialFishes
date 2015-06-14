package aquarium;

import gui.Background;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class View extends JFrame {
	
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
		add(aquariumPanel);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState( getExtendedState() | JFrame.MAXIMIZED_BOTH);
	}
	
}
