package parts;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JToggleButton;

import org.jdesktop.swingx.JXCollapsiblePane;

import style.TextBoxStyleSource;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import enums.Padding;

import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class TextBoxPadding extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 830582859485860262L;
	private JXCollapsiblePane collasibleAllPane;
	private JXCollapsiblePane collasibleAdvancePane;
	private JToggleButton tglbtnAdvanced;
	private LDSpiner spinnerTop;
	private LDSpiner spinnerRight;
	private LDSpiner spinnerBottom;
	private LDSpiner spinnerLeft;
	private LDSpiner spinnerAll;
	private TextBoxStyleSource textBoxStyleSorce;

	public TextBoxPadding(TextBoxStyleSource source) {
		this.textBoxStyleSorce = source;
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.MIN_COLSPEC,},
			new RowSpec[] {
				FormFactory.PREF_ROWSPEC,
				FormFactory.MIN_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));


		collasibleAllPane = new JXCollapsiblePane();
		collasibleAllPane.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("55px"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.PREF_COLSPEC,},
			new RowSpec[] {
				RowSpec.decode("24px"),}));

		JLabel lblAll = new JLabel("All: ");
		collasibleAllPane.getContentPane().add(lblAll, "1, 1, right, center");

		spinnerAll = new LDSpiner(0, 5, "px");
		spinnerAll.getSpinner().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				spinnerTop.setValue(spinnerAll.getValue());
				spinnerLeft.setValue(spinnerAll.getValue());
				spinnerBottom.setValue(spinnerAll.getValue());
				spinnerRight.setValue(spinnerAll.getValue());
			}
		});
		collasibleAllPane.getContentPane().add(spinnerAll, "3, 1, left, top");


		add(collasibleAllPane, "1, 1, fill, fill");

		collasibleAdvancePane = new JXCollapsiblePane();
		collasibleAdvancePane.setCollapsed(true);
		add(collasibleAdvancePane, "1, 2, fill, fill");
		collasibleAdvancePane.getContentPane().setLayout(new BoxLayout(collasibleAdvancePane.getContentPane(), BoxLayout.Y_AXIS));

		JPanel topPanel = new JPanel();
		topPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		topPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		collasibleAdvancePane.getContentPane().add(topPanel);
		topPanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("55px"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.MIN_COLSPEC,},
			new RowSpec[] {
				RowSpec.decode("24px"),}));

		JLabel lblTop = new JLabel("Top: ");
		topPanel.add(lblTop, "1, 1, right, center");

		spinnerTop = new LDSpiner(0,5,"px");
		spinnerTop.getSpinner().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				textBoxStyleSorce.setPaddingTop(spinnerTop.getValue());
			}
		});
		topPanel.add(spinnerTop, "3, 1, left, top");

		JPanel rightPanel = new JPanel();
		rightPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		rightPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		collasibleAdvancePane.getContentPane().add(rightPanel);
		rightPanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("55px"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.MIN_COLSPEC,},
			new RowSpec[] {
				RowSpec.decode("24px"),}));

		JLabel lblRight = new JLabel("Right: ");
		rightPanel.add(lblRight, "1, 1, right, center");

		spinnerRight = new LDSpiner(0,5,"px");
		spinnerRight.getSpinner().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				textBoxStyleSorce.setPaddingRight(spinnerRight.getValue());
			}
		});
		rightPanel.add(spinnerRight, "3, 1, left, top");

		JPanel bottomPanel = new JPanel();
		bottomPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		bottomPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		collasibleAdvancePane.getContentPane().add(bottomPanel);
		bottomPanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("55px"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.MIN_COLSPEC,},
			new RowSpec[] {
				RowSpec.decode("24px"),}));

		JLabel lblBottom = new JLabel("Bottom: ");
		bottomPanel.add(lblBottom, "1, 1, right, center");

		spinnerBottom = new LDSpiner(0,5,"px");
		spinnerBottom.getSpinner().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				textBoxStyleSorce.setPaddingBottom(spinnerBottom.getValue());
			}
		});
		bottomPanel.add(spinnerBottom, "3, 1, left, top");

		JPanel leftPanel = new JPanel();
		leftPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		collasibleAdvancePane.getContentPane().add(leftPanel);
		leftPanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("55px"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.MIN_COLSPEC,},
			new RowSpec[] {
				FormFactory.PREF_ROWSPEC,}));

		JLabel lblLeft = new JLabel("Left: ");
		leftPanel.add(lblLeft, "1, 1, right, center");

		spinnerLeft = new LDSpiner(0,5,"px");
		spinnerLeft.getSpinner().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				textBoxStyleSorce.setPaddingLeft(spinnerLeft.getValue());
			}
		});
		leftPanel.add(spinnerLeft, "3, 1, left, top");

		tglbtnAdvanced = new JToggleButton("Advanced");
		add(tglbtnAdvanced, "1, 4, center, center");
		tglbtnAdvanced.setAlignmentX(Component.CENTER_ALIGNMENT);
		tglbtnAdvanced.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				collasibleAllPane.setCollapsed(tglbtnAdvanced.isSelected());
				collasibleAdvancePane.setCollapsed(!tglbtnAdvanced.isSelected());
			}
		});
		tglbtnAdvanced.setMargin(new Insets(2, 10, 2, 10));
	}

	public void setPadding(TextBoxStyleSource source) {
		this.textBoxStyleSorce = source;
		Padding padding = source.getPadding();
		if(padding != null) {
			if(padding.isAllValueSame()) {
				spinnerAll.setValue(padding.top);
				tglbtnAdvanced.setSelected(false);
			}
			else {
				tglbtnAdvanced.setSelected(true);
			}
			collasibleAllPane.setCollapsed(tglbtnAdvanced.isSelected());
			collasibleAdvancePane.setCollapsed(!tglbtnAdvanced.isSelected());
			spinnerTop.setValue(padding.top);
			spinnerLeft.setValue(padding.left);
			spinnerBottom.setValue(padding.bottom);
			spinnerRight.setValue(padding.right);
		}
	}

}
