package evolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

public class Operators {
	
	public static void mutateIndividuals(List<Evolvable> currentPopulation) {
		Random rand = new Random();
		
		for(Evolvable ev : currentPopulation)
			if( rand.nextBoolean() )
				ev.mutate();
	}


	public static  List<Evolvable> mateIndividuals(List<Evolvable> currentPopulation) {
		List<Evolvable> offspring = new ArrayList<Evolvable>();
		int N = currentPopulation.size();
		
		for(int i=0; i<N ; ++i){
			for(int j=i+1; j<N; j++){
				offspring.add(currentPopulation.get(i).mate(currentPopulation.get(j)));
			}
		}
		
			
		return offspring;
	}


	public static  List<Evolvable> selectIndividuals(Population population) {
		int threshold = (int) Math.max(1.0,(double)population.size()/4.0);
		
		PriorityQueue<Evolvable> pq = new PriorityQueue<Evolvable>(population.individuals());
		List<Evolvable> best = new ArrayList<Evolvable>(threshold);
		while(threshold-- > 0) best.add(pq.poll());
		
		return best;
	}
}
