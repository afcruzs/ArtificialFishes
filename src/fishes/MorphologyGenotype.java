package fishes;

import java.util.ArrayList;
import java.util.List;

import fishes.MorphologyGenotype.BendAction.BendType;

public class MorphologyGenotype {
	protected List<BendAction> bends;

	public MorphologyGenotype() {
		bends = new ArrayList<>();
	}
	
	public void addBendAction( BendType type, int offset ){
		bends.add( new BendAction(offset, type) );
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
}
