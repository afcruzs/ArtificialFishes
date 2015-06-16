package flocking;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Demo {
	static JFrame mainFrame;
	static JPanel panel = null;
	static List<FlockingAgent> agents = new ArrayList<>();
	
	@SuppressWarnings("serial")
	static void initPanel(){
		panel = new JPanel(){
			{
				add( new JButton("Move"){
					{
						addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent arg0) {
								for(FlockingAgent agent : agents)
									agent.move();
								
								mainFrame.repaint();
							}
						});
					}
				});
			}
			@Override
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				for(FlockingAgent agent : agents)
					agent.draw(g);
			}
		};
	}
	
	static void initAgents(){
		FlockingAgent a1 = new FlockingAgent(); 
		a1.setVector(10, 10);
		agents.add(a1);
		a1.setPosition(0,0);
		FlockingAgent a2 = new FlockingAgent(); 
		a2.setPosition(0,0);
		a2.setVector(10, 30);
		agents.add(a2);
	}
	
	static void init(){
		initAgents();
		initPanel();
		mainFrame = new JFrame("Flocking demo :)");
		assert panel != null;
		mainFrame.add(panel);
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(600, 600);
	}
	
	public static void main(String[] args) {
		init();
	}
}
