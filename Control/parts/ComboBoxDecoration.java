package parts;

import java.awt.Dimension;
import org.jdesktop.swingx.JXComboBox;

import enums.Enums.TextDecoration;
public class ComboBoxDecoration extends JXComboBox {
	/**
	 *
	 */
	private static final long serialVersionUID = -6099492522476497787L;
	final static String[] decoration = {"Underline", "Strike", "Over-line"};

	public ComboBoxDecoration() {
		super(decoration);
		this.setPreferredSize(new Dimension(80,20));
		this.setVisible(true);
		this.setSelectedIndex(1);

	}

	public TextDecoration getSelectedDecoration() {
		TextDecoration value = null;
		switch(super.getSelectedIndex()) {
			case 0:
				value = TextDecoration.UNDERLINE;
				break;
			case 1:
				value = TextDecoration.STRIKE;
				break;
			case 2:
				value = TextDecoration.OVERLINE;
				break;
		}

		return value;
	}


	public void setSelectedStyleDecoration(TextDecoration decoration) {
		int index = 0;
		switch(decoration) {
			case  UNDERLINE:
				index = 0;
				break;
			case STRIKE:
				index = 1;
				break;
			case OVERLINE:
				index = 2;
				break;
		}
		super.setSelectedIndex(index);
	}

}
