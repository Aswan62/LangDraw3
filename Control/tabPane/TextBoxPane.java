package tabPane;

import javax.swing.JPanel;

import style.TextBoxStyle;
import style.TextBoxStyleSource;

import bodyComponent.FontComponent;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import components.BorderComponent;
import components.PaddingComponent;


public class TextBoxPane extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = -5451071626785642002L;
	private FontComponent fontComponent;
	private TextBoxStyleSource textBoxStyleSorce;
	private PaddingComponent paddingComponent;
	private BorderComponent borderComponent;

	public TextBoxPane(TextBoxStyleSource sorce) {
		this.textBoxStyleSorce = sorce;
		fontComponent = new FontComponent(textBoxStyleSorce);

		paddingComponent = new PaddingComponent(textBoxStyleSorce);
		paddingComponent.setTitle("Padding");

		borderComponent = new BorderComponent(textBoxStyleSorce);

		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.UNRELATED_GAP_COLSPEC,
				FormFactory.PREF_COLSPEC,
				FormFactory.UNRELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.UNRELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,}));
		add(fontComponent, "2, 2, center, top");
		add(paddingComponent, "2, 4, fill, fill");
		add(borderComponent, "2, 6, fill, fill");
	}

	public void initializeValue(TextBoxStyle baseStyle) {
		textBoxStyleSorce.UpdateBaseStyleSource(baseStyle);
		fontComponent.initializeValue(textBoxStyleSorce);
		paddingComponent.initializeValue(textBoxStyleSorce);
		borderComponent.initializeValue(textBoxStyleSorce);
	}
}
