package aquarium;

import java.util.Collections;
import java.util.List;

public class AlgorithmUtils {
	public static <T extends Comparable<? super T>> T median( List<T> c ){
		Collections.sort(c);
		return c.get(c.size()/2);
	}
}
