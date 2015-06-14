package fishes;

import java.awt.Color;

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
	
}	
