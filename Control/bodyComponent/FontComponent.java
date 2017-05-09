package bodyComponent;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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

import style.BaseStyleSource;
import javax.swing.UIManager;

public class FontComponent extends JXTaskPane{

	/**
	 *
	 */
	private static final long serialVersionUID = 1554441833830629151L;
	private BaseStyleSource spanStyleSource;
	private ComboBoxFont comboBoxFontFamily;
	private ComboBoxFont comboBoxFontFamily2Byte;
	private LDSpiner spinerFontSize;
	private ComboBoxStyle comboBoxFontStyle;
	private PanelColor panelFontColor;
	private JLabel checkBoxFontFamily;
	private JLabel checkBoxFontFamily2Byte;
	private JLabel checkBoxFontStyle;
	private JLabel checkBoxFontSize;
	private JLabel checkBoxFontColor;

	public FontComponent(BaseStyleSource source) {
		spanStyleSource = source;
		initializeLayout();
	}

	private void initializeLayout() {
		setScrollOnExpand(true);
		getContentPane().setBackground(UIManager.getColor("control"));
		setTitle("Font");
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.PREF_COLSPEC,},
			new RowSpec[] {
				FormFactory.PREF_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,}));

		JPanel panelFont = new JPanel();
		getContentPane().add(panelFont, "1, 1, fill, fill");
		panelFont.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

		JPanel panelDummy1 = new JPanel();
		panelFont.add(panelDummy1);
		panelDummy1.setLayout(new BorderLayout(0, 0));

		checkBoxFontFamily = new JLabel("Font-Family");

		panelDummy1.add(checkBoxFontFamily, BorderLayout.NORTH);

		comboBoxFontFamily = new ComboBoxFont();
		panelDummy1.add(comboBoxFontFamily, BorderLayout.SOUTH);
		comboBoxFontFamily.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				spanStyleSource.setFontFamily(comboBoxFontFamily.getFontFamily());
			}
		});

		JPanel panelFont2Byte = new JPanel();
		getContentPane().add(panelFont2Byte, "1, 3, fill, fill");
		panelFont2Byte.setLayout(new BorderLayout(0, 0));
		panelFont2Byte.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

		JPanel panelDummy2 = new JPanel();
		panelFont2Byte.add(panelDummy2);
		panelDummy2.setLayout(new BorderLayout(0, 0));

		checkBoxFontFamily2Byte = new JLabel("Font-Family (for 2byte char.)");

		panelDummy2.add(checkBoxFontFamily2Byte, BorderLayout.NORTH);

		comboBoxFontFamily2Byte = new ComboBoxFont();
		panelDummy2.add(comboBoxFontFamily2Byte, BorderLayout.SOUTH);
		comboBoxFontFamily2Byte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				spanStyleSource.setFontFamily2Byte(comboBoxFontFamily2Byte.getFontFamily());
			}
		});


		JPanel panelFontStyle = new JPanel();
		getContentPane().add(panelFontStyle, "1, 5, fill, fill");
		panelFontStyle.setLayout(new BorderLayout(0, 0));

		JPanel panelDummy3 = new JPanel();
		panelFontStyle.add(panelDummy3, BorderLayout.WEST);
		panelDummy3.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		checkBoxFontStyle = new JLabel("Font-Style");

		panelDummy3.add(checkBoxFontStyle);

		comboBoxFontStyle = new ComboBoxStyle();
		panelDummy3.add(comboBoxFontStyle);
		comboBoxFontStyle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				spanStyleSource.setFontStyle(comboBoxFontStyle.getSelectedStyle());
			}
		});


		JPanel panel = new JPanel();
		getContentPane().add(panel, "1, 7, fill, fill");panel.setLayout(new BorderLayout(0, 0));

		JPanel panelDummy4 = new JPanel();
		panel.add(panelDummy4, BorderLayout.WEST);
		panelDummy4.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

		checkBoxFontSize = new JLabel("Font-Size");

		panelDummy4.add(checkBoxFontSize);

		spinerFontSize = new LDSpiner(0.1f, 30f ,"pt");
		panelDummy4.add(spinerFontSize);
		spinerFontSize.getSpinner().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				spanStyleSource.setFontSize(spinerFontSize.getValue());
			}
		});


		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, "1, 9, fill, fill");
		panel_1.setLayout(new BorderLayout(0, 0));

		JPanel panelDummy5 = new JPanel();
		panel_1.add(panelDummy5, BorderLayout.WEST);
		panelDummy5.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

		checkBoxFontColor = new JLabel("Font-Color");

		panelDummy5.add(checkBoxFontColor);

		panelFontColor = new PanelColor(spanStyleSource, LDAttributes.FontColor);
		panelDummy5.add(panelFontColor);
	}

	public void initializeValue(BaseStyleSource styleSource) {
		this.spanStyleSource = styleSource;
		spanStyleSource.isInitializing = true;
		comboBoxFontFamily.setFontFamily(spanStyleSource.fontFamily);
		comboBoxFontFamily2Byte.setFontFamily(spanStyleSource.fontFamily2Byte);
		spinerFontSize.setValue(spanStyleSource.fontSize);
		comboBoxFontStyle.setSelectedStyle(spanStyleSource.fontStyle);
		panelFontColor.setColor(spanStyleSource.fontColor);
		panelFontColor.setSource(spanStyleSource);
		spanStyleSource.isInitializing = false;
	}
}
