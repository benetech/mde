package gov.nasa.ial.mde.ui;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DescriberPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel instructions;
	private JTextField input;
	
	public DescriberPanel(){
		instructions = new JLabel("Enter equation and press enter:");
		input = new JTextField(20);
		input.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				int val = e.getKeyCode();
				if(val == KeyEvent.VK_ENTER){
					System.out.println("\n\n"+input.getText());
					input.setText("");
					System.out.println("ENTER pressed");
				}
			}
		});
		this.add(instructions);
		this.add(input);
		//this.add(output);
		
	}
}
