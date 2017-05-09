package components;


import style.BaseStyleSource;

import org.jdesktop.swingx.JXTaskPane;

import java.awt.Dimension;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.UIManager;


public class TextDecorationComponent extends JXTaskPane {

	/**
	 *
	 */
	private static final long serialVersionUID = -6762824195458180737L;
	private BaseStyleSource spanStyleSource;
	private DrawDecorationComponent panel;

	public TextDecorationComponent(BaseStyleSource source) {
		spanStyleSource = source;
		initializeLayout();
	}

	private void initializeLayout() {
		setScrollOnExpand(true);
		getContentPane().setBackground(UIManager.getColor("control"));
		setTitle("Text Decoration");
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("200px"),},
			new RowSpec[] {
				FormFactory.MIN_ROWSPEC,}));

		panel = new DrawDecorationComponent(spanStyleSource);
		panel.setBackground(UIManager.getColor("control"));
		getContentPane().add(panel, "1, 1, left, top");

		this.setPreferredSize(new Dimension(220, 200));
	}

	public void initializeValue(BaseStyleSource spanStyle) {
		this.spanStyleSource = spanStyle;
		panel.initializeValue(spanStyle);
	}
}
