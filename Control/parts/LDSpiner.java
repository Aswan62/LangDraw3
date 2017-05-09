package parts;

import org.jdesktop.swingx.JXPanel;
import javax.swing.JLabel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner;

import java.awt.Dimension;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class LDSpiner extends JXPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = -6722525333176422818L;
	private JSpinner spinner;

	public LDSpiner(float minValue, float defaultValue, String unit) {

		SpinnerNumberModel numberModel = new SpinnerNumberModel(0.0f, 0.0f, null, 0.5f);
		numberModel.setMinimum(minValue);
		numberModel.setValue(defaultValue);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{70, 30, 0};
		gridBagLayout.rowHeights = new int[]{20, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
				spinner = new JSpinner(numberModel);
				spinner.setEditor(new JSpinner.NumberEditor(spinner, "#0.0"));
				GridBagConstraints gbc_spinner = new GridBagConstraints();
				gbc_spinner.fill = GridBagConstraints.BOTH;
				gbc_spinner.insets = new Insets(0, 0, 0, 5);
				gbc_spinner.gridx = 0;
				gbc_spinner.gridy = 0;
				add(spinner, gbc_spinner);

				JLabel lblPx = new JLabel(unit);
				lblPx.setAlignmentX(Component.RIGHT_ALIGNMENT);
				GridBagConstraints gbc_lblPx = new GridBagConstraints();
				gbc_lblPx.fill = GridBagConstraints.VERTICAL;
				gbc_lblPx.anchor = GridBagConstraints.WEST;
				gbc_lblPx.gridx = 1;
				gbc_lblPx.gridy = 0;
				add(lblPx, gbc_lblPx);

		this.setPreferredSize(new Dimension(100, 24));
	}

	public void setValue(float val) {
		spinner.setValue(val);
	}

	public JSpinner getSpinner() {
		return spinner;
	}


	public float getValue() {
		return (Float) spinner.getValue();
	}

}
