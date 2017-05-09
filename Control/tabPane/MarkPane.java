package tabPane;

import bodyComponent.FontComponent;
import style.MarkStyle;
import style.MarkStyleSource;

import javax.swing.JPanel;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import components.BorderComponent;
import components.PositionComponent;
import components.TextDecorationComponent;

public class MarkPane extends JPanel{

	/**
	 *
	 */
	private static final long serialVersionUID = -3012337253033076429L;
	private FontComponent fontComponent;
	private TextDecorationComponent textDecorationComponent;
	private BorderComponent borderComponent;
	private MarkStyleSource markStyleSource;
	private PositionComponent positionPane;

	public MarkPane(MarkStyleSource source) {
		markStyleSource = source;

		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.UNRELATED_GAP_COLSPEC,
				FormFactory.PREF_COLSPEC,},
			new RowSpec[] {
				FormFactory.UNRELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC,
				FormFactory.MIN_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC,
				FormFactory.MIN_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC,}));

		positionPane = new PositionComponent(markStyleSource);
		add(positionPane, "2, 2, fill, fill");
		fontComponent = new FontComponent(markStyleSource);
		add(fontComponent, "2, 4, fill, top");

		textDecorationComponent = new TextDecorationComponent(markStyleSource);
		add(textDecorationComponent, "2, 6, fill, fill");

		borderComponent = new BorderComponent(markStyleSource);
		add(borderComponent, "2, 8, fill, top");
	}

	public void initializeValue(MarkStyle markStyle) {
		markStyleSource.UpdateBaseStyleSource(markStyle);
		positionPane.initilizeValue(markStyleSource);
		fontComponent.initializeValue(markStyleSource);
		textDecorationComponent.initializeValue(markStyleSource);
		borderComponent.initializeValue(markStyleSource);
	}
}
