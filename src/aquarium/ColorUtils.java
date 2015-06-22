package aquarium;	

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import aquarium.ColorUtils.ColorPopulation.ColorIndividual;
import evolution.CrossoverOperator;
import evolution.EvolutionaryAlgorithm;
import evolution.Evolvable;
import evolution.Life;
import evolution.MutationOperator;
import evolution.Operators;
import evolution.Population;
import evolution.PopulationFactory;
import evolution.SelectionOperator;

public class ColorUtils {
    //Based on http://stackoverflow.com/questions/2103368/color-logic-algorithm
	
	static final double MAX_DIFFERENCE = 764.8333151739665;
    public static double ColourDistance(Color e1, Color e2){
        long rmean = ( (long)e1.getRed() + (long)e2.getRed() ) / 2;
        long r = (long)e1.getRed() - (long)e2.getRed();
        long g = (long)e1.getGreen() - (long)e2.getGreen();
        long b = (long)e1.getBlue() - (long)e2.getBlue();
        return Math.sqrt((((512+rmean)*r*r)>>8) + 4*g*g + (((767-rmean)*b*b)>>8))/MAX_DIFFERENCE;
    	
//    	return Math.sqrt( (e1.getRed()-e2.getRed())*(e1.getRed()-e2.getRed()) +
//    					  (e1.getGreen()-e2.getGreen())*(e1.getGreen()-e2.getGreen()) +
//    					  (e1.getBlue()-e2.getBlue())*(e1.getBlue()-e2.getBlue()) ) / 441.16729559;
    }
    
    
    public static boolean isTransparent(int pixel){
		return (pixel>>24) == 0x00;
	}
    
    
    
    public static Color randomColor(){
    	Random rand = new Random();
    	int r = rand.nextInt(256);
    	int g = rand.nextInt(256);
    	int b = rand.nextInt(256);
    	
    	return new Color(r,g,b);
    }
    
    public static Color blend(Color c0, Color c1) {
        double totalAlpha = c0.getAlpha() + c1.getAlpha();
        double weight0 = c0.getAlpha() / totalAlpha;
        double weight1 = c1.getAlpha() / totalAlpha;

        double r = weight0 * c0.getRed() + weight1 * c1.getRed();
        double g = weight0 * c0.getGreen() + weight1 * c1.getGreen();
        double b = weight0 * c0.getBlue() + weight1 * c1.getBlue();
        double a = Math.max(c0.getAlpha(), c1.getAlpha());

        return new Color((int) r, (int) g, (int) b, (int) a);
      }
    
    public static void main(String[] args) {
    	//System.out.println( ColourDistance(Color.BLACK, Color.WHITE) );
    	double maxm = -1.0;
    	for(int i=0; i<1000; i++){
    		@SuppressWarnings("unchecked")
    		EvolutionaryAlgorithm<ColorPopulation> ea = 
    				new EvolutionaryAlgorithm<>(ColorPopulation.randomPopulation(), new Life(){

    					@Override
    					public void live(Population population) {
    						// TODO Auto-generated method stub
    						
    					}
    				}, new PopulationFactory(){

    					@Override
    					public Population createPopulation(
    							List<Evolvable> currentPopulation) {
    						ColorPopulation cp = new ColorPopulation();
    						for(Evolvable ev : currentPopulation){
    							cp.add((ColorIndividual) ev);
    						}
    						return cp;
    					}
    					
    				}, 
    				
    				selection, mutation, crossover);
    		
    		ColorIndividual cl = (ColorIndividual)ea.evolveBestIndividual(1000);
    		maxm = Math.max(maxm,cl.fitness());
    	}
    	
    	System.out.println(maxm);
		
	}
    
    static class ColorPopulation implements Population{

    	ArrayList<Evolvable> population;
    	ColorIndividual best = null;
    	
    	static ColorPopulation randomPopulation(){
    		int N = 50;
    		ColorPopulation cp = new ColorPopulation();
    		for(int i=0; i<N; i++){
    			ColorIndividual cl = new ColorIndividual(randomColor(), randomColor());
    			cp.add(cl);
    		}
    		
    		cp.add(new ColorIndividual(Color.BLACK, Color.WHITE));
    		return cp;
    	}
    	
    	public ColorPopulation() {
			population = new ArrayList<>();
			best = null;
    	}
    	
    	
    	void add(ColorIndividual cl){
    		population.add(cl);
    		if( best == null || best.fitness() < cl.fitness() )
    			best = cl;
    	}
    	
		@Override
		public Population clone() {
			ColorPopulation cp = new ColorPopulation();
			
			for(Evolvable cl : population) 
				cp.add(((ColorIndividual) cl).clone());
			
			return cp;
		}

		@Override
		public Collection<Evolvable> individuals() {
			return population;
		}

		@Override
		public int size() {
			return population.size();
		}

		@Override
		public Evolvable getBestIndividual() {
			return best;
		}
    	
		static class ColorIndividual implements Evolvable{
			
			
			Color c1,c2 ;
			
			public ColorIndividual clone(){
				Color a = new Color(c1.getRed(),c1.getGreen(),c1.getBlue());
				Color b = new Color(c2.getRed(),c2.getGreen(),c2.getBlue());
				return new ColorIndividual(a, b);
				
			}
			
			public ColorIndividual(Color c1, Color c2) {
				super();
				this.c1 = c1;
				this.c2 = c2;
			}

			@Override
			public int compareTo(Evolvable arg0) {
				double f1 = fitness();
				double f2 = arg0.fitness();
				
				if(f1 > f2) return 1;
				else if(f1 < f2) return -1;
				else return 0;
			}

			@Override
			public double fitness() {
				return ColourDistance(c1, c2);
			}
			
			public int[] rawCode(){
				return new int[]{c1.getRed(),c1.getGreen(),c1.getBlue(),c2.getRed(),c2.getGreen(),c2.getBlue()};
			}
			
			public static ColorIndividual make(int[] code) {
				Color a = new Color(code[0],code[1],code[2]);
				Color b = new Color(code[3],code[4],code[5]);
				
				return new ColorIndividual(a, b);
			}

			@Override
			public Evolvable mate(Evolvable evolvable) {
				ColorIndividual partner = (ColorIndividual)evolvable;
				int[] geneticCode1 = rawCode();
				int[] geneticCode2 = partner.rawCode();
				
				int pivot = new Random().nextInt(geneticCode1.length);
				
				int[] offspring1 = new int[geneticCode1.length];

				for (int i = 0; i < pivot; i++) {
					offspring1[i] = geneticCode1[i];
				}
				
				for (int i = pivot; i < geneticCode1.length; i++) {
					offspring1[i] = geneticCode2[i];
				}
				
				
				
				return make(offspring1);
			}

			@Override
			public void mutate() {
				int code[] = rawCode();
				int r = new Random().nextInt(code.length);
				code[r] = (code[r] + new Random().nextInt(256)) % 255;
				
				c1 = new Color(code[0],code[1],code[2]);
				c2 = new Color(code[3],code[4],code[5]);
			}
			
			public String toString(){
				return fitness()+"";
			}
			
		}
    }
    static int MAX = 50;
	static SelectionOperator selection = new SelectionOperator() {
			
			@Override
			public List<Evolvable> selectIndividuals(Population population) {
				List<Evolvable>  ans =  Operators.selectIndividuals(population);
				if( ans.size() > MAX ) Collections.sort(ans);
				while( ans.size() > MAX ) ans.remove(ans.get(0));
				return ans;
			
			}
		};
		
		static CrossoverOperator crossover = new CrossoverOperator() {
			
			@Override
			public List<Evolvable> mateIndividuals(List<Evolvable> currentPopulation) {
				return Operators.mateIndividuals(currentPopulation);
			}
		};
		
		static MutationOperator mutation = new MutationOperator() {
			
			@Override
			public void mutateIndividuals(List<Evolvable> currentPopulation) {
				Operators.mutateIndividuals(currentPopulation);
			}
		};
}
