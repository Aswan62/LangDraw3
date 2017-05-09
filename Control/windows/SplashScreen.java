package windows;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.Timer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

public class SplashScreen extends JWindow implements ActionListener {
	private JLabel splashLabel;
	private Timer timer;
	private boolean flag;

	public SplashScreen(ImageIcon icon, String vesion) {
		if(icon != null) {
			splashLabel = new JLabel(icon);
			this.getContentPane().add(splashLabel);
		}
		this.getContentPane().setBackground(null);

		JLabel versionLabel = new JLabel("Version: " + vesion);
		versionLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		getContentPane().add(versionLabel, BorderLayout.SOUTH);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		flag = false;
		timer = new Timer(2000, this);
		timer.start();
	}

	public void hideSplash() {
		while(true) {
			try {
				Thread.sleep(100);
				if(flag) break;
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		timer.stop();
		this.setVisible(false);
		splashLabel  = null;
		this.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		flag = true;
	}
}
