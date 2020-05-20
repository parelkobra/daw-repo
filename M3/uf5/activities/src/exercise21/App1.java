package exercise21;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class App1 extends JFrame {
	private static final long serialVersionUID = 7404313003999511716L;

	private static void addComponentsToPane(Container contentPane) {
		JLabel label = new JLabel("T�tol del formulari");
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(Color.WHITE);
		contentPane.add(label, BorderLayout.PAGE_START);

		label = new JLabel("Menu");
		label.setForeground(Color.WHITE);
		contentPane.add(label, BorderLayout.LINE_START);

		JTextArea textArea = new JTextArea("Quadre de text:" + System.lineSeparator() + "...");
		textArea.setPreferredSize(new Dimension(200, 100));
		contentPane.add(textArea);

		JPanel bottomPane = new JPanel(new FlowLayout());
		JButton button = new JButton("Boto 1");
		button.setBackground(Color.RED);
		bottomPane.add(button);

		for (int i = 2; i <= 3; i++) {
			button = new JButton("Boto " + i);
			bottomPane.add(button);
		}

		contentPane.add(bottomPane, BorderLayout.PAGE_END);

		contentPane.setBackground(Color.BLACK);
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
