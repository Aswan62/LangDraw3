package components;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

import org.jdesktop.swingx.JXCollapsiblePane;

import parts.PanelColor;
import style.BaseStyleSource;

import java.awt.BorderLayout;
import java.awt.Dimension;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

import enums.LDAttributes;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;

public class FillBackgroundComponent  extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = 4074462556298890976L;
	private BaseStyleSource spanStyleSource;
	private JCheckBox checkBoxFillBackground;
	private PanelColor panelColor;
	private JXCollapsiblePane collasPanel;

	public FillBackgroundComponent(BaseStyleSource source) {
		setBackground(UIManager.getColor("control"));
		spanStyleSource = source;
		initializeLayout();
	}

	private void initializeLayout() {
		setPreferredSize(new Dimension(200, 130));
		setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(UIManager.getColor("control"));
		add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		collasPanel = new JXCollapsiblePane();
		collasPanel.getContentPane().setBackground(UIManager.getColor("control"));
		collasPanel.setCollapsed(true);
		panel_1.add(collasPanel);

		JPanel panel = new JPanel();
		panel.setBackground(UIManager.getColor("control"));
		collasPanel.add(panel, BorderLayout.NORTH);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.PREF_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.PREF_COLSPEC,},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,
				FormFactory.PREF_ROWSPEC,}));

		JLabel label = new JLabel("Background-Color");
		label.setHorizontalAlignment(SwingConstants.TRAILING);
		panel.add(label, "1, 2, left, center");

		panelColor = new PanelColor(spanStyleSource, LDAttributes.BackgroundColor);
		panelColor.setBackground(UIManager.getColor("control"));
		panel.add(panelColor, "3, 2, left, top");


		checkBoxFillBackground = new JCheckBox(collasPanel.getActionMap().get(JXCollapsiblePane.TOGGLE_ACTION));
		checkBoxFillBackground.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				spanStyleSource.setFillBackground(checkBoxFillBackground.isSelected());
			}
		});
		checkBoxFillBackground.setText("Fill background");

		panel_1.add(checkBoxFillBackground, BorderLayout.NORTH);
	}


	public void initializeValue(BaseStyleSource spanStyle) {
		spanStyleSource = spanStyle;
		if(spanStyleSource.borderSetting != null) {
			checkBoxFillBackground.setSelected(spanStyleSource.isFillBackground());
			collasPanel.setCollapsed(!spanStyleSource.isFillBackground());
			panelColor.setColor(spanStyleSource.getBackgroundColor());
		} else {
			checkBoxFillBackground.setSelected(false);
			collasPanel.setCollapsed(true);
		}
		panelColor.setSource(spanStyleSource);
	}
}
