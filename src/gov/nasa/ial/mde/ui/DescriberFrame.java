package gov.nasa.ial.mde.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;


public class DescriberFrame {
	
	public static void main(String[] args)
	{
		//1. Create the frame.
		JFrame frame = new JFrame("MDE Decriber Driver");

		//2. Optional: What happens when the frame closes?
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//3. Create components and put them in the frame.
		DescriberPanel panel = new DescriberPanel();
        //panel.setPreferredSize(new Dimension(175, 100));

		frame.getContentPane().add(panel, BorderLayout.CENTER);

		//4. Size the frame.
		frame.pack();

		//5. Show it.
		frame.setVisible(true);
	}

}
