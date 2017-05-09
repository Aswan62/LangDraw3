package selectStyles;

import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;

import windows.MainWindow;

abstract class IDNameDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6946580967160011690L;

	public IDNameDialog(String text) {
		setResizable(false);
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);

		JButton btnYes = new JButton("Yes");
		panel.add(btnYes);

		JButton btnNo = new JButton("No");
		panel.add(btnNo);

		JButton btnCancel = new JButton("Cancel");
		panel.add(btnCancel);

		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setVgap(10);
		flowLayout.setHgap(10);
		getContentPane().add(panel_1, BorderLayout.NORTH);

		JLabel lblDeleteAll = new JLabel(text);
		panel_1.add(lblDeleteAll);
		lblDeleteAll.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDeleteAll.setHorizontalAlignment(SwingConstants.CENTER);
		lblDeleteAll.setFont(new Font("MS UI Gothic", Font.PLAIN, 14));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialogNo();
				dispose();
			}
		});
		btnYes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialogYes();
				dispose();
			}
		});

		this.pack();
		int x = MainWindow.getMainWindow().getX() + MainWindow.getMainWindow().getWidth()/2 - this.getWidth()/2;
		int y = MainWindow.getMainWindow().getY() + MainWindow.getMainWindow().getHeight()/2 - this.getHeight()/2;
		this.setLocation(x, y);

		InputMap imap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "close");
		getRootPane().getActionMap().put("close", close);

		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");
		getRootPane().getActionMap().put("enter", close);
	}

	AbstractAction close = new AbstractAction() {
		  /**
		 * 
		 */
		private static final long serialVersionUID = 4615233841930178418L;

		public void actionPerformed(ActionEvent e) {
			  dispose();
		  }
	};

	AbstractAction enter = new AbstractAction() {
		  /**
		 * 
		 */
		private static final long serialVersionUID = -7484900982030189918L;

		public void actionPerformed(ActionEvent e) {
			  dialogYes();
		  }
	};

	abstract public void dialogYes();
	abstract public void dialogNo();
}
