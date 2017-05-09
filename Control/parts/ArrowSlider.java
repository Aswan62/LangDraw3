package parts;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class ArrowSlider extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8967687148621890504L;
	private JSlider slider;
	private JLabel lblNewLabel;

	public ArrowSlider() {
		setLayout(new BorderLayout(0, 0));

		lblNewLabel = new JLabel("x1.0");
		add(lblNewLabel, BorderLayout.EAST);

		slider = new JSlider();
		slider.setValue(10);
		slider.setMaximum(20);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				lblNewLabel.setText("x".concat(Float.toString(slider.getValue()/10F)));
			}
		});
		slider.setMinorTickSpacing(1);
		slider.setMinimum(1);
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		add(slider, BorderLayout.CENTER);

		this.setPreferredSize(new Dimension(190, 30));
	}

	public JSlider getSlider() {
		return slider;
	}

	public void setValue(float headSize) {
		slider.setValue((int) (headSize*10));
	}

	public float getValue() {
		return slider.getValue()/10F;
	}

}
