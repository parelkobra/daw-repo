package exercise23;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class SimpleForm extends JFrame {
	private static final long serialVersionUID = -7723579519014592400L;

	private static JPanel commandBar(JPanel basePane) {
		JPanel commandBarPane = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));

		JMenuBar commandBarMenu = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		JMenu formsMenu = new JMenu("Forms");
		JMenu helpMenu = new JMenu("Help");

		JMenuItem fileMenuItem = new JMenuItem("Close");
		JMenuItem formsMenuItem1 = new JMenuItem("Form 1");
		JMenuItem formsMenuItem2 = new JMenuItem("Form 2");
		JMenuItem helpMenuItem = new JMenuItem("About");

		// TODO add listeners
		fileMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		// TODO
		formsMenuItem1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		formsMenuItem2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});

		helpMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame about = new JFrame();
				JOptionPane.showMessageDialog(about, "This is a random message.");
			}
		});

		fileMenu.add(fileMenuItem);
		formsMenu.add(formsMenuItem1);
		formsMenu.add(formsMenuItem2);
		helpMenu.add(helpMenuItem);

		commandBarMenu.add(fileMenu);
		commandBarMenu.add(formsMenu);
		commandBarMenu.add(helpMenu);

		commandBarPane.add(commandBarMenu);

		return commandBarPane;
	}

	// Creates a form panel
	// TODO I must change the layout
	private static JPanel formPanel() {
		JPanel formPanel = new JPanel(new SpringLayout());
		String[] labels = { "Name", "Email", "Addres", "Phone num." };

		for (String labelText : labels) {
			JLabel label = new JLabel(labelText);
			formPanel.add(label);
			JTextField textField = new JTextField(10);
			label.setLabelFor(textField);
			formPanel.add(textField);
		}

		return formPanel;
	}

	private static void addComponentsToPane(Container contentPane) {
		JPanel basePane = new JPanel(new BorderLayout());
		basePane.add(commandBar(basePane), BorderLayout.PAGE_START);
		contentPane.add(basePane);
		basePane.add(formPanel(), BorderLayout.CENTER);
	}

	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Simple Form");
		frame.setSize(new Dimension(400, 250));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addComponentsToPane(frame.getContentPane());
		// frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
