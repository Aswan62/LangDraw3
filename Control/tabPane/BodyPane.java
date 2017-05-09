package tabPane;

import javax.swing.JPanel;


import style.BaseStyle;
import style.BaseStyleSource;
import bodyComponent.BackgroundComponent;
import bodyComponent.FontComponent;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;



public class BodyPane extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = -706377119077754328L;

	private BaseStyleSource spanStyleSource;
	private FontComponent fontPane;
	private BackgroundComponent backPane;


	public BodyPane(BaseStyleSource source) {
		this.spanStyleSource = source;
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("220px"),
				FormFactory.UNRELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.UNRELATED_GAP_ROWSPEC,
				FormFactory.MIN_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.MIN_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC,}));

		fontPane = new FontComponent(source);
		add(fontPane, "2, 2, fill, top");

		backPane = new BackgroundComponent(source);
		add(backPane, "2, 4, fill, top");
	}


	public void initializeValue(BaseStyle spanStyle) {
		this.spanStyleSource.UpdateBaseStyleSource(spanStyle);
		fontPane.initializeValue(spanStyleSource);
		backPane.initializeValue(spanStyleSource);
	}
}
