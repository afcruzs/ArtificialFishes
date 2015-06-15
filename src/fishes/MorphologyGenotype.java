package fishes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fishes.MorphologyGenotype.BendAction.BendType;

public class MorphologyGenotype {
	protected List<BendAction> bends;

	public MorphologyGenotype() {
		bends = new ArrayList<>();
	}

	public MorphologyGenotype(List<BendAction> b) {
		bends = new ArrayList<>();
		for (BendAction ba : b) {
			bends.add(ba);
		}
	}

	public void addBendAction(BendType type, int offset) {
		bends.add(new BendAction(offset, type));
	}

	public static class BendAction {
		enum BendType {
			VERTICAL, HORIZONTAL
		};

		int offset;
		BendType type;

		public BendAction(int offset, BendType type) {
			super();
			this.offset = offset;
			this.type = type;
		}

	}

	public void mutate() {
		Random r = new Random();
		if (bends.isEmpty()) {
			bends.add(new BendAction(r.nextInt(10),
					r.nextBoolean() ? BendType.VERTICAL : BendType.HORIZONTAL));
			
		}else{
			if( r.nextBoolean() ){
				bends.add(new BendAction(r.nextInt(10),
						r.nextBoolean() ? BendType.VERTICAL : BendType.HORIZONTAL));
			}else{
				int idx = r.nextInt(bends.size());
				BendAction ba = bends.get(idx);
				ba.offset += ba.offset*r.nextGaussian();
				ba.offset = Math.max(10, ba.offset);
			}
		}
	}
}
