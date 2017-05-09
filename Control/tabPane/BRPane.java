package tabPane;

import java.awt.Dimension;

import javax.swing.JPanel;
import style.BRStyle;
import style.BRStyleSource;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;
import components.LineHeightComponent;

public class BRPane extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = -4128435153956485358L;
	private BRStyleSource brStyleSource;
	private LineHeightComponent lineHeightComponent;

	public BRPane(BRStyleSource source) {
		brStyleSource = source;
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("220px"),},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.MIN_ROWSPEC,}));
		lineHeightComponent = new LineHeightComponent(brStyleSource);
		add(lineHeightComponent, "2, 2, fill, top");
		this.setPreferredSize(new Dimension(270,100));
	}

	public void initializeValue(BRStyle brStyle) {
		brStyleSource.UpdateBaseStyleSource(brStyle);
		lineHeightComponent.initializeValue(brStyleSource);
	}
}
