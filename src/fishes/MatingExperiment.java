package fishes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MatingExperiment {
	
	static Fish fish1 = RandomFishGenerator.randomFish(100);
	static Fish fish2 = RandomFishGenerator.randomFish(100);
	static Fish off1 = null;
	static Fish off2 = null;
	static JFrame frame ;
	static double diff;
	public static void main(String[] args) {
		diff = Fish.colorDifference(fish1, fish2);
		fish1.setX(10);
		fish1.setY(30);
		
		fish1.setWidth(300);
		fish1.setHeight(300);
		
		
		fish2.setX(320);
		fish2.setY(30);
		
		fish2.setWidth(300);
		fish2.setHeight(300);
		
		frame = new JFrame();
		frame.setVisible(true);
		frame.setSize(650,500);
		
		frame.add( new JPanel(){
			{
				add(new JButton("Repaint"){
					{
						addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								frame.repaint();								
							}
						});
					}
				});
				add(new JButton("MATE"){
					{
						addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								ArrayList<Fish> fishes = new ArrayList<Fish>();
								fishes.add(fish2);
								List<Fish> x = fish1.mate(fishes);
								
								
								fish1 = x.get(0);
								fish2 = x.get(1);
								
								fish1.setX(10);
								fish1.setY(30);
								
								fish1.setWidth(300);
								fish1.setHeight(300);
								
								
								fish2.setX(320);
								fish2.setY(30);
								
								fish2.setWidth(300);
								fish2.setHeight(300);
								
								diff = Fish.colorDifference(fish1, fish2);
							}
						});
					}
				});
			}
			@Override
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D)g;
				fish1.draw(g2d);
				fish2.draw(g2d);
				
				g2d.setColor(fish1.getColor1());
				g2d.fillRect(10, 340, 50, 50);
				g2d.setColor(fish1.getColor2());
				g2d.fillRect(60, 340, 50, 50);
				g2d.setColor(fish1.system.averageColor);
				g2d.fillRect(110, 340, 50, 50);
				g2d.drawString(diff+"", 10, 400);
				
				int col[] = fish1.colours;
				int sz = 4;
				for(int i=0; i<col.length; i++){
					g.setColor(new Color(col[i]));
					g.fillRect(sz*i, 420, sz, 10);
					
				}
				
				col = fish2.colours;
				
				for(int i=0; i<col.length; i++){
					g.setColor(new Color(col[i]));
					g.fillRect(sz*i, 440, sz, 10);
					
				}
			}
		});
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
