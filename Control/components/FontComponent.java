package components;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.JXCollapsiblePane;
import org.jdesktop.swingx.JXCollapsiblePane.Direction;
import org.jdesktop.swingx.JXTaskPane;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import enums.LDAttributes;

import parts.ComboBoxFont;
import parts.ComboBoxStyle;
import parts.LDSpiner;
import parts.PanelColor;
import parts.ComboBoxVAlign;

import style.BaseStyleSource;
import java.awt.SystemColor;

public class FontComponent extends JXTaskPane {

	/**
	 *
	 */
	private static final long serialVersionUID = 7833736493928086708L;
	private BaseStyleSource spanStyleSource;
	private ComboBoxFont comboBoxFontFamily;
	private ComboBoxFont comboBoxFontFamily2Byte;
	private LDSpiner spinerFontSize;
	private ComboBoxStyle comboBoxFontStyle;
	private PanelColor panelFontColor;
	private JCheckBox checkBoxFontFamily;
	private JCheckBox checkBoxFontFamily2Byte;
	private JCheckBox checkBoxFontStyle;
	private JCheckBox checkBoxFontSize;
	private JCheckBox checkBoxFontColor;
	private JXCollapsiblePane collasColor;
	private JXCollapsiblePane collasStyle;
	private JXCollapsiblePane collasFont2Byte;
	private JXCollapsiblePane collasFont;
	private JXCollapsiblePane collasSize;
	private JXCollapsiblePane collasVAlign;
	private JPanel panelVAlign;
	private JPanel panelDummy6;
	private JCheckBox checkBoxVAlign;
	private ComboBoxVAlign comboBoxVAlign;


	public FontComponent(BaseStyleSource source) {
		setBackground(UIManager.getColor("control"));
		spanStyleSource = source;
		initializeLayout();
	}

	private void initializeLayout() {
		setScrollOnExpand(true);
		getContentPane().setBackground(SystemColor.control);
		setTitle("Font");
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("200px:grow"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,}));

		JLabel lblIfYouCheck = new JLabel("<html>If you check the box,<BR/>the each settings override.");
		getContentPane().add(lblIfYouCheck, "1, 1, left, top");

		JPanel panelFont = new JPanel();
		panelFont.setBackground(UIManager.getColor("control"));
		getContentPane().add(panelFont, "1, 3, fill, fill");
		panelFont.setLayout(new BorderLayout(0, 0));

		JPanel panelDummy1 = new JPanel();
		panelFont.add(panelDummy1, BorderLayout.WEST);


		collasFont = new JXCollapsiblePane();
		collasFont.setDirection(Direction.DOWN);
		collasFont.setCollapsed(true);
		collasFont.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		comboBoxFontFamily = new ComboBoxFont();
		comboBoxFontFamily.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				spanStyleSource.setFontFamily(comboBoxFontFamily.getFontFamily());
			}
		});
		collasFont.add(comboBoxFontFamily, BorderLayout.NORTH);
		panelDummy1.setLayout(new BorderLayout(0, 0));

		checkBoxFontFamily = new JCheckBox("Font-Family");
		checkBoxFontFamily.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				spanStyleSource.setOverrideFontFamily(checkBoxFontFamily.isSelected());
				spanStyleSource.setFontFamily(comboBoxFontFamily.getFontFamily());
				collasFont.setCollapsed(!checkBoxFontFamily.isSelected());
			}
		});
		panelDummy1.add(checkBoxFontFamily, BorderLayout.NORTH);

		panelDummy1.add(collasFont, BorderLayout.WEST);

		JPanel panelFont2Byte = new JPanel();
		panelFont2Byte.setBackground(UIManager.getColor("control"));
		getContentPane().add(panelFont2Byte, "1, 5, fill, fill");
		panelFont2Byte.setLayout(new BorderLayout(0, 0));
		panelFont2Byte.setLayout(new BorderLayout(0, 0));

		JPanel panelDummy2 = new JPanel();
		panelFont2Byte.add(panelDummy2, BorderLayout.WEST);
		panelDummy2.setLayout(new BorderLayout(0, 0));


		collasFont2Byte = new JXCollapsiblePane();
		collasFont2Byte.setDirection(Direction.DOWN);
		collasFont2Byte.setCollapsed(true);
		panelDummy2.add(collasFont2Byte, BorderLayout.WEST);
		collasFont2Byte.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		comboBoxFontFamily2Byte = new ComboBoxFont();
		comboBoxFontFamily2Byte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				spanStyleSource.setFontFamily2Byte(comboBoxFontFamily2Byte.getFontFamily());
			}
		});
		collasFont2Byte.add(comboBoxFontFamily2Byte);

		checkBoxFontFamily2Byte = new JCheckBox("Font-Family (for 2byte char.)");
		checkBoxFontFamily2Byte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				spanStyleSource.setOverrideFontFamily2Byte(checkBoxFontFamily2Byte.isSelected());
				spanStyleSource.setFontFamily2Byte(comboBoxFontFamily2Byte.getFontFamily());
				collasFont2Byte.setCollapsed(!checkBoxFontFamily2Byte.isSelected());
			}
		});
		panelDummy2.add(checkBoxFontFamily2Byte, BorderLayout.NORTH);


		JPanel panelFontStyle = new JPanel();
		panelFontStyle.setBackground(UIManager.getColor("control"));
		getContentPane().add(panelFontStyle, "1, 7, fill, fill");
		panelFontStyle.setLayout(new BorderLayout(0, 0));

		JPanel panelDummy3 = new JPanel();
		panelFontStyle.add(panelDummy3, BorderLayout.WEST);
		panelDummy3.setLayout(new BorderLayout(0, 0));


		collasStyle = new JXCollapsiblePane();
		collasStyle.setDirection(Direction.LEFT);
		collasStyle.setCollapsed(true);
		panelDummy3.add(collasStyle, BorderLayout.CENTER);

		comboBoxFontStyle = new ComboBoxStyle();
		comboBoxFontStyle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				spanStyleSource.setFontStyle(comboBoxFontStyle.getSelectedStyle());
			}
		});
		collasStyle.add(comboBoxFontStyle);
		collasStyle.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		checkBoxFontStyle = new JCheckBox("Font-Style");
		checkBoxFontStyle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				spanStyleSource.setOverrideFontStyle(checkBoxFontStyle.isSelected());
				spanStyleSource.setFontStyle(comboBoxFontStyle.getSelectedStyle());
				collasStyle.setCollapsed(!checkBoxFontStyle.isSelected());
			}
		});
		panelDummy3.add(checkBoxFontStyle, BorderLayout.WEST);


		JPanel panelFontSize = new JPanel();
		getContentPane().add(panelFontSize, "1, 9, fill, fill");panelFontSize.setLayout(new BorderLayout(0, 0));

		JPanel panelDummy4 = new JPanel();
		panelFontSize.add(panelDummy4, BorderLayout.WEST);
		panelDummy4.setLayout(new BorderLayout(0, 0));

		collasSize = new JXCollapsiblePane(Direction.LEFT);
		collasSize.setCollapsed(true);
		collasSize.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panelDummy4.add(collasSize, BorderLayout.CENTER);

		spinerFontSize = new LDSpiner(0.1f, 30f ,"pt");
		spinerFontSize.getSpinner().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				spanStyleSource.setFontSize(spinerFontSize.getValue());
			}
		});
		collasSize.add(spinerFontSize, BorderLayout.NORTH);

		checkBoxFontSize = new JCheckBox("Font-Size");
		checkBoxFontSize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				spanStyleSource.setOverrideFontSize(checkBoxFontSize.isSelected());
				spanStyleSource.setFontSize(spinerFontSize.getValue());
				collasSize.setCollapsed(!checkBoxFontSize.isSelected());
			}
		});
		panelDummy4.add(checkBoxFontSize, BorderLayout.WEST);


		JPanel panelColor = new JPanel();
		getContentPane().add(panelColor, "1, 11, fill, fill");
		panelColor.setLayout(new BorderLayout(0, 0));

		JPanel panelDummy5 = new JPanel();
		panelColor.add(panelDummy5, BorderLayout.WEST);
		panelDummy5.setLayout(new BorderLayout(0, 0));


		collasColor = new JXCollapsiblePane(Direction.LEFT);
		collasColor.setCollapsed(true);
		collasColor.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panelDummy5.add(collasColor, BorderLayout.CENTER);

		panelFontColor = new PanelColor(spanStyleSource, LDAttributes.FontColor);
		collasColor.add(panelFontColor);

		checkBoxFontColor = new JCheckBox("Font-Color");
		checkBoxFontColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(checkBoxFontColor.isSelected()) panelFontColor.setColor(spanStyleSource.getFontColor());
				spanStyleSource.setOverrideFontColor(checkBoxFontColor.isSelected());
				spanStyleSource.setFontColor(panelFontColor.getColor());
				collasColor.setCollapsed(!checkBoxFontColor.isSelected());
			}
		});
		panelDummy5.add(checkBoxFontColor, BorderLayout.WEST);

		panelVAlign = new JPanel();
		getContentPane().add(panelVAlign, "1, 13, fill, fill");
		panelVAlign.setLayout(new BorderLayout(0, 0));

		panelDummy6 = new JPanel();
		panelVAlign.add(panelDummy6, BorderLayout.WEST);
		panelDummy6.setLayout(new BorderLayout(0, 0));

		checkBoxVAlign = new JCheckBox("Vertical align");
		checkBoxVAlign.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				spanStyleSource.setUseVerticalAlign(checkBoxVAlign.isSelected());
				spanStyleSource.setVerticalAlign(comboBoxVAlign.getSelectedAlign());
				collasVAlign.setCollapsed(!checkBoxVAlign.isSelected());
			}
		});
		panelDummy6.add(checkBoxVAlign, BorderLayout.WEST);

		collasVAlign = new JXCollapsiblePane(Direction.LEFT);
		collasVAlign.setCollapsed(true);
		panelDummy6.add(collasVAlign);

		comboBoxVAlign = new ComboBoxVAlign();
		comboBoxVAlign.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				spanStyleSource.setVerticalAlign(comboBoxVAlign.getSelectedAlign());
			}
		});
		collasVAlign.add(comboBoxVAlign, BorderLayout.NORTH);
	}

	public void initializeValue(BaseStyleSource styleSource) {
		this.spanStyleSource = styleSource;
		spanStyleSource.isInitializing = true;
		if(spanStyleSource.overrideFontFamily) {
			comboBoxFontFamily.setFontFamily(spanStyleSource.fontFamily);
			checkBoxFontFamily.setSelected(true);
		} else {
			checkBoxFontFamily.setSelected(false);
		}

		if(spanStyleSource.overrideFontFamily2Byte) {
			comboBoxFontFamily2Byte.setFontFamily(spanStyleSource.fontFamily2Byte);
			checkBoxFontFamily2Byte.setSelected(true);
		} else {
			checkBoxFontFamily2Byte.setSelected(false);
		}
		if(spanStyleSource.overrideFontSize) {
			spinerFontSize.setValue(spanStyleSource.fontSize);
			checkBoxFontSize.setSelected(true);
		} else {
			checkBoxFontSize.setSelected(false);
		}

		if(spanStyleSource.overrideFontStyle) {
			comboBoxFontStyle.setSelectedStyle(spanStyleSource.fontStyle);
			checkBoxFontStyle.setSelected(true);
		} else {
			checkBoxFontStyle.setSelected(false);
		}

		if(spanStyleSource.overrideFontColor) {
			panelFontColor.setColor(spanStyleSource.fontColor);
			checkBoxFontColor.setSelected(true);
		} else {
			checkBoxFontColor.setSelected(false);
		}

		if(spanStyleSource.isUseVertivalAlign()) {
			comboBoxVAlign.setSelectedAlign(spanStyleSource.getSpanVertivalAlign());
			checkBoxVAlign.setSelected(true);
		} else {
			checkBoxVAlign.setSelected(false);
		}

		collasFont.setCollapsed(!checkBoxFontFamily.isSelected());
		collasFont2Byte.setCollapsed(!checkBoxFontFamily2Byte.isSelected());
		collasSize.setCollapsed(!checkBoxFontSize.isSelected());
		collasStyle.setCollapsed(!checkBoxFontStyle.isSelected());
		collasColor.setCollapsed(!checkBoxFontColor.isSelected());
		collasVAlign.setCollapsed(!checkBoxVAlign.isSelected());

		panelFontColor.setSource(spanStyleSource);

		spanStyleSource.isInitializing = false;
	}
}
