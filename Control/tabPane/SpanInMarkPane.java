package tabPane;

import javax.swing.JPanel;


import style.BaseStyle;
import style.BaseStyleSource;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;
import components.BorderComponent;
import components.FontComponent;
import components.TextDecorationComponent;


final public class SpanInMarkPane extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = -567026694855913831L;
	public FontComponent fontComponent;
	public BorderComponent borderComponent;
	public TextDecorationComponent textDecorationComponent;
	private BaseStyleSource spanStyleSource;

	public SpanInMarkPane(BaseStyleSource source) {
		spanStyleSource = source;
		initializeStyle();
	}

	private void initializeStyle() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.UNRELATED_GAP_COLSPEC,
				FormFactory.MIN_COLSPEC,},
			new RowSpec[] {
				FormFactory.UNRELATED_GAP_ROWSPEC,
				FormFactory.MIN_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC,
				FormFactory.MIN_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC,
				FormFactory.MIN_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC,}));
		fontComponent = new FontComponent(spanStyleSource);
		add(fontComponent, "2, 2, fill, top");

		textDecorationComponent = new TextDecorationComponent(spanStyleSource);
		add(textDecorationComponent, "2, 4, fill, fill");

		borderComponent = new BorderComponent(spanStyleSource);
		add(borderComponent, "2, 6, fill, top");
	}

	public void initializeValue(BaseStyle spanStyle) {
		spanStyleSource.UpdateBaseStyleSource(spanStyle);
		fontComponent.initializeValue(spanStyleSource);
		borderComponent.initializeValue(spanStyleSource);
		textDecorationComponent.initializeValue(spanStyleSource);
	}
}
