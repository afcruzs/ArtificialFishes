package aquarium;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

public class ConstructFishesArea {
	
	static Area getOutLine(String fileName, Color target){
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(fileName));
		} catch (IOException e) { e.printStackTrace(); }
		
		return new ImageOutline(image).getOutline(target); 
	}
	
	static void save(String fileName,Object o){
		FileOutputStream fout;
		try {
			fout = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(o);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	public static Object readFile(String fileName){
		FileInputStream f_in;
		ObjectInputStream obj_in = null;
		try {
			f_in = new FileInputStream(fileName);
			try {
				obj_in =  new ObjectInputStream (f_in);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			return obj_in.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
		return null;

	}
	
	public static void main(String[] args) {
		Shape shh = getOutLine("fish0.jpg", Color.black);
		shh = AffineTransform.getTranslateInstance(0,0).createTransformedShape(shh);
		

		save("fishOutLine", shh);
	}
}
