package gov.nasa.ial.mde.ui;


import gov.nasa.ial.mde.describer.Describer;
import gov.nasa.ial.mde.properties.MdeSettings;
import gov.nasa.ial.mde.solver.Solver;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class DescriberPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel instructions;
	private JTextField input;
	private JTextArea output;
	private MdeSettings currentSettings;
	private Solver solver;
	private Describer describer;
	
	public DescriberPanel(){
		
		setupMDE();
		
		//Instructions
		instructions = new JLabel("Enter equation and press enter:");
		
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
		
		//setup output
		output = new JTextArea("Your description will appear here.");
		output.setEditable(false);
		
		
		
		
		
		//add components
		this.add(instructions);
		this.add(input);
		this.add(output);
		
	}
	
	
	private void setupMDE() {
		this.currentSettings = new MdeSettings("myAppsMdeProperties");
		this.solver = new Solver();
		this.describer = new Describer(solver, currentSettings);
		
		//we can change this if we wanted to change what
		this.describer.setOutputFormat(Describer.TEXT_OUTPUT);   
	}


	private String processEquation(String equation){
		String description ="OH DEAR GOD\n FULL TILT\nPLEASE RESTART\n";
		try{
			solver.add(equation);
			solver.solve();
			
			if (solver.anyDescribable()) {
				description = describer.getDescriptions("standards");
			} else {
				description="MDE could not generate a description for " + equation + ".";
			}
			solver.removeAll();
			
		}catch (Exception e) 
		{
			System.out.println(e);
		}
		return description;
    }
}
