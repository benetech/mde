package gov.nasa.ial.mde.ui;


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
	
	
	public DescriberPanel(){
		//Instructions
		instructions = new JLabel("Enter equation and press enter:");
		//input
		input = new JTextField(20);
		input.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				int val = e.getKeyCode();
				if(val == KeyEvent.VK_ENTER){
					System.out.println("\n\n"+input.getText());
					input.setText("");
					output.setText(processEquation("2"));
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
	
	
	private String processEquation(String equation){
		return equation;
	}
}
