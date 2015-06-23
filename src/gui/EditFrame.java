package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import flocking.FlockingAgent;
import aquarium.Controller;

@SuppressWarnings("serial")
public class EditFrame extends JFrame {
	
	JTextField populationField, generationsField, iterationsField,sleepTimeField,fishSizeField,
			   radiusField, separationField;
	
	JCheckBox animationCheckBox;
	
	public EditFrame() {
		super("Simulation settings");
		add(new FieldsPanel());
		add(new AcceptButtonsPanel(this),BorderLayout.SOUTH);
		setVisible(true);
		setSize(500,500);
	}
	
	private class AcceptButtonsPanel extends JPanel{
		
		public AcceptButtonsPanel(final JFrame parent) {
			setLayout(new FlowLayout());
			JButton acceptButton = new JButton("Save");
			acceptButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Controller.setPopulationSize( Integer.parseInt(populationField.getText()) );
					Controller.setNumberOfGenerationsPerIteration( Integer.parseInt(generationsField.getText()) );
					Controller.setNumberOfIterations( Integer.parseInt(iterationsField.getText()) );
					Controller.setSleepTime(Long.parseLong(sleepTimeField.getText()));
					Controller.setFishSize(Integer.parseInt(fishSizeField.getText()));
					FlockingAgent.setNeighborRadius( Double.parseDouble(radiusField.getText()) );
					FlockingAgent.setSeparationRadius( Double.parseDouble(separationField.getText()) );
					Controller.setAnimation(animationCheckBox.isSelected());
					parent.dispatchEvent(new WindowEvent(parent, WindowEvent.WINDOW_CLOSING));
				}
			});
			
			add(acceptButton);
			JButton suggested = new JButton("Suggested Flocking Parameters");
			suggested.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					double separation = Double.parseDouble(fishSizeField.getText())*0.9;
					separationField.setText(String.valueOf(separation));
					radiusField.setText(String.valueOf( separation*1.42 ));
				}
			});
			
			add(suggested);
		}
	}
	
	private class FieldsPanel extends JPanel{
		public FieldsPanel() {
			setLayout( new GridLayout(0,2) );
			
			setVisible(true);
			
			populationField = new IntegerJTextField(String.valueOf(Controller.getPopulation()));
			generationsField = new IntegerJTextField(String.valueOf(Controller.getGenerations()));
			iterationsField = new IntegerJTextField(String.valueOf(Controller.getIterations()));
			sleepTimeField = new IntegerJTextField(String.valueOf(Controller.getSleepTime()));
			fishSizeField = new IntegerJTextField(String.valueOf(Controller.getFishSize()));
			radiusField = new DoubleJTextField( String.valueOf(FlockingAgent.NEIGHBOR_RAIDUS) );
			separationField = new DoubleJTextField( String.valueOf(FlockingAgent.SEPARATION_RAIDUS) );
			
			animationCheckBox = new JCheckBox();
			
			animationCheckBox.setSelected(Controller.getAnimation());
			
			add(new JLabel("Initial Population") );
			add(populationField);
			
			add(new JLabel("Number of generations") );
			add(generationsField);		
			
			add(new JLabel("Number of iterations per generation") );
			add(iterationsField);
			
			add(new JLabel("Time between iterations") );
			add(sleepTimeField);
			
			add(new JLabel("Maximum fish size (Pixels)") );
			add(fishSizeField);
			
			
			add(new JLabel("Neighbor Radius") );
			add(radiusField);
			
			add(new JLabel("Separation Radius") );
			add(separationField);
			
			add(new JLabel("Animation") );
			add(animationCheckBox);
			
		}
	}
	
	private class IntegerJTextField extends JTextField {
		public IntegerJTextField(String s){
			super(s);
			addKeyListener();
		}
		
	    
	    void addKeyListener(){
	    	addKeyListener(new KeyAdapter() {
	            public void keyTyped(KeyEvent e) {
	                char ch = e.getKeyChar();

	                if (!isNumber(ch) && !isValidSignal(ch)  && ch != '\b') {
	                    e.consume();
	                }
	            }
	        });
	    }

	    protected boolean isNumber(char ch){
	        return ch >= '0' && ch <= '9';
	    }

	    protected boolean isValidSignal(char ch){
	        if( (getText() == null || "".equals(getText().trim()) ) && ch == '-'){
	            return true;
	        }

	        return false;
	    }

	}
	
	private class DoubleJTextField extends IntegerJTextField{
		
		public DoubleJTextField(String s) {
			super(s);
		}
		
		@Override
		void addKeyListener(){
			addKeyListener(new KeyAdapter() {
	            public void keyTyped(KeyEvent e) {
	                char ch = e.getKeyChar();

	                if (!isNumber(ch) && !isValidSignal(ch) && !validatePoint(ch)  && ch != '\b') {
	                    e.consume();
	                }
	            }
	        });
		}
		
		private boolean validatePoint(char ch){
	        if(ch != '.'){
	            return false;
	        }

	        if(getText() == null || "".equals(getText().trim())){
	            setText("0.");
	            return false;
	        }else if("-".equals(getText())){
	            setText("-0.");
	        }

	        return true;
	    }
	}
	
}
