package windows;

import org.jdesktop.swingx.JXBusyLabel;
import render.ImageCanvas;
import textEditor.PaserThread;
import components.*;

import itemList.StyleList;

import javax.swing.JScrollPane;
import java.awt.BorderLayout;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.border.TitledBorder;
import java.awt.FlowLayout;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import java.awt.Point;

public class DrawingWindow extends JFrame implements ActionListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 2949054621634767651L;
	public static JXBusyLabel busyLabel;
	private static DefaultMutableTreeNode root;
	private static DefaultMutableTreeNode  textBoxNode, arrowNode;
	public static ImageCanvas canvas;
	private Timer timer;
	private JScrollPane scrollPane;
	private JPanel panel_1;
	private JPanel panel_2;
	private static PullDownButton btnDrawTextbox;
	private static PullDownButton btnDrawArrow;

	public DrawingWindow() {
		setLocation(new Point(300, 22));
		setMinimumSize(new Dimension(300, 300));
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentHidden(ComponentEvent e) {
				MainWindow.setImageEditorShown(false);
			}
			@Override
			public void componentShown(ComponentEvent e) {
				MainWindow.setImageEditorShown(true);
			}
		});
		setTitle("Drawing window");
		setResizable(true);

		textBoxNode = new DefaultMutableTreeNode("TextBox (T)");
		arrowNode = new DefaultMutableTreeNode("Arrow (A)");
		root = new DefaultMutableTreeNode("Style");
		root.add(textBoxNode);
		root.add(arrowNode);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.EAST);
		panel.setLayout(new BorderLayout(0, 0));

		busyLabel = new JXBusyLabel();
		busyLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				busyLabel.setBusy(!busyLabel.isBusy());
			}
		});
		panel.add(busyLabel, BorderLayout.SOUTH);

		final JSlider slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				double val = Math.exp(1.3*(slider.getValue() / 50D - 1));
				lblSize.setText(Math.round(val*100) + "%");
				AffineTransform at = AffineTransform.getScaleInstance(val, val);
				ImageCanvas.setImageScale(at);
				PaserThread.fouceUpdate = true;
			}
		});
		panel.add(slider, BorderLayout.CENTER);
		slider.setMajorTickSpacing(10);
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		slider.setOrientation(SwingConstants.VERTICAL);
		
		lblSize = new JLabel("100%");
		panel.add(lblSize, BorderLayout.NORTH);
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		ImageIcon icon = new ImageIcon();
		renderer.setLeafIcon(icon);
		ImageIcon closeIcon = new ImageIcon();
		ImageIcon openIcon = new ImageIcon();
		renderer.setClosedIcon(closeIcon);
		renderer.setOpenIcon(openIcon);

		panel_1 = new JPanel();
		getContentPane().add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);
		scrollPane.setViewportBorder(new TitledBorder(null, "Image", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setPreferredSize(new Dimension(250, 200));
		canvas = new ImageCanvas(scrollPane);
		scrollPane.setViewportView(canvas);

		panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_1.add(panel_2, BorderLayout.NORTH);

		btnDrawArrow = new PullDownButton("Draw Arrow", null, null);
		btnDrawArrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawArrowPress();
			}
		});
		btnDrawArrow.setMnemonic('A');
		panel_2.add(btnDrawArrow);

		btnDrawTextbox = new PullDownButton("Draw TextBox", null, null);
		btnDrawTextbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawTextBoxPress();
			}
		});
		btnDrawArrow.setMnemonic('T');
		panel_2.add(btnDrawTextbox);


		InputMap imap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.ALT_DOWN_MASK + KeyEvent.CTRL_DOWN_MASK), "selectArrow");
		getRootPane().getActionMap().put("selectArrow", selectArrow);

		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.ALT_DOWN_MASK + KeyEvent.CTRL_DOWN_MASK), "SelectTextBox");
		getRootPane().getActionMap().put("SelectTextBox", selectTextBox);

		setLabelBusy(false);
		timer = new Timer(500 , this);
		timer.start();
		this.pack();

	}

	AbstractAction selectArrow = new AbstractAction() {
		  /**
		 *
		 */
		private static final long serialVersionUID = -8637013975401527342L;

		@Override
		public void actionPerformed(ActionEvent e) {
			btnDrawArrow.showPopupMenu();
		}
	};

	AbstractAction selectTextBox = new AbstractAction() {
		  /**
		 *
		 */
		private static final long serialVersionUID = -2239921530735277994L;

		@Override
		public void actionPerformed(ActionEvent e) {
			btnDrawTextbox.showPopupMenu();
		}
	};
	private JLabel lblSize;

	private void drawTextBoxPress() {
		TextBoxDialog textBoxDialog = new TextBoxDialog();
		textBoxDialog.setVisible(true);
		if(textBoxDialog.result)
			canvas.startTextBoxEdit(null, textBoxDialog.getText());
	}

	private void drawArrowPress() {
		canvas.startArrowEdit(null);
	}

	public void actionPerformed(ActionEvent e) {
		scrollPane.repaint();
	}


	static public void setLabelBusy(boolean isBusy) {
 		busyLabel.setBusy(true);
	}

	static public boolean isLabelBusy() {
		return busyLabel.isBusy();
	}

	public static void setStyles(){
		ArrayList<String> menuItems = new ArrayList<String>();

	    if(StyleList.TextBoxStyleList != null) {
			menuItems = new ArrayList<String>();
	    	textBoxNode.removeAllChildren();
	    	for(String key : StyleList.TextBoxStyleList.keySet()) {
	    		if(key != null) {
	    			textBoxNode.add(new DefaultMutableTreeNode(key));
	    			menuItems.add(key);
	    		}
	    	}
	    	btnDrawTextbox.setMenus(menuItems);
	    }

	    if(StyleList.ArrowStyleList != null) {
	    	menuItems = new ArrayList<String>();
	    	arrowNode.removeAllChildren();
	    	for(String key : StyleList.ArrowStyleList.keySet()) {
	    		if(key != null) {
	    			arrowNode.add(new DefaultMutableTreeNode(key));
	    			menuItems.add(key);
	    		}
	    	}
	    	btnDrawArrow.setMenus(menuItems);
	    }
	}
}
