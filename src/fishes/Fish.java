package fishes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import affineTransforms.NonLinearTransform;
import aquarium.Main;
import DRSystem.ActivatorInhibitorSystem;

public class Fish {
	
	private ActivatorInhibitorSystem system;
	BufferedImage image;
	int colours[];
	int iterations = 0;
	public Fish(Color c1, Color c2, double s, double da, double db, double ra, double rb, double ba, double bb, int x, int y){
		colours = new int[256];
		colours[0] = c1.getRGB();
		colours[1] = c2.getRGB();
		for(int i=2; i<colours.length; i++){
			colours[i] = new Color(colours[i-1]|colours[i-2]).getRGB();
		}
		image = null;
		try {
			image = ImageIO.read(new File("fish0.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		int rows = image.getWidth(), cols = image.getHeight();
		system = new ActivatorInhibitorSystem(s,da,db,ra,rb,ba,bb, rows,cols,x,y);
		
	}
	
	public void horizontalBend(int offset){
		image = NonLinearTransform.bendHorizontal(image, offset);
		updateSystem();
	}
	
	private void updateSystem(){
		int rows = image.getWidth(), cols = image.getHeight();
		system = system.lightCopy(rows,cols);
		for(int i=0; i<iterations; i++) system.step();
	}
	
	public void verticalBend(int offset){
		image = NonLinearTransform.bendVertical(image, offset);
		updateSystem();
	}
	
	public int areaRows(){
		return system.getRows();
	}
	
	public int areaCols(){
		return system.getCols();
	}
	
	public int getWidth(){
		return image.getWidth();
	}
	
	public int getHeight() {
		return image.getHeight();
	}
	
	public void setX(int x){
		system.setX(x);
	}
	
	public void setY(int y){
		system.setY(y);
	}
	
	public int getX(){
		return system.getX();
	}
	
	public int getY(){
		return system.getY();
	}
	
	public void iterate(int times){
		for(int i=0; i<times; i++)
			system.step();
		
		this.iterations += times;
	}
	
	public void draw(Graphics2D g, int width, int height){
	//	system.step();
		system.draw(g, width, height, image, colours);
	}

	
}
