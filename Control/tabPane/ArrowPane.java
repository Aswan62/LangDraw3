package tabPane;

import javax.swing.JPanel;

import style.ArrowStyle;
import style.ArrowStyleSource;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import components.ArrowComponent;

public class ArrowPane extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 6861730713998433409L;
	private ArrowStyleSource arrowStyleSource;
	private ArrowComponent arrowComponent;


	public ArrowPane(ArrowStyleSource source) {
		arrowStyleSource = source;
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.UNRELATED_GAP_COLSPEC,
				FormFactory.MIN_COLSPEC,
				FormFactory.UNRELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.MIN_ROWSPEC,}));

		arrowComponent = new ArrowComponent(source);
		arrowComponent.setTitle("Arrow");
		add(arrowComponent, "2, 2, fill, top");

	}

	public void initializeValue(ArrowStyle arrowStyle) {
		arrowStyleSource.UpdateStyleSource(arrowStyle);
		arrowComponent.initializeValue(arrowStyleSource);
	}

}
