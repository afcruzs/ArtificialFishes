package gui;
import java.awt.BorderLayout;
import java.awt.Graphics;

import javax.swing.JPanel;

import fishes.Fish;

public class FishInfo extends JPanel{
	
	Fish fish;
	Canvas canvas;
	
	public FishInfo() {
		setLayout( new BorderLayout() );
		setVisible(true);
		canvas = new Canvas();
		add(canvas);
		fish = null;
	}
	
	public void setFish(Fish f){
		this.fish = f;
	}
	
	private class Canvas extends JPanel{
		@Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			if(fish != null)
				fish.drawInCorner(g);
		}
		
		
	}
}