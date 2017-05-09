package components;

import org.jdesktop.swingx.JXTaskPane;

import parts.ArrowSlider;
import parts.LDSpiner;
import parts.PanelColor;
import style.ArrowStyleSource;

import java.awt.SystemColor;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import enums.Enums.ArrowHeadType;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;

public class ArrowComponent extends JXTaskPane {

	/**
	 *
	 */
	private static final long serialVersionUID = -4427891927534407461L;
	private ArrowStyleSource arrowStyleSource;
	private PanelColor panelArrowColor;
	private LDSpiner spinnerLineWidth;
	private JComboBox comboBoxStartHeadType;
	private ArrowSlider sliderStartHeadSize;
	private ArrowSlider sliderEndHeadSize;
	private JComboBox comboBoxEndHeadType;
	private LDSpiner spinnerRadius;

	public ArrowComponent(ArrowStyleSource source) {
		this.arrowStyleSource = source;

		String[] types = {"None", "Fill", "Open"};

		getContentPane().setBackground(UIManager.getColor("control"));
		getContentPane().setForeground(SystemColor.control);
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.PREF_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.MIN_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.MIN_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.MIN_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,
				FormFactory.MIN_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC,}));

		JLabel lblNewLabel = new JLabel("Radius");
		getContentPane().add(lblNewLabel, "2, 2, left, default");

		spinnerRadius = new LDSpiner(0, 20, "px");
		spinnerRadius.getSpinner().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				arrowStyleSource.setRadius(spinnerRadius.getValue());
			}
		});
		getContentPane().add(spinnerRadius, "4, 2, fill, fill");

		JLabel lblArrowColor = new JLabel("Arrow Color");
		getContentPane().add(lblArrowColor, "2, 6, left, default");

		panelArrowColor = new PanelColor(arrowStyleSource);
		getContentPane().add(panelArrowColor, "4, 6, left, fill");

		JLabel lblLineWidth = new JLabel("Line width");
		getContentPane().add(lblLineWidth, "2, 4, left, default");

		spinnerLineWidth = new LDSpiner(0, 1, "px");
		spinnerLineWidth.getSpinner().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				arrowStyleSource.setWidth(spinnerLineWidth.getValue());
			}
		});
		getContentPane().add(spinnerLineWidth, "4, 4, fill, fill");

		JLabel lblStartheadtype = new JLabel("StartHead-Type");
		getContentPane().add(lblStartheadtype, "2, 8, left, default");

		comboBoxStartHeadType = new JComboBox(types);
		comboBoxStartHeadType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = comboBoxStartHeadType.getSelectedIndex();
				if(index == 0) arrowStyleSource.setStartHeadType(ArrowHeadType.NONE);
				if(index == 1) arrowStyleSource.setStartHeadType(ArrowHeadType.FILL);
				if(index == 2) arrowStyleSource.setStartHeadType(ArrowHeadType.OPEN);
			}
		});
		getContentPane().add(comboBoxStartHeadType, "4, 8, left, top");

		JLabel lblHeadSize = new JLabel("StartHead-Size");
		getContentPane().add(lblHeadSize, "2, 10");

		sliderStartHeadSize = new ArrowSlider();
		sliderStartHeadSize.getSlider().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				 arrowStyleSource.setStartHeadSize(sliderStartHeadSize.getValue());
			}
		});
		getContentPane().add(sliderStartHeadSize, "2, 11, 3, 1, left, fill");

		JLabel lblEndheadsize = new JLabel("EndHead-Size");
		getContentPane().add(lblEndheadsize, "2, 15, left, default");

		sliderEndHeadSize = new ArrowSlider();
		sliderEndHeadSize.getSlider().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				 arrowStyleSource.setEndHeadSize(sliderEndHeadSize.getValue());
			}
		});
		getContentPane().add(sliderEndHeadSize, "2, 16, 3, 1, left, fill");

		JLabel lblEndheadtype = new JLabel("EndHead-Type");
		getContentPane().add(lblEndheadtype, "2, 13, left, default");

		comboBoxEndHeadType = new JComboBox(types);
		comboBoxEndHeadType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = comboBoxEndHeadType.getSelectedIndex();
				if(index == 0) arrowStyleSource.setEndHeadType(ArrowHeadType.NONE);
				if(index == 1) arrowStyleSource.setEndHeadType(ArrowHeadType.FILL);
				if(index == 2) arrowStyleSource.setEndHeadType(ArrowHeadType.OPEN);
			}
		});
		getContentPane().add(comboBoxEndHeadType, "4, 13, left, top");
	}

	public void initializeValue(ArrowStyleSource arrowStyleSource) {
		this.arrowStyleSource = arrowStyleSource;
		arrowStyleSource.isInitializing = true;
		spinnerRadius.setValue(arrowStyleSource.radius);
		panelArrowColor.setColor(arrowStyleSource.arrowColor);
		panelArrowColor.setSource(arrowStyleSource);
		spinnerLineWidth.setValue(arrowStyleSource.width);
		sliderStartHeadSize.setValue(arrowStyleSource.startHeadSize);
		sliderEndHeadSize.setValue(arrowStyleSource.endHeadSize);

		if(arrowStyleSource.startHeadType == ArrowHeadType.NONE)
			comboBoxStartHeadType.setSelectedIndex(0);
		else if(arrowStyleSource.startHeadType == ArrowHeadType.FILL)
			comboBoxStartHeadType.setSelectedIndex(1);
		else if(arrowStyleSource.startHeadType == ArrowHeadType.OPEN)
			comboBoxStartHeadType.setSelectedIndex(2);

		if(arrowStyleSource.endHeadType == ArrowHeadType.NONE)
			comboBoxEndHeadType.setSelectedIndex(0);
		else if(arrowStyleSource.endHeadType == ArrowHeadType.FILL)
			comboBoxEndHeadType.setSelectedIndex(1);
		else if(arrowStyleSource.endHeadType == ArrowHeadType.OPEN)
			comboBoxEndHeadType.setSelectedIndex(2);

		arrowStyleSource.isInitializing = false;
	}

}
