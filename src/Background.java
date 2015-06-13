import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;


public class Background extends JPanel {
	
	List<LSystem> trees;
	
	public Background() {
		trees = new ArrayList<>();
		trees.add(new Koch());
		setBackground(Color.WHITE);

		
	}
	
	@Override
	public void paintComponent(Graphics g){

		super.paintComponent(g);
		LSystem l = trees.get(0); 
		l = new FractalPlant();
		Graphics2D g2 = (Graphics2D)g;
		RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_TEXT_ANTIALIASING,
	             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    g2.setRenderingHints(rh);
		
		l.draw(g2, l.make(5),500,550);
	}
}
