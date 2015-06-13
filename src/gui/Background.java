package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import plants.DrawingTreeEntry;
import DRSystem.ActivatorInhibitorSystem;
import aquarium.Main;
import fishes.Fish;



public class Background extends JPanel {
	
	public Background() throws IOException {
		setBackground(new Color(28,107,160));
	}
	

	@Override
	public void paintComponent(Graphics g){

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setRenderingHint(
			    RenderingHints.KEY_ANTIALIASING,
			    RenderingHints.VALUE_ANTIALIAS_ON);
		
		for(DrawingTreeEntry entry : Main.iterateTreeList())
			entry.draw(g2,getWidth(),getHeight());
		
		
		for(Fish fish : Main.iterateFishes())
			fish.draw((Graphics2D) g, getWidth(), getHeight());
		
		
	}
	
}
