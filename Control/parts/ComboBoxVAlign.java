package parts;

import java.awt.Dimension;

import org.jdesktop.swingx.JXComboBox;

import enums.Enums.SpanVerticalAlign;

public class ComboBoxVAlign extends JXComboBox {
	/**
	 *
	 */
	private static final long serialVersionUID = 6656093049749905889L;
	final static String[] align = {"None", "Super", "Sub"};

	public ComboBoxVAlign() {
		super(align);
		this.setPreferredSize(new Dimension(80,20));
	}

	public SpanVerticalAlign getSelectedAlign() {
		SpanVerticalAlign value = SpanVerticalAlign.NONE;
		switch(super.getSelectedIndex()) {
			case 0:
				value = SpanVerticalAlign.NONE;
				break;
			case 1:
				value = SpanVerticalAlign.SUPER;
				break;
			case 2:
				value = SpanVerticalAlign.SUB;
				break;
		}

		return value;
	}


	public void setSelectedAlign(SpanVerticalAlign align) {
		int index = 0;
		switch(align) {
			case  NONE:
				index = 0;
				break;
			case  SUPER:
				index = 1;
				break;
			case  SUB:
				index = 2;
				break;
		}
		super.setSelectedIndex(index);
	}
}
