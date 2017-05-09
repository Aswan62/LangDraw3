package components;

import org.jdesktop.swingx.JXTaskPane;

import parts.LDSpiner;
import style.MarkStyleSource;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JToggleButton;
import javax.swing.JLabel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import enums.Enums.HorizontalAlign;
import enums.Enums.VerticalAlign;

import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;

public class PositionComponent extends JXTaskPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6577679077794613139L;
	private MarkStyleSource markStyleSource;
	private JToggleButton tglbtnTopleft;
	private JToggleButton tglbtnTopcenter;
	private JToggleButton tglbtnTopright;
	private JToggleButton tglbtnBottomleft;
	private JToggleButton tglbtnBottomcenter;
	private JToggleButton tglbtnBottomright;
	private LDSpiner marginSpiner;


	public PositionComponent(MarkStyleSource source) {
		this.markStyleSource = source;
		setTitle("Position");
		setScrollOnExpand(true);
		getContentPane().setBackground(UIManager.getColor("control"));

		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BorderLayout(0, 0));

		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.MIN_COLSPEC,
				FormFactory.UNRELATED_GAP_COLSPEC,
				FormFactory.PREF_COLSPEC,},
			new RowSpec[] {
				FormFactory.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("24px"),}));

		JLabel lblMargin = new JLabel("Margin");
		panel_2.add(lblMargin, "1, 2, fill, fill");

		marginSpiner = new LDSpiner(-5000,5,"px");
		marginSpiner.getSpinner().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				markStyleSource.setMargin(marginSpiner.getValue());
			}
		});
		panel_2.add(marginSpiner, "3, 2, fill, top");

		JPanel panel = new JPanel();
		panel_1.add(panel);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.PREF_COLSPEC,
				FormFactory.PREF_COLSPEC,
				FormFactory.PREF_COLSPEC,},
			new RowSpec[] {
				FormFactory.PREF_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,}));

		ButtonGroup buttonGroup = new ButtonGroup();
		tglbtnTopleft = new JToggleButton("<html><center>Top<BR>Left");
		tglbtnTopleft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chengePosition(HorizontalAlign.LEFT, VerticalAlign.SUPER);
			}
		});
		tglbtnTopleft.setMargin(new Insets(2, 2, 2, 2));
		tglbtnTopleft.setHorizontalTextPosition(SwingConstants.CENTER);
		panel.add(tglbtnTopleft, "1, 1, fill, fill");

		tglbtnTopcenter = new JToggleButton("<html><center>Top<br>Center");
		tglbtnTopcenter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chengePosition(HorizontalAlign.CENTER, VerticalAlign.SUPER);
			}
		});
		tglbtnTopcenter.setMargin(new Insets(2, 2, 2, 2));
		tglbtnTopcenter.setHorizontalTextPosition(SwingConstants.CENTER);
		panel.add(tglbtnTopcenter, "2, 1, fill, fill");

		tglbtnTopright = new JToggleButton("<html><center>Top<BR>Right");
		tglbtnTopright.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chengePosition(HorizontalAlign.RIGHT, VerticalAlign.SUPER);
			}
		});
		tglbtnTopright.setMargin(new Insets(2, 2, 2, 2));
		panel.add(tglbtnTopright, "3, 1, fill, fill");

		JLabel lblNewLabel = new JLabel("The marked-up text");
		panel.add(lblNewLabel, "1, 3, 3, 1, center, center");

		tglbtnBottomleft = new JToggleButton("<html><center>Bottom<br>Left");
		tglbtnBottomleft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chengePosition(HorizontalAlign.LEFT, VerticalAlign.SUB);
			}
		});
		tglbtnBottomleft.setMargin(new Insets(2, 2, 2, 2));
		panel.add(tglbtnBottomleft, "1, 5, fill, fill");

		tglbtnBottomcenter = new JToggleButton("<html><center>Bottom<br>Center");
		tglbtnBottomcenter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chengePosition(HorizontalAlign.CENTER, VerticalAlign.SUB);
			}
		});
		tglbtnBottomcenter.setMargin(new Insets(2, 2, 2, 2));
		panel.add(tglbtnBottomcenter, "2, 5, fill, fill");

		tglbtnBottomright = new JToggleButton("<html><center>Bottom<br>Right");
		tglbtnBottomright.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chengePosition(HorizontalAlign.RIGHT, VerticalAlign.SUB);
			}
		});
		tglbtnBottomright.setMargin(new Insets(2, 2, 2, 2));
		panel.add(tglbtnBottomright, "3, 5, fill, fill");

		buttonGroup.add(tglbtnTopleft);
		buttonGroup.add(tglbtnTopcenter);
		buttonGroup.add(tglbtnTopright);
		buttonGroup.add(tglbtnBottomleft);
		buttonGroup.add(tglbtnBottomcenter);
		buttonGroup.add(tglbtnBottomright);
	}

	public void initilizeValue(MarkStyleSource source) {
		this.markStyleSource = source;
		markStyleSource.isInitializing = true;
		switch(markStyleSource.getVerticalPosition()) {
			case SUPER:
				switch(markStyleSource.getHorizontalPosition()) {
					case LEFT:
						tglbtnTopleft.setSelected(true);
						break;
					case CENTER:
						tglbtnTopcenter.setSelected(true);
						break;
					case RIGHT:
						tglbtnTopright.setSelected(true);
						break;
				}
				break;
			case SUB:
				switch(markStyleSource.getHorizontalPosition()) {
					case LEFT:
						tglbtnBottomleft.setSelected(true);
						break;
					case CENTER:
						tglbtnBottomcenter.setSelected(true);
						break;
					case RIGHT:
						tglbtnBottomright.setSelected(true);
						break;
				}
				break;
		}
		marginSpiner.setValue(markStyleSource.getMargin());
		markStyleSource.isInitializing = false;
	}

	private void chengePosition(HorizontalAlign hAlign, VerticalAlign vAlign){
		markStyleSource.isInitializing = true;
		markStyleSource.setVerticalPosition(vAlign);
		markStyleSource.isInitializing = false;
		markStyleSource.setHorizontalPosition(hAlign);
	}

}
