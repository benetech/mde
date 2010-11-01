package gov.nasa.ial.mde.ui;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import gov.nasa.ial.mde.describer.Describer;
import gov.nasa.ial.mde.properties.MdeSettings;
import gov.nasa.ial.mde.solver.Solver;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DescriberAndGraphPanel extends JPanel {

	
	private Solver solver;
	private Describer describer;
	private JLabel instructions;
	private JTextField input;
	private JTextArea output;
	private MdeSettings currentSettings;
	
	public DescriberAndGraphPanel() {
		

		JPanel describerPanel = new JPanel();
		JPanel graphPanel = new JPanel();
		
		
		setupDescriberPanel();
		setupGraphPanel();
	}

	private void setupDescriberPanel() {
		instructions = new JLabel("Enter equation and press enter:");

		//setup output
		output = new JTextArea("Your description will appear here.");
		output.setEditable(false);
		output.setWrapStyleWord(true);
		output.setLineWrap(true);
		output.setPreferredSize(new Dimension(240, 240));
		
		//input
		input = new JTextField(20);
		input.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				int val = e.getKeyCode();
				if(val == KeyEvent.VK_ENTER){
					output.setText(processEquation(input.getText()));
					input.setText("");
				}
			}
		});		
	}
	
	private void setupGraphPanel() {
		// TODO Auto-generated method stub
		
	}

	private String processEquation(String text) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
