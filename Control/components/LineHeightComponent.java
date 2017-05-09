package components;

import org.jdesktop.swingx.JXTaskPane;

import parts.LDSpiner;
import style.BRStyleSource;

import javax.swing.UIManager;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class LineHeightComponent extends JXTaskPane {

	/**
	 *
	 */
	private static final long serialVersionUID = -3931947502473552505L;
	BRStyleSource brStyleSource;
	private LDSpiner spinner;

	public LineHeightComponent(BRStyleSource source) {
		brStyleSource = source;
		setTitle("Line-Height");
		getContentPane().setBackground(UIManager.getColor("control"));
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);

		spinner = new LDSpiner(0,20,"px");
		spinner.getSpinner().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				brStyleSource.setLineHeight(spinner.getValue());
			}
		});
		panel.add(spinner);
	}

	public void initializeValue(BRStyleSource source) {
		brStyleSource = source;
		brStyleSource.isInitializing = true;
		spinner.setValue(brStyleSource.getLineHeight());
		brStyleSource.isInitializing = false;
	}

}
