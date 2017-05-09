package bodyComponent;

import org.jdesktop.swingx.JXTaskPane;

import parts.PanelColor;
import style.BaseStyleSource;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

import enums.LDAttributes;

import javax.swing.UIManager;

public class BackgroundComponent extends JXTaskPane {
	/**
	 *
	 */
	private static final long serialVersionUID = 975412419571948720L;
	private BaseStyleSource spanStyleSource;
	private PanelColor panelBackColor;

	public BackgroundComponent(BaseStyleSource source) {
		spanStyleSource = source;
		getContentPane().setBackground(UIManager.getColor("control"));
		setTitle("Background");

		JPanel panel = new JPanel();
		panel.setBackground(UIManager.getColor("control"));
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.PREF_COLSPEC,
				FormFactory.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(51dlu;pref):grow"),},
			new RowSpec[] {
				FormFactory.PREF_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,}));

		JLabel lblThisSettingApplies = new JLabel("<html>Change the background color<BR/>of the diagram.");
		panel.add(lblThisSettingApplies, "1, 1, 3, 1, fill, top");

		JLabel label_1 = new JLabel("Background-Color");
		panel.add(label_1, "1, 3");

		panelBackColor = new PanelColor(spanStyleSource, LDAttributes.BackgroundColor);
		panelBackColor.setBackground(UIManager.getColor("control"));
		panel.add(panelBackColor, "3, 3, left, top");
	}

	public void initializeValue(BaseStyleSource styleSource) {
		this.spanStyleSource = styleSource;
		spanStyleSource.isInitializing = true;
		panelBackColor.setColor(spanStyleSource.getBackgroundColor());
		panelBackColor.setSource(spanStyleSource);
		spanStyleSource.isInitializing = false;
	}

}
