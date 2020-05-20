package exercise21;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class App2 extends JFrame {
	private static final long serialVersionUID = 7404313003999511716L;

	private static void addComponentsToPane(Container contentPane) {
		// To generate random RGB value
		Random random = new Random();
		int redValue = random.nextInt(255);
		int greenValue = random.nextInt(255);
		int blueValue = random.nextInt(255);

		// Components at the top of the panel
		JTextField textField = new JTextField("Nord: hi pots escriure aqu�...");
		contentPane.add(textField, BorderLayout.PAGE_START);

		// Components at the center of the panel
		JButton button = new JButton("Oest");
		button.setBackground(new Color(redValue, greenValue, blueValue));
		button.setForeground(Color.WHITE);
		contentPane.add(button, BorderLayout.LINE_START);

		JTextArea textArea = new JTextArea("Centre:" + System.lineSeparator() + "tabme hi pots escriure aqu�...");
		textArea.setPreferredSize(new Dimension(200, 200));
		contentPane.add(textArea, BorderLayout.CENTER);

		button = new JButton("Est");
		button.setBackground(new Color(redValue, greenValue, blueValue));
		button.setForeground(Color.WHITE);
		contentPane.add(button, BorderLayout.LINE_END);

		// Add components inside different panel, then add it at the bottom of
		// the main panel
		JPanel bottomPane = new JPanel(new FlowLayout());

		// Creates 5 buttons, each one with a random background color, and adds
		// them to the bottom pane
		for (int i = 1; i <= 5; i++) {
			redValue = random.nextInt(255);
			greenValue = random.nextInt(255);
			blueValue = random.nextInt(255);

			button = new JButton("Sud " + i);
			button.setBackground(new Color(redValue, greenValue, blueValue));
			button.setForeground(Color.WHITE);
			bottomPane.add(button);
		}

		contentPane.add(bottomPane, BorderLayout.PAGE_END);
	}

	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Simple Frame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addComponentsToPane(frame.getContentPane());
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
