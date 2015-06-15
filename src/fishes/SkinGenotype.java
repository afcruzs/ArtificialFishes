package fishes;

import java.awt.Color;
import java.util.Random;

/*
 * Defines the genotype of the fish's skin (Using a Turing Morph)
 */
public class SkinGenotype {
	protected double s;
	protected double Da, Db;
	protected double ra, 	rb;
	protected double ba, bb;
	protected Color color1, color2;
	protected int iterations;
	
	
	public SkinGenotype(double s, double da, double db, double ra, double rb,
			double ba, double bb, Color color1, Color color2, int iterations) {
		super();
		this.s = s;
		Da = da;
		Db = db;
		this.ra = ra;
		this.rb = rb;
		this.ba = ba;
		this.bb = bb;
		this.color1 = color1;
		this.color2 = color2;
		this.iterations = iterations;
	}


	public void mutate() {
		Random r = new Random();
		int op = r.nextInt(10),op2;
		float re,g,b;
		switch (op) {
		case 0:
			s += r.nextGaussian();
			break;
		case 1:
			Da += r.nextGaussian();
			break;
		case 2:
			Db += r.nextGaussian();
			break;
		case 3:
			ra += r.nextGaussian();
			break;
		case 4:
			rb += r.nextGaussian();
			break;
		case 5:
			ba += r.nextGaussian();
			break;
		case 6:
			bb += r.nextGaussian();
			break;
		case 7:
	
			break;
		case 8:

			break;
			
		default:
			iterations += r.nextInt(iterations)*r.nextGaussian();
			iterations = Math.max(iterations, 1);
			break;
		}
		
	}
	
}	
