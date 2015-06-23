package DRSystem;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import aquarium.ColorUtils;
import aquarium.ConstructFishesArea;

public class ActivatorInhibitorSystem {
	private double s;
	private double Da, Db;
	private double ra, 	rb;
	private double ba, bb;
	private double A[][], B[][];
	private double k;
	int x,y;
	private int iterations;
	public Color averageColor;
	
	
	public ActivatorInhibitorSystem(double s, double da, double db, double ra,
			double rb, double ba, double bb, int rows, int cols,int x, int y, int iterations) {
		super();
		this.s = s;
		Da = da;
		Db = db;
		this.ra = ra;
		this.rb = rb;
		this.ba = ba;
		this.bb = bb;
		this.A = new double[rows][cols];
		this.B = new double[rows][cols];
		this.k = 0.01;
		this.x = x;
		this.y = y;
		this.iterations = iterations;
		randomInit();
		//iterateSystem();
	}
	
	public ActivatorInhibitorSystem lightCopy(int rows, int cols){
		return new ActivatorInhibitorSystem(s, Da, Db, ra, rb, ba, bb, rows, cols, x, y, iterations);
		
	}
	
	public void iterateSystem(){
		for(int i=0; i<iterations; i++)
			step();
		
	}
	
	public int getRows() {
		return A.length;
	}



	public int getCols() {
		return A[0].length;
	}



	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	


	public void step(){
		ArrayList<double[]> changesA = new ArrayList<double[]>();
		ArrayList<double[]> changesB = new ArrayList<double[]>();
		for (int i = 1; i < A.length; i++) {
			int prev_i = i-1;
			if( prev_i < 0 ) prev_i = getRows()-1;
			int post_i = (i+1) % getRows();
			for (int j = 1; j < A[i].length; j++) {
				int prev_j = j-1;
				if( prev_j < 0 ) prev_j = getCols()-1;
				int post_j = (j+1) % getCols();
				
				double aith = A[i][j] + s*( A[i][j]*A[i][j]/B[i][j] + ba ) -
						ra*A[i][j] + Da*( A[prev_i][j] + A[post_i][j] - 2*A[i][j] );
				
				double ajth = A[i][j] + s*( A[i][j]*A[i][j]/B[i][j] + ba ) -
						ra*A[i][j] + Da*( A[i][prev_j] + A[i][post_j] - 2*A[i][j] );
				
				
				double bith = B[i][j] + s*A[i][j]*A[i][j] - rb*B[i][j] +
							Db*(B[prev_i][j] + B[post_i][j] - 2*A[i][j]) + bb;

				double bjth = B[i][j] + s*A[i][j]*A[i][j] - rb*B[i][j] +
							Db*(B[i][prev_j] + B[i][post_j] - 2*A[i][j]) + bb;
				
//				A[i][j] = A[i][j] + k*(aith+ajth);
//				B[i][j] = B[i][j] + k*(bith+bjth);
//				
//				if( A[i][j] <= 0.0 ) A[i][j] = 0.01; //HUEHUEHUEHE
//				if( B[i][j] <= 0.0 ) B[i][j] = 0.01;
				
				changesA.add( new double[]{ i, j, A[i][j] + k*(aith+ajth) } );
				changesB.add( new double[]{ i, j, B[i][j] + k*(bith+bjth) } );
				
			}
		}
		
		for(double[] data : changesA){
			int i = (int) data[0];
			int j = (int) data[1];
			double d = data[2];
			
			A[i][j] = d;
			if( A[i][j] <= 0.0 ) A[i][j] = 0.01; //HUEHUEHUEHE
		}
		
		for(double[] data : changesB){
			int i = (int) data[0];
			int j = (int) data[1];
			double d = data[2];
			
			B[i][j] = d;
			if( B[i][j] <= 0.0 ) B[i][j] = 0.01; //HUEHUEHUEHE
		}
	}
	
	void randomInit(){
		Random r = new Random();
		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getCols(); j++) {
				this.A[i][j] = r.nextDouble();
				this.B[i][j] = r.nextDouble();
				
				if( A[i][j] <= 0 ) A[i][j] = 0.01;
				if( B[i][j] <= 0 ) B[i][j] = 0.01;
				
			}
		}
	}
	
	public int scale( int maxScale, double minRange, double maxRange, double value ){
		int t = (int) ((value - minRange) * ((double)maxScale) / (maxRange-minRange));
		t = Math.max( 0, Math.min( 255, t ) );
		return t;
	}
	
	public double[] getMinMax(double data[][]){
		double min = Double.POSITIVE_INFINITY;
		double max = Double.NEGATIVE_INFINITY;
		for(int i=0; i<getRows(); i++){
			for(int j=0; j<getCols(); j++){
				min = Math.min(min, data[i][j]);
				max = Math.max(max,data[i][j]);
			}
		}
		return new double[]{min,max};
	}
	
	
		
	boolean isTransparent(int pixel){
		return (pixel>>24) == 0x00;
	}
	
	public void paintFish(BufferedImage image,int[] colours){
		double minMax[] = getMinMax(A);
		double minRange = minMax[0];
		double maxRange = minMax[1];
		
		Color c = null;
 		
		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getCols(); j++) {
				if( !isTransparent(image.getRGB(i, j)) && image.getRGB(i, j) != Color.BLACK.getRGB() ){
					image.setRGB( i, j, colours[ scale(255,minRange,maxRange,A[i][j]) ] );
					Color t = new Color(colours[ scale(255,minRange,maxRange,A[i][j]) ]);
					if( c == null ) c = t;
					else c = ColorUtils.blend(c, t);
				}
			}
		}
		
		averageColor = c;
	}
		
	public void draw(Graphics2D g, BufferedImage image, int[] colours, int width, int height){
		
		g.setRenderingHint(
			    RenderingHints.KEY_ANTIALIASING,
			    RenderingHints.VALUE_ANTIALIAS_ON);
		
 		//g.drawImage(image, x, y, null);
		
		//g.drawImage(image,af,null);
 		g.drawImage(image, x, y, x+width, y+height, 0, 0, image.getWidth(), image.getHeight(), null);		
	}
	
	public void drawInCorner(Graphics2D g, BufferedImage image, int[] colours, int width, int height){
		g.setRenderingHint(
			    RenderingHints.KEY_ANTIALIASING,
			    RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(image, 0, 0, width, height, 0, 0, image.getWidth(), image.getHeight(), null);
	}

//	public static void main(String[] args) thgetRows() IOException {
//		double s = 11.5;
//		double da = 18.5;
//		double db = 12.5;
//		double ra = 1.6;
//		double rb = 1.6;
//		double ba = 21.7;
//		double bb = 8.8;
//		
//
//		final int[] colours = new int[256];
//		colours[0] = Color.GREEN.getRGB();
//		colours[1] = Color.YELLOW.getRGB();
//		for(int i=2; i<colours.length; i++){
//			colours[i] = new Color(colours[i-1]|colours[i-2]).darker().getRGB();
//		}
//		final BufferedImage image = /*new BufferedImage(getRows(), getCols(), BufferedImage.TYPE_INT_RGB);*/
//				ImageIO.read(new File("fish0.png")); 
//		int getRows() = image.getWidth(), getCols() = image.getHeight();
//		final ActivatorInhibitorSystem system = new ActivatorInhibitorSystem(s, da, db, ra, rb, ba, bb, getRows(),getCols(),0,0,1);
//		
//
//		
//	//	for(int i=0; i<100; i++) system.step();
//		JPanel panel  = new JPanel(){
//			@Override
//			public void paintComponent(Graphics g){
//				super.paintComponents(g);
//				system.draw((Graphics2D) g,  image, colours);
//			}
//		};
//		final JFrame frame = new JFrame();
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setVisible(true);
//		frame.add(panel);
//		frame.setSize(getRows(), getCols());
//		
//		
//		//for(int i=0; i<getRows(); i++) System.out.println(Arrays.toString(system.A[i]));
//		new Thread(){
//			@Override
//			public void run(){
//				int it = 0;
//				while(true){
//					system.step();
//					frame.repaint();
//					frame.setTitle( String.valueOf(it++) + " " + String.valueOf(system.A[0][0]) );
//				}
//				
//			}
//		}.start();
//		
//		//double xd[][] = system.scaleData(system.A);
//		//for(int i=0; i<getRows(); i++) System.out.println(Arrays.toString(xd[i]));
//	}
	
	
	
}
