package fishes;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import aquarium.ColorUtils;

public class ColorTest {
	public static void main(String[] args) {
		
		JFrame f = new JFrame();
		f.setSize(600, 600);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final int col[] = ColorUtils.gradient(Color.red, Color.yellow, 10);
		
		f.add( new JPanel(){
			public void paintComponent(Graphics gg){
				super.paintComponent(gg);
				
				int sz = 10;
				for(int i=0; i<col.length; i++){
					gg.setColor(new Color(col[i]));
					gg.fillRect(sz*i, 30, sz, sz);
					
				}
			}
		});
	}
}
