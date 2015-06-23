package fishes;

import java.awt.Color;
import java.util.Random;

import aquarium.RandomUtils;

/*
 * Defines the genotype of the fish's skin (Using a Turing Morph)
 */
public class SkinGenotype {
	@Override
	public String toString() {
		return "SkinGenotype [s=" + s + ", Da=" + Da + ", Db=" + Db + ", ra="
				+ ra + ", rb=" + rb + ", ba=" + ba + ", bb=" + bb + ", color1="
				+ color1 + ", color2=" + color2 + ", iterations=" + iterations
				+ "]";
	}


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
		int op = r.nextInt(10),op2,xd;
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
			op2 = r.nextInt(3);
			xd = RandomUtils.randInt(0, 255);
			if( op2 == 0 )
				color1 = new Color(xd,color1.getGreen(),color1.getBlue());
			else if( op2 == 1 )
				color1 = new Color(color1.getRed(),xd,color1.getBlue());
			else
				color1 = new Color(color1.getRed(),color1.getGreen(),xd);
			
			break;
		case 8:
			op2 = r.nextInt(3);
			xd = RandomUtils.randInt(0, 255);
			if( op2 == 0 )
				color2 = new Color(xd,color2.getGreen(),color2.getBlue());
			else if( op2 == 1 )
				color2 = new Color(color2.getRed(),xd,color2.getBlue());
			else
				color2 = new Color(color2.getRed(),color2.getGreen(),xd);
			
			break;
			
		default:
			iterations += r.nextInt(Math.max(1, iterations))*r.nextGaussian();
			iterations = Math.max(iterations, 1);
			break;
		}
		
	}
	
}	
