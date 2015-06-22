package gui;


import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import fishes.Fish;
import aquarium.Controller;

@SuppressWarnings("serial")
public class View extends JFrame {
	
	private ViewBar menuBar;
	
	class AquariumPanel extends JPanel{
		public AquariumPanel() {
			addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					Fish f = Controller.getFishIn(e.getPoint());
					if(f == null) return;
					f.iterateSystem();
					JFrame frame = new JFrame();
					FishInfo info = new FishInfo();
					info.setFish(f);
					frame.add(info);
					frame.setVisible(true);
					frame.setSize(f.getImageWidth()+20,f.getImageHeight()+20);
				}
			});
		} 
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
