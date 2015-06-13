package plants;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Stack;


public class CustomStickyTree extends StickyTree {
	
	public CustomStickyTree(){
		size = 2;
		delta = 0.0;
	}
	@Override
	String rule(char variable) {
		if( variable == 'X' )
			//return "F-[[X]+X]+XF[+FX]-X";
			return "F-[[X]+X]+XF[+FX]-X";
		if( variable == 'F' )
			return "FF";
		
		return variable+"";
	}
}
