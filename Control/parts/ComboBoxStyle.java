package parts;

import java.awt.Dimension;
import java.awt.Font;

import org.jdesktop.swingx.JXComboBox;

public class ComboBoxStyle extends JXComboBox {
	/**
	 *
	 */
	private static final long serialVersionUID = -6876222732767752834L;
	final static String[] styles = {"Plain", "Bold", "Italic", "BoldItalic"};

	public ComboBoxStyle() {
		super(styles);

		this.setPreferredSize(new Dimension(80,20));
	}

	public int getSelectedStyle() {
		int value = 0;
		switch(super.getSelectedIndex()) {
			case 0:
				value = Font.PLAIN;
				break;
			case 1:
				value = Font.BOLD;
				break;
			case 2:
				value = Font.ITALIC;
				break;
			case 3:
				value = Font.BOLD + Font.ITALIC;
				break;
		}

		return value;
	}


	public void setSelectedStyle(int style) {
		int index = 0;
		switch(style) {
			case  Font.PLAIN:
				index = 0;
				break;
			case Font.BOLD:
				index = 1;
				break;
			case Font.ITALIC:
				index = 2;
				break;
			case Font.BOLD + Font.ITALIC:
				index = 3;
				break;
		}
		super.setSelectedIndex(index);
	}


}
