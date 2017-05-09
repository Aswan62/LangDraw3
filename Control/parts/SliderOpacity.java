package parts;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import java.awt.Dimension;

public class SliderOpacity extends JPanel{
	/**
	 *
	 */
	private static final long serialVersionUID = 5709299429224123636L;

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		slider.setEnabled(enabled);
		if(enabled) {
			opacity = (float)slider.getValue() / 10;
			label.setText(Float.toString(opacity));
		} else {
			label.setText("");
		}
	}

	private JSlider slider;
	private JLabel label;
	private float opacity;

	public SliderOpacity() {
		label = new JLabel("1.0");
		label.setBounds(133, 4, 29, 22);

		slider = new JSlider();
		slider.setBounds(0, 0, 132, 31);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				opacity = (float)slider.getValue() / 10;
				label.setText(Float.toString(opacity));
			}
		});
		setLayout(null);
		slider.setValue(10);
		slider.setMajorTickSpacing(1);
		slider.setMaximum(10);
		slider.setPaintTicks(true);
		slider.setSnapToTicks(true);

		add(slider);
		add(label);

		this.setPreferredSize(new Dimension(160, 36));
	}

	public float getOpacityValue() {
		float value = (float)slider.getValue() / 10;
		return value;
	}

	public int getAlphaValue() {
		int value = (int)((float)slider.getValue() / 10 * 255);
		return value;
	}


    public void setOpacityValue(float opacity) {
    	this.opacity = opacity;
    	slider.setValue((int)(opacity * 10));
    }

	public void setAlphaValue(int alpha) {
		slider.setValue((int)((float)alpha / 255 * 10));
	}

	public JSlider getSlider() {
		return slider;
	}
}
