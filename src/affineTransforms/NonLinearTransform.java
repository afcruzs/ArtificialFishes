package affineTransforms;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.analysis.polynomials.PolynomialFunctionLagrangeForm;

public class NonLinearTransform{
	
	public static BufferedImage bendVertical(BufferedImage image, int offset){
		try{
			BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight()+Math.abs(offset), image.getType());
			PolynomialFunctionLagrangeForm poly = null;
			double X[] = new double[3];
			double Y[] = new double[3];
			
			X[0] = 0.0; 
			X[1] = image.getWidth()/2; 
			X[2] = image.getWidth(); 
			int mostNegative = 0;
			for(int j=0; j<image.getHeight(); j++){
				Y[0] = Y[2] = j;
				Y[1] = j+offset;
				poly = new PolynomialFunctionLagrangeForm(X, Y);
				for(int i=0; i<image.getWidth(); i++){
					int newy = (int) poly.value((double)i);
					if(newy < 0 && newy < mostNegative) mostNegative = newy;
				}
			}
			mostNegative = Math.abs(mostNegative);
			for(int j=0; j<image.getHeight(); j++){ //Iterates again in order to actually change the image
				Y[0] = Y[2] = j;					//The trade-off is time vs memory, as images could be huge...
				Y[1] = j+offset;
				poly = new PolynomialFunctionLagrangeForm(X, Y);
				for(int i=0; i<image.getWidth(); i++){
					int val = image.getRGB(i, j);
					int newx = i;
					int newy = (int) poly.value((double)i);
					newImage.setRGB(newx, newy+mostNegative, val);
				}
			}
			
			return newImage;
		}catch(FunctionEvaluationException fee){
			fee.printStackTrace();
		}
		return image;
	}
	
	public static BufferedImage bendHorizontal(BufferedImage image,int offset){
		try{
			BufferedImage newImage = new BufferedImage(image.getWidth()+Math.abs(offset), image.getHeight(), image.getType());
			PolynomialFunctionLagrangeForm poly = null;
			double X[] = new double[3];
			double Y[] = new double[3];
			X[0] = 0.0; 
			X[1] = image.getHeight()/2; 
			X[2] = image.getHeight();
			int mostNegative = 0;
			for(int i=0; i<image.getWidth(); i++){
				Y[0] = Y[2] = i;
				Y[1] = i+offset;
				poly = new PolynomialFunctionLagrangeForm(X, Y);
				for(int j=0; j<image.getHeight(); j++){
					int newx = (int) poly.value((double)j);
					if( newx < 0 && newx < mostNegative ) mostNegative = newx;
				}
			}
			
			mostNegative = Math.abs(mostNegative);
			for(int i=0; i<image.getWidth(); i++){//Iterates again in order to actually change the image
				Y[0] = Y[2] = i;				  //The trade-off is time vs memory, as images could be huge...
				Y[1] = i+offset;
				poly = new PolynomialFunctionLagrangeForm(X, Y);
				for(int j=0; j<image.getHeight(); j++){
					int val = image.getRGB(i, j);
					int newx = (int) poly.value((double)j);
					int newy = j;
					newImage.setRGB(newx+mostNegative, newy, val);
				}
			}
			
			image = newImage;
		}catch(FunctionEvaluationException fee){
			fee.printStackTrace();
		}
		return image;
	}
	
	static void go(final BufferedImage img){
		JFrame frame = new JFrame("TKJASLDSAd");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new JPanel(){
			@Override
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.drawImage(img, 10, 10, null);
			}
		});
		frame.setVisible(true);
		frame.setSize(800, 600);
	}
	
	public static void main(String[] args) throws IOException{
		BufferedImage img = ImageIO.read(new File("square.png"));
		img = NonLinearTransform.bendVertical(img, 100);
		img = NonLinearTransform.bendHorizontal(img, 150);
		
		go(img);
	}
}
