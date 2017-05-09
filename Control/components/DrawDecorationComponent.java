package components;

import org.jdesktop.swingx.JXCollapsiblePane;
import org.jdesktop.swingx.JXPanel;


import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Dimension;
import org.jdesktop.swingx.JXCollapsiblePane.Direction;

import parts.ComboBoxDecoration;
import parts.PanelColor;
import style.BaseStyleSource;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

import enums.LDAttributes;
import enums.Enums.TextDecoration;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;

public class DrawDecorationComponent extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = 3289529872857490623L;
	private BaseStyleSource spanStyleSource;
	private JCheckBox checkBoxDrawDecoration;
	private ComboBoxDecoration comboBoxTextDecoration;
	private PanelColor panelColor;
	private JXCollapsiblePane collapsiblePane;

	public DrawDecorationComponent(BaseStyleSource source) {
		this.spanStyleSource = source;
		setAlignmentY(Component.TOP_ALIGNMENT);
		setAlignmentX(Component.LEFT_ALIGNMENT);
		initializeLayout();
	}

	private void initializeLayout() {
		setLayout(new BorderLayout(0, 0));

		setPreferredSize(new Dimension(187, 160));

		JPanel panel = new JPanel();
		panel.setBackground(UIManager.getColor("control"));
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		collapsiblePane = new JXCollapsiblePane(Direction.DOWN);
		collapsiblePane.setCollapsed(true);
		collapsiblePane.setBackground(UIManager.getColor("control"));
		panel.add(collapsiblePane);
		collapsiblePane.setLayout(new BorderLayout(0,0));


		JXPanel tdp = new JXPanel();

		tdp.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.PREF_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("max(15dlu;pref)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(31dlu;pref)"),},
			new RowSpec[] {
				FormFactory.PREF_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,}));

			JLabel label = new JLabel("Text-Decoration");
			label.setHorizontalAlignment(SwingConstants.LEFT);
			tdp.add(label, "1, 1, fill, center");

		comboBoxTextDecoration = new ComboBoxDecoration();
		comboBoxTextDecoration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				spanStyleSource.setTextDecoration(comboBoxTextDecoration.getSelectedDecoration());
			}
		});
		comboBoxTextDecoration.setPreferredSize(new Dimension(80, 19));
		tdp.add(comboBoxTextDecoration, "3, 1, 3, 1, left, center");

		JLabel label_1 = new JLabel("Text-Decoration-Color");
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		tdp.add(label_1, "1, 3, 3, 1, left, center");

		panelColor = new PanelColor(spanStyleSource, LDAttributes.TextDecorationColor);
		tdp.add(panelColor, "5, 3, left, center");

		checkBoxDrawDecoration = new JCheckBox(collapsiblePane.getActionMap().get(JXCollapsiblePane.TOGGLE_ACTION));
		checkBoxDrawDecoration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				spanStyleSource.setDrawTextDecoration(checkBoxDrawDecoration.isSelected());
			}
		});
		checkBoxDrawDecoration.setText("Draw text decoration");
		panel.add(checkBoxDrawDecoration, BorderLayout.NORTH);
		collapsiblePane.add(tdp, BorderLayout.NORTH);
	}

	public void initializeValue(BaseStyleSource spanStyle) {
		this.spanStyleSource = spanStyle;
		spanStyleSource.isInitializing = true;
		if(spanStyleSource.isDrawTextDecoration()) {
			checkBoxDrawDecoration.setSelected(true);
			collapsiblePane.setCollapsed(false);
			if(spanStyleSource.getDecoration() == TextDecoration.UNDERLINE)
				comboBoxTextDecoration.setSelectedIndex(0);
			else if(spanStyleSource.getDecoration() == TextDecoration.STRIKE)
				comboBoxTextDecoration.setSelectedIndex(1);
			else if(spanStyleSource.getDecoration() == TextDecoration.OVERLINE)
				comboBoxTextDecoration.setSelectedIndex(2);
			panelColor.setColor(spanStyle.decorationColor);
		} else {
			checkBoxDrawDecoration.setSelected(false);
			collapsiblePane.setCollapsed(true);
		}

		panelColor.setSource(spanStyleSource);
		spanStyleSource.isInitializing = false;
	}
}
