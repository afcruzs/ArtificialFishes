package fishes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MatingExperiment {
	
	static Fish fish1 = RandomFishGenerator.randomFish();
	static Fish fish2 = RandomFishGenerator.randomFish();
	static Fish off1 = null;
	static Fish off2 = null;
	static JFrame frame ;
	public static void main(String[] args) {
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
				
				if(off1 != null)
					off1.draw(g2d);
				
				if(off2 != null)
					off2.draw(g2d);
				
			}
		});
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
