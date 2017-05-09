package parts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import org.jdesktop.swingx.JXComboBox;

final public class ComboBoxFont extends JXComboBox {
	/**
	 *
	 */
	private static final long serialVersionUID = 5119530663157852442L;
	static String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	static List<String> fontList = Arrays.asList(fonts);
	private ComboBoxFont comboBox;
	private String fontFamily = null;

	public ComboBoxFont() {
		super(fonts);

		comboBox = this;
		this.getEditor().getEditorComponent().setBackground(Color.WHITE);

		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					fontFamily = comboBox.getSelectedItem().toString();
				} catch(Exception ex) {
					fontFamily = null;
				}

				if(fontFamily == null || !fontList.contains(fontFamily))
				{
					comboBox.getEditor().getEditorComponent().setBackground(Color.PINK);
				} else {
					comboBox.getEditor().getEditorComponent().setBackground(Color.WHITE);
				}
			}

		});

		this.setPreferredSize(new Dimension(170,20));
	}



	public String getFontFamily() {
		if(comboBox != null) return comboBox.getSelectedItem().toString();
		else return null;
	}

	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;

		if(fontFamily == null || !fontList.contains(fontFamily))
		{
			fontFamily = "null";
			comboBox.getEditor().getEditorComponent().setBackground(Color.PINK);
		} else {
			comboBox.getEditor().getEditorComponent().setBackground(Color.WHITE);
		}
		comboBox.setSelectedItem(fontFamily);
	}
}
