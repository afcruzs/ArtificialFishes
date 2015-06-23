package gui;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import aquarium.Controller;
import fishes.Fish;

public class FishesVisualizer extends JFrame{
	
	JList<Fish> list;
	FishInfo fishInfo;
	Fish[] arrRef;
	public FishesVisualizer() {
		setLayout( new GridLayout(2,0) );
		arrRef = Controller.getFishesArray();
		list = new JList<Fish>( arrRef );
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
		
		addWindowListener(new WindowAdapter()
		{
		    @Override
		    public void windowClosing(WindowEvent we)
		    {
		        for(int i=0; i<arrRef.length; i++)
		        	arrRef[i] = null;
		        
		        arrRef = null;
		        list = null;
		        fishInfo = null;
		    }
		});

	}
	
		
	
	public static void main(String[] args) {
		new FishesVisualizer();
	}
}
