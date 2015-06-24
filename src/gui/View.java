package gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import aquarium.Controller;
import fishes.Fish;

@SuppressWarnings("serial")
public class View extends JFrame {
	
	private ViewBar menuBar;
	static BufferedImage bg ;
	static{
		try {
			bg = ImageIO.read(new File("bg.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class AquariumPanel extends JPanel{
		public AquariumPanel() {
			setBackground(new Color(28,107,160));
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
			Dimension dim = Controller.getDimension();
			int width = (int) dim.getWidth();
			int height = (int) dim.getHeight();
			
			
			g.drawImage(bg,0,0,width,height,0,0,(int)bg.getWidth(),(int)bg.getHeight(),null);
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
