package components;

import org.jdesktop.swingx.JXCollapsiblePane;
import org.jdesktop.swingx.JXTaskPane;

import parts.LDSpiner;
import parts.PanelColor;
import style.BaseStyleSource;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

import javax.swing.BoxLayout;
import javax.swing.SwingConstants;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

import enums.LDAttributes;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.SystemColor;

public class BorderComponent extends JXTaskPane {

	/**
	 *
	 */
	private static final long serialVersionUID = -2402881525092534832L;
	private BaseStyleSource baseStyleSource;
	private JCheckBox checkBoxDecoration;
	private LDSpiner borderWidthSpinner;
	private PanelColor panelBorderColor;
	private BorderFormComponents borderFormPane;
	private JXCollapsiblePane callapsPane;
	private FillBackgroundComponent fillBackPane;

	public BorderComponent(BaseStyleSource source) {
		this.baseStyleSource = source;
		initializeLayout();
	}

	private void initializeLayout() {
		setScrollOnExpand(true);
		getContentPane().setBackground(SystemColor.control);
		this.setPreferredSize(new Dimension(220, 300));
		setTitle("Border");
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.PREF_COLSPEC,},
			new RowSpec[] {
				FormFactory.PREF_ROWSPEC,
				RowSpec.decode("top:min"),
				RowSpec.decode("bottom:min"),
				FormFactory.UNRELATED_GAP_ROWSPEC,}));

		JPanel panel = new JPanel();
		getContentPane().add(panel, "1, 1, fill, top");
		panel.setLayout(new BorderLayout(0, 0));

		final JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2, "1, 2, fill, fill");
		panel_2.setLayout(new BorderLayout(0, 0));
		callapsPane = new JXCollapsiblePane(new BorderLayout(0, 0));
		panel_2.add(callapsPane);

		JPanel panel_3 = new JPanel();
		callapsPane.add(panel_3, BorderLayout.NORTH);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.Y_AXIS));

		JPanel panel_5 = new JPanel();
		panel_3.add(panel_5);
		panel_5.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.MIN_COLSPEC,
				FormFactory.UNRELATED_GAP_COLSPEC,
				FormFactory.PREF_COLSPEC,},
			new RowSpec[] {
				RowSpec.decode("24px"),}));

		JLabel lblBorderwidth = new JLabel("Border-Width");
		panel_5.add(lblBorderwidth, "1, 1, fill, center");

		borderWidthSpinner = new LDSpiner(0, 1, "px");
		borderWidthSpinner.getSpinner().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				baseStyleSource.setBorderWidth(borderWidthSpinner.getValue());
			}
		});
		panel_5.add(borderWidthSpinner, "3, 1, left, top");

		JPanel panel_1 = new JPanel();
		panel_3.add(panel_1);
				panel_1.setLayout(new FormLayout(new ColumnSpec[] {
						FormFactory.PREF_COLSPEC,
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						FormFactory.PREF_COLSPEC,
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						FormFactory.PREF_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.PREF_COLSPEC,},
					new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("20px"),
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("pref:grow"),}));

		JLabel label_2 = new JLabel("Boder-Color");
		label_2.setHorizontalAlignment(SwingConstants.TRAILING);
		panel_1.add(label_2, "1, 2, left, center");

		panelBorderColor = new PanelColor(baseStyleSource, LDAttributes.BorderColor);
		panel_1.add(panelBorderColor, "3, 2, left, top");

		borderFormPane = new BorderFormComponents(baseStyleSource);
		panel_1.add(borderFormPane, "1, 4, 7, 1, left, top");


		checkBoxDecoration = new JCheckBox(callapsPane.getActionMap().get(JXCollapsiblePane.TOGGLE_ACTION));
		checkBoxDecoration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkBoxDecoration.isSelected()) {
					baseStyleSource.isInitializing = true;
					borderWidthSpinner.setValue(baseStyleSource.getBorderWidth());
					panelBorderColor.setColor(baseStyleSource.getBorderColor());
					baseStyleSource.isInitializing = false;
				}
				baseStyleSource.setDrawBorder(checkBoxDecoration.isSelected());
			}
		});
		checkBoxDecoration.setText("Draw Border");
		panel.add(checkBoxDecoration, BorderLayout.NORTH);

		fillBackPane = new FillBackgroundComponent(baseStyleSource);
		getContentPane().add(fillBackPane, "1, 3, fill, top");

	}

	public void initializeValue(BaseStyleSource spanStyle) {
		this.baseStyleSource = spanStyle;
		this.baseStyleSource.isInitializing = true;

		checkBoxDecoration.setSelected(baseStyleSource.isDrawBorder());
		callapsPane.setCollapsed(!baseStyleSource.isDrawBorder());
		borderWidthSpinner.setValue(baseStyleSource.getBorderWidth());
		panelBorderColor.setColor(baseStyleSource.getBorderColor());
		borderFormPane.initializeValue(spanStyle);
		panelBorderColor.setSource(baseStyleSource);
		fillBackPane.initializeValue(spanStyle);
		this.baseStyleSource.isInitializing = false;
	}
}
