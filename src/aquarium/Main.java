package aquarium;

import fishes.Fish;
import gui.Background;

import java.awt.Color;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import affineTransforms.NonLinearTransform;
import plants.Bushy;
import plants.CustomStickyTree;
import plants.DrawingTreeEntry;
import plants.LSystem;
import plants.Plant2;
import plants.Plant3;
import plants.StickyTree;

public class Main {

	private static List<DrawingTreeEntry> trees;
	private static List<Fish> fishes;
	static JFrame frame = new JFrame();

	public static void init() {
		trees = new ArrayList<>();
		fishes = new ArrayList<>();
	}

	static double randomParameter() {
		return randomRange(5.0, 30.0);
	}

	static double randomRange(double start, double end) {
		double random = new Random().nextDouble();
		double result = start + (random * (end - start));
		return result;
	}

	public static int randomRange(int min, int max) {
		return new Random().nextInt(max - min + 1) + min;
	}

	// Cartesian coordinates
	public static void addTree(LSystem system, int iterations, int x, int y) {
		trees.add(new DrawingTreeEntry(system, system.make(iterations), x, y));
	}

	public static void addFish(Color c1, Color c2, double s, double da,
			double db, double ra, double rb, double ba, double bb, int x, int y) {
		Fish fish = new Fish(c1, c2, s, da, db, ra, rb, ba, bb, x, y);
		
		
		int sign  = Main.randomRange(0,1) == 0 ? 1 : -1;
		fish.horizontalBend(sign*Main.randomRange(fish.getWidth()/4, fish.getWidth()/2));
		sign  = Main.randomRange(0,1) == 0 ? 1 : -1;
		fish.verticalBend(sign*Main.randomRange(fish.getHeight()/4, fish.getHeight()/2));
		
		
		fish.iterate(100);
		fishes.add(fish);
	}

	static void randomAnimation() {
		new Thread() {
			int moveY(int y) {
				if (Math.random() >= 0.9)
					return y + randomRange(-10, 10);
				return y;
			}

			public void run() {
				while (true) {
					for (Fish f : fishes) {
						int go = f.getX() - 1;
						if (go < -f.getWidth())
							go = frame.getWidth();
						f.setX(go);
						f.setY(moveY(f.getY()));
					}
					try {
						sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		}.start();
	}

	public static void setUpFishes() {
		addFish(Color.BLUE, Color.green, 11.5, 18.5, 12.5, 1.6, 1.6, 21.7, 8.8,
				50, 50);
		addFish(Color.RED, Color.YELLOW, 11.5, 28.5, 2.5, 0.6, 1.6, 21.7, 8.8,
				500, 300);
		addFish(Color.ORANGE, Color.CYAN, 1.5, 8.5, 21.5, 20.6, 1.6, 10.7,
				18.8, 600, 50);
		addFish(Color.PINK, Color.ORANGE, randomParameter(), randomParameter(),
				randomParameter(), randomParameter(), randomParameter(),
				randomParameter(), randomParameter(), 50, 300);
	}

	public static void setUpPlants() {
		// Left side
		addTree(new Plant3(), 7, 50, 8);
		addTree(new Plant3(), 7, 200, 10);
		addTree(new StickyTree(), 7, 400, 0);

		// 'corals'
		addTree(new Plant2(8), 8, 550, 0);
		addTree(new Plant2(8), 8, 650, 0);
		addTree(new Plant2(8), 8, 750, 0);
		addTree(new Plant2(8), 8, 850, 0);
		addTree(new Plant2(8), 8, 950, 0);

		// Right side
		addTree(new CustomStickyTree(), 7, 1150, 0);
		addTree(new Bushy(), 5, 1250, 0);
		addTree(new Bushy(), 5, 1350, 0);

	}

	public static Iterable<DrawingTreeEntry> iterateTreeList() {
		return trees;
	}

	public static Iterable<Fish> iterateFishes() {
		return fishes;
	}

	public static void main(String[] args) throws InvocationTargetException,
			InterruptedException, IOException {
		init();
		setUpPlants();
		setUpFishes();
	//	randomAnimation();
		SwingUtilities.invokeAndWait(new Runnable() {

			@Override
			public void run() {

				frame.setSize(800, 600);
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				try {
					frame.add(new Background());
				} catch (IOException e) {
					e.printStackTrace();
				}
				frame.setExtendedState(frame.getExtendedState()
						| JFrame.MAXIMIZED_BOTH);

				new Thread() {
					int i = 0;

					public void run() {
						while (true) {
							frame.repaint();
							frame.setTitle(i++ + "");
						}
					}
				}.start();

			}
		});
	}

}
