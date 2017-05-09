package windows;

import itemList.StyleList;

import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.FlowLayout;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import components.*;
import textEditor.*;

public class TextBoxDialog extends JDialog {

	/**
	 *
	 */
	private static final long serialVersionUID = 3444435520782327239L;
	private RSyntaxTextArea textArea;
	private RTextScrollPane textScrollPane;
	public boolean result;

	private PullDownButton btnBr;
	private PullDownButton btnSpan;

	public TextBoxDialog() {
		result = false;
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setModal(true);
		setAlwaysOnTop(true);
		setTitle("Input TextBox Source");

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		JButton btnOk = new JButton("OK");
		btnOk.setMnemonic('O');
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				result = true;
				dispose();
			}
		});
		panel.add(btnOk);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setMnemonic('C');
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				result = false;
				dispose();
			}
		});
		panel.add(btnCancel);

		textArea = new RSyntaxTextArea();
		textArea.setRows(5);
		textArea.setColumns(40);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textScrollPane = new RTextScrollPane(textArea);
		getContentPane().add(textScrollPane, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BorderLayout(0, 0));

		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new FlowLayout(FlowLayout.LEFT));


		ArrayList<String> items = new ArrayList<String>();
		if(StyleList.BRStyleList != null) {
			items = new ArrayList<String>();
	    	for(String key : StyleList.BRStyleList.keySet()) {
	    		if(key != null) items.add(key);
	    	}
		}
		btnBr = new PullDownButton("BR",items, textArea);
		btnBr.setMnemonic('B');
		btnBr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InsertText.InsertBR(null, textArea);
			}
		});
		panel_2.add(btnBr);


	    if(StyleList.SpanStyleList != null) {
	    	items = new ArrayList<String>();
	    	for(String key : StyleList.SpanStyleList.keySet()) {
	    		if(key != null) items.add(key);
	    	}
	    }
		btnSpan = new PullDownButton("Span",items, textArea);
		btnSpan.setMnemonic('S');
		btnSpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  InsertText.InsertSpan(null, textArea);
			}
		});
		panel_2.add(btnSpan);

		pack();

		InputMap imap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "dispose");
		getRootPane().getActionMap().put("dispose", close);

		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.ALT_DOWN_MASK + KeyEvent.CTRL_DOWN_MASK), "insertBR");
		getRootPane().getActionMap().put("insertBR", insertBR);

		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.ALT_DOWN_MASK + KeyEvent.CTRL_DOWN_MASK), "insertSpan");
		getRootPane().getActionMap().put("insertSpan", insertSpan);
	}

	public String getText() {
		return textArea.getText();
	}

	AbstractAction close = new AbstractAction(){
		  /**
		 *
		 */
		private static final long serialVersionUID = 7705221770296355988L;

		public void actionPerformed(ActionEvent e) {
			  dispose();
		  }
	};

	AbstractAction insertBR = new AbstractAction(){

		/**
		 *
		 */
		private static final long serialVersionUID = 6753719805668743504L;

		public void actionPerformed(ActionEvent e) {
			btnBr.showPopupMenu();
		}
	};

	AbstractAction insertSpan = new AbstractAction(){

		/**
		 *
		 */
		private static final long serialVersionUID = 3441054032568519580L;

		public void actionPerformed(ActionEvent e) {
			btnSpan.showPopupMenu();
		}
	};
}
