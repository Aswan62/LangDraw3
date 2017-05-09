package components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import textEditor.InsertText;
import windows.EditorWindow;
import windows.StyleWindow;
import enums.LDXMLTags;

public class BodyTextArea extends JPanel{

	private RTextScrollPane bodyScrollPane;
	public RSyntaxTextArea bodyTextArea;
	private DocumentListener bodyDocumentListener;
	private JTextArea plainTextArea;
	private JPanel panel_2;

	private static PullDownButton btnSpanButton;
	private static PullDownButton btnInMarkButton;
	private static PullDownButton btnMarkButton;
	private static PullDownButton btnBRButton;


	public BodyTextArea() {

		final InputMap imap = getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.ALT_DOWN_MASK +  Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "selectBR");
		getActionMap().put("selectBR", selectBR);
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.ALT_DOWN_MASK +  Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "selectSpan");
		getActionMap().put("selectSpan", selectSpan);
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.ALT_DOWN_MASK +  Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "selectInMark");
		getActionMap().put("selectInMark", selectInMark);
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_M, KeyEvent.ALT_DOWN_MASK +  Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "selectMark");
		getActionMap().put("selectMark", selectMark);


		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "insertTag");
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		ImageIcon icon = new ImageIcon();
		renderer.setLeafIcon(icon);
		ImageIcon closeIcon = new ImageIcon();
		ImageIcon openIcon = new ImageIcon();
		renderer.setClosedIcon(closeIcon);
		renderer.setOpenIcon(openIcon);

		bodyTextArea = new RSyntaxTextArea();
		bodyTextArea.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				if(EditorWindow.sourceTextArea != null) {
					int index = bodyTextArea.getCaretPosition();
					int startIndex = EditorWindow.sourceTextArea.getText().indexOf("<" + LDXMLTags.Body + ">") + ("<" + LDXMLTags.Body + ">").length();
					if(startIndex != -1) {
						index += startIndex;
						//render.ImageCanvas.changeCaretIndex(index);
					}
				}
			}
		});
		bodyTextArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				StyleWindow.editByTextInput = true;
			}
		});
		bodyTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		bodyTextArea.setLineWrap(true);
		bodyTextArea.setWhitespaceVisible(true);
		bodyTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
		bodyDocumentListener = new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				updateSourceText(bodyTextArea.getText());
		    	setPlainText(bodyTextArea.getText());
			}
		};
		bodyTextArea.getDocument().addDocumentListener(bodyDocumentListener);
		this.setLayout(new BorderLayout(0, 0));

		panel_2 = new JPanel();
		this.add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));


		btnBRButton = new PullDownButton(LDXMLTags.BR, null, bodyTextArea);
		btnBRButton.setToolTipText("inserts a break tag, <BR/>.");
		panel_2.add(btnBRButton);
		btnBRButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InsertText.InsertBR(null, bodyTextArea);
			}
		});
		btnBRButton.setMnemonic('B');

		btnSpanButton = new PullDownButton(LDXMLTags.Span, null, bodyTextArea);
		btnSpanButton.setToolTipText("inserts span tags, <Span></Span>, to selected text.");
		panel_2.add(btnSpanButton);
		btnSpanButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InsertText.InsertSpan(null, bodyTextArea);
			}
		});
		btnSpanButton.setMnemonic('S');


		btnInMarkButton = new PullDownButton(LDXMLTags.InMark, null, bodyTextArea);
		btnInMarkButton.setToolTipText("inserts a text-inside mark tag, <InMark>.");
		panel_2.add(btnInMarkButton);
		btnInMarkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InsertText.InsertInMark(null, bodyTextArea);
			}
		});
		btnInMarkButton.setMnemonic('I');

		btnMarkButton = new PullDownButton(LDXMLTags.Mark, null, bodyTextArea);
		btnMarkButton.setToolTipText("inserts mark tags, <Mark></Mark>, to selected text.");
		panel_2.add(btnMarkButton);
		btnMarkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InsertText.InsertMark(null, bodyTextArea);
			}
		});
		btnMarkButton.setMnemonic('M');

		String[] combodata = {"6", "8", "9", "10", "11", "12", "13", "14", "16", "18"};

		fontSizecomboBox = new JComboBox(combodata);
		fontSizecomboBox.setSelectedItem(Integer.toString(getFontSize()));
		fontSizecomboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int fontSize = Integer.valueOf(fontSizecomboBox.getSelectedItem().toString());
				setFontSize(fontSize);
			}
		});

		lblInputTextOf = new JLabel("<HTML>Font size of<BR/>input text");
		panel_2.add(lblInputTextOf);
		fontSizecomboBox.setEditable(true);
		panel_2.add(fontSizecomboBox);

		bodyScrollPane = new RTextScrollPane(bodyTextArea);


		plainTextArea = new JTextArea();
		plainTextArea.setEditable(false);
		plainTextArea.setLineWrap(true);
		plainTextArea.setBorder(new TitledBorder(null, "Plain Text", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setMinimumSize(new Dimension(23, 50));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setViewportView(plainTextArea);

		splitPane = new JSplitPane();
		splitPane.setDividerSize(3);
		splitPane.setResizeWeight(1.0);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setTopComponent(bodyScrollPane);
		splitPane.setBottomComponent(scrollPane);
		this.add(splitPane, BorderLayout.CENTER);

	}


	AbstractAction selectBR = new AbstractAction() {
		  /**
		 *
		 */
		private static final long serialVersionUID = -7027427181542659542L;
		@Override
		public void actionPerformed(ActionEvent e) {
			btnBRButton.showPopupMenu();
		}
	};

	AbstractAction selectSpan = new AbstractAction() {
		 /**
		 *
		 */
		private static final long serialVersionUID = -7252411473683325323L;

		@Override
		public void actionPerformed(ActionEvent e) {
			btnSpanButton.showPopupMenu();
		}
	};

	AbstractAction selectInMark = new AbstractAction() {
		 /**
		 *
		 */
		private static final long serialVersionUID = -8064280076553902761L;

		@Override
		public void actionPerformed(ActionEvent e) {
			btnInMarkButton.showPopupMenu();
		}
	};

	AbstractAction selectMark = new AbstractAction() {
		 /**
		 *
		 */
		private static final long serialVersionUID = 882176438463036050L;

		@Override
		public void actionPerformed(ActionEvent e) {
			btnMarkButton.showPopupMenu();
		}
	};
	private JSplitPane splitPane;
	private JComboBox fontSizecomboBox;
	private JLabel lblInputTextOf;


	public void setFontSize(int size) {
		fontSizecomboBox.setSelectedItem(size);
		Font font = bodyTextArea.getFont();
		font = new Font(font.getFamily(),0, size);
		bodyTextArea.setFont(font);

	}



	public int getFontSize() {
		return bodyTextArea.getFont().getSize();
	}

	public void setText(String text) {
		bodyTextArea.setText(text);
	}

	public String getText() {
		return bodyTextArea.getText();
	}


	private void setPlainText(String text) {
		Pattern pattern = Pattern.compile("<.*?>");
		Matcher m = pattern.matcher(text);
		String result = m.replaceAll("");
		result = result.replace("\n", "");
		result = result.replace("\r", "");
		plainTextArea.setText(result);
	}

	public static void updateSourceText(String bodyText) {
		if(bodyText.isEmpty()) bodyText = " ";
		String source = "<" + LDXMLTags.Body + ">" + bodyText + "</" + LDXMLTags.Body + ">";
		int startOldIndex = EditorWindow.sourceTextArea.getText().indexOf("<" + LDXMLTags.Body + ">");
        int endOldIndex = EditorWindow.sourceTextArea.getText().indexOf("</" + LDXMLTags.Body + ">") + ("</" + LDXMLTags.Body + ">").length();

	    if(startOldIndex != -1 && endOldIndex != -1)
	    {
	    	String oldText = EditorWindow.sourceTextArea.getText().substring(startOldIndex, endOldIndex);
	    	EditorWindow.setSourceText(EditorWindow.sourceTextArea.getText().replace(oldText, source));
    	}
	}

	public void setBRButton(ArrayList<String> menuItems) {
    	btnBRButton.setMenus(menuItems);
	}

	public void setSpanButtonMenu(ArrayList<String> menuItems) {
    	btnSpanButton.setMenus(menuItems);
	}

	public void setInMarkButton(ArrayList<String> menuItems) {
    	btnInMarkButton.setMenus(menuItems);
	}
	public void setMarkButton(ArrayList<String> menuItems) {
		btnMarkButton.setMenus(menuItems);
	}
}
