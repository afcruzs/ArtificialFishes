package flocking;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import fishes.RandomFishGenerator;
import aquarium.RandomUtils;

public class Demo {
	static JFrame mainFrame;
	static JPanel panel = null;
	static Vector<FlockingAgent> agents = new Vector<>();

	static Thread go = new Thread() {
		@Override
		public void run() {
			int lel = 0;
			while (true){
				lel++;
				try {
					sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (FlockingAgent agent : agents) {
					agent.act(agents);
					torus(agent);
				}

				mainFrame.repaint();
				mainFrame.setTitle(lel+"");
			}
		}
	};



	static void torus(FlockingAgent ag) {
		Point position = ag.getPosition();
		if (position.getX() > mainFrame.getWidth())
			position.x = 0;
		if (position.getY() > mainFrame.getHeight()-50)
			position.y = mainFrame.getHeight()-50;

		if (position.x <= 0)
			position.x = mainFrame.getWidth();
		if (position.y <= 50)
			position.y = 50;

		ag.setPosition(position.x, position.y);
	}

	@SuppressWarnings("serial")
	static void initPanel() {
		panel = new JPanel() {
			{
				add(new JButton("Move") {
					{
						addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								initAgents();
								go.start();
							}
						});
					}
				});
			}

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				for (FlockingAgent agent : agents)
					agent.draw(g);
			}
		};
	}

	static void initAgents() {
		int N = 500;
		for (int i = 0; i < N; i++) {
			//FlockingAgent a1 = FlockingAgent.randomFlockingAgent(mainFrame.getSize());
			 FlockingAgent a1 = RandomFishGenerator.randomFish();

			agents.add(a1);
		}
	}

	static void init() {

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
