package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import aquarium.Controller;
import fishes.Fish;

public class FishesVisualizer extends JFrame{
	
	JList<Fish> list;
	FishInfo fishInfo;
	public FishesVisualizer() {
		setLayout( new GridLayout(2,0) );
		list = new JList<Fish>( Controller.getFishesArray() );
		list.setSelectedIndex(0);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				list.getSelectedValue().iterateSystem();
				fishInfo.setFish(list.getSelectedValue());
				repaint();
			}
		});
		
		fishInfo = new FishInfo();
		add(new JScrollPane(list));
		add(fishInfo);
		setSize(400, 600);
		setVisible(true);

	}
	
		
	
	public static void main(String[] args) {
		new FishesVisualizer();
	}
}
