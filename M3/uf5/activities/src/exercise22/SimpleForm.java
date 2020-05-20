package exercise22;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemListener;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class SimpleForm extends JFrame {
	private static final long serialVersionUID = -8019145440486092511L;
	private static final int FONT_SIZE = 24;
	public static Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
	public static final Font FONT_B = new Font(Font.DIALOG, Font.BOLD, FONT_SIZE);
	public static final Font FONT_BI = new Font(Font.DIALOG, Font.BOLD | Font.ITALIC, FONT_SIZE);
	public static final Font FONT_BIU = new Font(getUnderlineFontAttributes()).deriveFont(Font.BOLD | Font.ITALIC);
	public static final Font FONT_BU = new Font(getUnderlineFontAttributes()).deriveFont(Font.BOLD);
	public static final Font FONT_I = new Font(Font.DIALOG, Font.ITALIC, FONT_SIZE);
	public static final Font FONT_IU = new Font(getUnderlineFontAttributes()).deriveFont(Font.ITALIC);
	public static final Font FONT_U = new Font(getUnderlineFontAttributes());
	public static final Font FONT_PLAIN = new Font(Font.DIALOG, Font.PLAIN, FONT_SIZE);

	private static void addComponentsToPane(Container contentPane) {
		// Create sub-panels
		JPanel checkBoxPane = new JPanel(new GridLayout(3, 1));
		JPanel colorMenu = new JPanel(new FlowLayout());

		// Create components
		JTextArea textArea = new JTextArea("Escriu aqui...");
		textArea.setFont(new Font(Font.DIALOG, Font.PLAIN, FONT_SIZE));
		textArea.setPreferredSize(new Dimension(300, 100));

		JCheckBox checkBoxBold = new JCheckBox("Negreta");
		JCheckBox checkBoxItalic = new JCheckBox("Cursiva");
		JCheckBox checkBoxUnderline = new JCheckBox("Subrallat");

		ButtonGroup buttonGroup = new ButtonGroup();
		JRadioButton colorItem1 = new JRadioButton("Negre", true);
		JRadioButton colorItem2 = new JRadioButton("Blau");
		JRadioButton colorItem3 = new JRadioButton("Vermell");

		// Implement check boxes listener
		ItemListener checkBoxListener = e -> {
			Font font = textArea.getFont();
			if (checkBoxBold.isSelected() && checkBoxItalic.isSelected() && checkBoxUnderline.isSelected()) {
				font = FONT_BIU;
			} else if (checkBoxBold.isSelected() && checkBoxItalic.isSelected()) {
				font = FONT_BI;
			} else if (checkBoxBold.isSelected() && checkBoxUnderline.isSelected()) {
				font = FONT_BU;
			} else if (checkBoxItalic.isSelected() && checkBoxUnderline.isSelected()) {
				font = FONT_IU;
			} else if (checkBoxBold.isSelected()) {
				font = FONT_B;
			} else if (checkBoxItalic.isSelected()) {
				font = FONT_I;
			} else if (checkBoxUnderline.isSelected()) {
				font = FONT_U;
			} else {
				font = FONT_PLAIN;
			}
			textArea.setFont(font);
		};

		// Add listener to each check box
		checkBoxBold.addItemListener(checkBoxListener);
		checkBoxItalic.addItemListener(checkBoxListener);
		checkBoxUnderline.addItemListener(checkBoxListener);

		// Add check boxes to their panel
		checkBoxPane.add(checkBoxBold);
		checkBoxPane.add(checkBoxItalic);
		checkBoxPane.add(checkBoxUnderline);

		// Implement button events
		colorItem1.addActionListener(e -> textArea.setForeground(Color.BLACK));
		colorItem2.addActionListener(e -> textArea.setForeground(Color.BLUE));
		colorItem3.addActionListener(e -> textArea.setForeground(Color.RED));

		// Add buttons to their group
		buttonGroup.add(colorItem1);
		buttonGroup.add(colorItem2);
		buttonGroup.add(colorItem3);

		// Add each button to the button color menu
		colorMenu.add(colorItem1);
		colorMenu.add(colorItem2);
		colorMenu.add(colorItem3);

		// Add components to content pane
		contentPane.add(textArea, BorderLayout.LINE_START);
		contentPane.add(checkBoxPane);
		contentPane.add(colorMenu, BorderLayout.PAGE_END);
	}

	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Simple Form");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addComponentsToPane(frame.getContentPane());
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		SwingUtilities.invokeLater(() -> createAndShowGUI());
	}

	/**
	 * Set's the the fontAttributes variable with the size of the font and the
	 * underline
	 */
	public static Map<TextAttribute, Integer> getUnderlineFontAttributes() {
		fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		fontAttributes.put(TextAttribute.SIZE, FONT_SIZE);
		return fontAttributes;
	}
}
