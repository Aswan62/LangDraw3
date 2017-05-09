package windows;


import itemList.StyleList;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import components.BodyTextArea;
import components.CloseButtonTabbedPane;

import enums.LDXMLTags;

public class EditorWindow extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 9004427153643920005L;
	static public RSyntaxTextArea sourceTextArea;
	private static DocumentListener sourceDocumentListener;
	private RTextScrollPane sourceScrollPane;
	private static CloseButtonTabbedPane tabbedPane;

	static public String sourceText = "";

	private static BodyTextArea bodyTextArea;
	private BodyTextArea currentBodyTextArea;

	int snapNumber = 1;

	public EditorWindow() {

		new JTextArea();
		setLayout(new BorderLayout(0, 0));

		final Icon icon = new CloseTabIcon();
		tabbedPane = new CloseButtonTabbedPane(icon);
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if(tabbedPane.getSelectedComponent().getClass() == bodyTextArea.getClass()){
					currentBodyTextArea = (BodyTextArea) tabbedPane.getSelectedComponent();
					BodyTextArea.updateSourceText(currentBodyTextArea.getText());
				}
			}
		});

		add(tabbedPane);
		sourceTextArea = new RSyntaxTextArea();

		bodyTextArea = new BodyTextArea();
		currentBodyTextArea = bodyTextArea;
		tabbedPane.addTab("<Body></Body>", bodyTextArea, false);


		sourceTextArea.setEditable(false);
		sourceTextArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				StyleWindow.editByTextInput = true;
			}
		});
		sourceTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		sourceTextArea.setLineWrap(true);
		sourceTextArea.setAntiAliasingEnabled(true);
		sourceTextArea.setWhitespaceVisible(true);
		sourceTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
		sourceDocumentListener= new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				MainWindow.setXmlSourceSaved(false);
				sourceText = sourceTextArea.getText();
			}
		};
		sourceTextArea.getDocument().addDocumentListener(sourceDocumentListener);
		sourceScrollPane = new RTextScrollPane(sourceTextArea);
		sourceScrollPane.getTextArea().setEditable(false);


		JPanel panel_3 = new JPanel();
		panel_3.setToolTipText("");
		panel_3.setLayout(new BorderLayout(0, 0));
		panel_3.add(sourceScrollPane);
		tabbedPane.addTab("Entire Source", panel_3, false);
	}


	public static void setBodyText(String text) {
    	bodyTextArea.setText(text);
	}

	public static void setSourceToBodyText(String text) {
		int startIndex = sourceTextArea.getText().indexOf("<" + LDXMLTags.Body + ">") + ("<" + LDXMLTags.Body + ">").length();
        int endIndex = sourceTextArea.getText().indexOf("</" + LDXMLTags.Body + ">");
        if(startIndex != -1 && endIndex != -1)
	    {
        	text = text.substring(startIndex, endIndex);
	    }

    	bodyTextArea.setText(text);
	}

	public static String getBodyText() {
		return bodyTextArea.getText();
	}

	public static void setSourceText(String text) {
    	sourceTextArea.setText(text);
	}

	public static String getSourceText() {
		return sourceTextArea.getText();
	}

	public static void setStyles(){
		ArrayList<String> menuItems = new ArrayList<String>();

		if(StyleList.BRStyleList != null) {
			menuItems = new ArrayList<String>();
	    	for(String key : StyleList.BRStyleList.keySet()) {
	    		if(key != null) {
	    			menuItems.add(key);
	    		}
	    	}
	    	bodyTextArea.setBRButton(menuItems);
		}

	    if(StyleList.SpanStyleList != null) {
	    	menuItems = new ArrayList<String>();
	    	for(String key : StyleList.SpanStyleList.keySet()) {
	    		if(key != null) {
	    			menuItems.add(key);
	    		}
	    	}
	    	bodyTextArea.setSpanButtonMenu(menuItems);
	    }

	    if(StyleList.InMarkStyleList != null) {
	    	menuItems = new ArrayList<String>();
	    	for(String key : StyleList.InMarkStyleList.keySet()) {
	    		if(key != null) {
	    			menuItems.add(key);
	    		}
	    	}
	    	bodyTextArea.setInMarkButton(menuItems);
	    }

	    if(StyleList.MarkStyleList != null) {
	    	menuItems = new ArrayList<String>();
	    	for(String key : StyleList.MarkStyleList.keySet()) {
	    		if(key != null) {
	    			menuItems.add(key);
	    		}
	    	}
	    	bodyTextArea.setMarkButton(menuItems);
	    }

	}

	public static void expandAll(JTree tree) {
		int row = 0;
		while(row<tree.getRowCount()) {
			tree.expandRow(row);
			row++;
		}
	}

	public static void collapseAll(JTree tree) {
		int row = 0;
		while(row<tree.getRowCount()) {
			tree.collapseRow(row);
			row++;
		}
	}


	public void setFontSize(int fontSize) {
		if(fontSize < 4) {
			fontSize = 4;
		}
		else if (fontSize > 100) {
			fontSize = 100;
		}

		bodyTextArea.setFontSize(fontSize);
		Font font = bodyTextArea.getFont();
		font = new Font(font.getFamily(),0, fontSize);

		sourceTextArea.setFont(font);
	}


	public int getFontSize() {
		return bodyTextArea.getFontSize();
	}

	private static class CloseTabIcon implements Icon {
        private int width;
        private int height;
        public CloseTabIcon() {
            width  = 16;
            height = 16;
        }
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.translate(x, y);
            g.setColor(Color.BLACK);
            g.drawLine(4,  4, 11, 11);
            g.drawLine(4,  5, 10, 11);
            g.drawLine(5,  4, 11, 10);
            g.drawLine(11, 4,  4, 11);
            g.drawLine(11, 5,  5, 11);
            g.drawLine(10, 4,  4, 10);
            g.translate(-x, -y);
        }
        @Override
        public int getIconWidth() {
            return width;
        }
        @Override
        public int getIconHeight() {
            return height;
        }
    }

	public void addSnap() {
		BodyTextArea oldTextArea = currentBodyTextArea;
		currentBodyTextArea = new BodyTextArea();
		currentBodyTextArea.setText(oldTextArea.getText());

		tabbedPane.addTab("Snap_".concat(Integer.toString(snapNumber)), currentBodyTextArea, true);
		snapNumber += 1;

		BodyTextArea.updateSourceText(oldTextArea.getText());
	}

}
