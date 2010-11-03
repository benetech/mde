package gov.nasa.ial.mde.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import gov.nasa.ial.mde.describer.Describer;
import gov.nasa.ial.mde.properties.MdeSettings;
import gov.nasa.ial.mde.solver.Solver;
import gov.nasa.ial.mde.ui.graph.CartesianGraph;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DescriberAndGraphPanel extends JPanel {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6145441370339707300L;
	private  Solver solver;
	private Describer describer;
	private JLabel instructions;
	private JTextField input;
	private JTextArea output;
	private MdeSettings currentSettings;
	private JPanel describerPanel;
	private CartesianGraph graphPanel;
	
	
	public DescriberAndGraphPanel() {
		setupMDE();
		
		this.describerPanel = new JPanel();
		this.graphPanel = new CartesianGraph(solver, currentSettings);
		
		setupDescriberPanel();
		setupGraphPanel();
		
		this.add(describerPanel);
		this.add(graphPanel);
	}

	
	
	private void setupMDE() {
		this.currentSettings = new MdeSettings("myAppsMdeProperties");
		solver = new Solver();
		this.describer = new Describer(solver, currentSettings);
		
		//we can change this if we wanted to change what
		this.describer.setOutputFormat(Describer.TEXT_OUTPUT);  
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
		
		JPanel inputPanel = new JPanel();
		inputPanel.add(instructions);
		inputPanel.add(input);
		
		describerPanel.add(inputPanel, BorderLayout.PAGE_START);
		describerPanel.add(output, BorderLayout.CENTER);
	}
	
	private void setupGraphPanel() {
	}

	private String processEquation(String equation) {
		String description ="OH DEAR GOD\n FULL TILT\nPLEASE RESTART\n";
		try{
			solver.removeAll();
			solver.add(equation);
			solver.solve();
			
			if(solver.anyGraphable())
			{
				graphPanel.drawGraph();
			}
			
			if (solver.anyDescribable()) {
				description = describer.getDescriptions("standards");
					
			} else {
				description="MDE could not generate a description for " + equation + ".";
			}
			
			//
			
		}catch (Exception e) 
		{
			System.out.println(e);
			solver.removeAll();
		}
		return description;
	}
	
	
	public static void main(String[] args){
		
		JFrame frame = new JFrame("Math Description Engine");
		DescriberAndGraphPanel panel = new DescriberAndGraphPanel();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
        //frame.toFront();
	}
	

}
