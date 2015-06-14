package fishes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import fishes.MorphologyGenotype.BendAction;
import fishes.MorphologyGenotype.BendAction.BendType;
import affineTransforms.NonLinearTransform;
import aquarium.PreviewMain;
import DRSystem.ActivatorInhibitorSystem;

public class Fish {
	
	protected ActivatorInhibitorSystem system;
	protected BufferedImage image;
	protected int colours[];
	protected FishGenotype genotype;
	protected int width, height;
	
	public Fish(BufferedImage fishTemplate, FishGenotype fishGenotype, int x, int y, int width, int height){
		this.image = fishTemplate;
		this.genotype = fishGenotype;
		this.width = width;
		this.height = height;
		colours  = new int[256];
		colours[0] = genotype.skinGenotype.color1.getRGB();
		colours[1] = genotype.skinGenotype.color2.getRGB();
		
		for(int i=2; i<colours.length; i++)
			colours[i] = new Color(colours[i-1]|colours[i-2]).getRGB();
		
		SkinGenotype sk = genotype.skinGenotype;
		system = new ActivatorInhibitorSystem(sk.s,sk.Da,sk.Db,sk.ra,sk.rb,sk.ba,sk.bb, 
				image.getWidth(),image.getHeight(),x,y,sk.iterations);
		
		for( BendAction ba : genotype.morphologyGenotype.bends ){
			if( ba.type.equals(BendType.VERTICAL) )
				verticalBend(ba.offset);
			else
				horizontalBend(ba.offset);
		}
	}
	
	public Fish(Color c1, Color c2, double s, double da, double db, double ra, double rb, double ba, double bb, int x, int y, int iterations){
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
		system = new ActivatorInhibitorSystem(s,da,db,ra,rb,ba,bb, rows,cols,x,y,iterations);
		
	}
	
	public void horizontalBend(int offset){
		image = NonLinearTransform.bendHorizontal(image, offset);
		updateSystem();
	}
	
	private void updateSystem(){
		int rows = image.getWidth(), cols = image.getHeight();
		system = system.lightCopy(rows,cols);
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
	
	public void draw(Graphics2D g){
	//	system.step();
		system.draw(g,image, colours,width,height);
	}

	
}
