package components;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.SwingConstants;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;

import enums.*;
import textEditor.*;
import windows.*;

public class PullDownButton extends JButton{

	/**
	 *
	 */
	private static final long serialVersionUID = -8487579258549326514L;
	ArrayList<String> menuItems;
	boolean isMouseOvered = false;
	String buttonText;
	Border defaultBorder;
	RSyntaxTextArea textArea;

	public PullDownButton(String text, ArrayList<String> menus, RSyntaxTextArea area) {
		super(text);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				isMouseOvered = true;
			}
			@Override
			public void mouseExited(MouseEvent e) {
				isMouseOvered = false;
			}
		});

		buttonText = text;
		defaultBorder = getBorder();
		textArea = area;

		if(menus != null && menus.size() > 0) {
			setHorizontalAlignment(SwingConstants.LEFT);
			setFocusPainted(false);
			menuItems = menus;

			Border border= BorderFactory.createEtchedBorder();
	        setBorder(BorderFactory.createCompoundBorder(border,new PullDownBorder()));

	        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
		}
	}

	public void setMenus(ArrayList<String> menus) {
		menuItems = menus;
		if(menus != null && menus.size() > 0) {
			setHorizontalAlignment(SwingConstants.LEFT);
			setFocusPainted(false);
			Border border= BorderFactory.createEtchedBorder();
	        setBorder(BorderFactory.createCompoundBorder(border,new PullDownBorder()));

	        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
		}
		else {
			setHorizontalAlignment(SwingConstants.CENTER);
			setBorder(defaultBorder);
			disableEvents(AWTEvent.MOUSE_EVENT_MASK);
		}
	}


	protected void processMouseEvent(MouseEvent evt){
        if(isEnabled()==false){
            return;
        }
	        switch(evt.getID()) {
	            case MouseEvent.MOUSE_PRESSED:
	                Border border = getBorder();
	                Insets insets = border.getBorderInsets(this);

	                if(menuItems != null && menuItems.size() > 0) {
		                if(evt.getX() >= getWidth() - insets.right) {
		                    showPopupMenu(0,getHeight());
		                }
		                else{
		                    super.processMouseEvent(evt);
		                }
	                } else {
	                	super.processMouseEvent(evt);
	                }

	                break;

	            case MouseEvent.MOUSE_EXITED:
	                setCursor(Cursor.getDefaultCursor());
	                super.processMouseEvent(evt);
	                break;

	            default:
	                super.processMouseEvent(evt);
	                break;
	        }
    }

    private void showPopupMenu(int x,int y){
    	if(menuItems != null && menuItems.size() > 0) {
    		int i = 1;
	        JPopupMenu popup=new JPopupMenu();
	        for(String menuText :menuItems) {
	        	JMenuItem menu = new JMenuItem(new MyAction(menuText));
	        	if(i < 10) menu.setAccelerator(KeyStroke.getKeyStroke(Integer.toString(i).charAt(0),KeyEvent.CTRL_DOWN_MASK));
	        	i += 1;
	        	popup.add(menu);
	        }
	        popup.show(this,x,y);
    	}
    }

    public void showPopupMenu(){
    	showPopupMenu(0,getHeight());
    }


    class MyAction extends AbstractAction {
    	/**
    	 *
    	 */
    	private static final long serialVersionUID = 1412272058678973738L;

    	public MyAction(String text) {
    		super(text);
    	}

    	@Override
    	public void actionPerformed(ActionEvent e) {
    		if(textArea != null) {
	    		if(buttonText.equalsIgnoreCase(LDXMLTags.InMark)) {
	    			InsertText.InsertInMark(((JMenuItem)e.getSource()).getText(), textArea);
	    		}
	    		else if(buttonText.equalsIgnoreCase(LDXMLTags.Mark)) {
	    			InsertText.InsertMark(((JMenuItem)e.getSource()).getText(), textArea);
	    		}
	    		else if(buttonText.equalsIgnoreCase(LDXMLTags.Span)) {
	    			InsertText.InsertSpan(((JMenuItem)e.getSource()).getText(), textArea);
	    		}
	    		else if(buttonText.equalsIgnoreCase(LDXMLTags.BR)) {
	    			InsertText.InsertBR(((JMenuItem)e.getSource()).getText(), textArea);
	    		}
    		} else {}
    			
    		if(buttonText.equalsIgnoreCase("Draw " + LDXMLTags.TextBox)) {
				TextBoxDialog textBoxDialog = new TextBoxDialog();
				textBoxDialog.setVisible(true);
				if(textBoxDialog.result)
					DrawingWindow.canvas.startTextBoxEdit(((JMenuItem)e.getSource()).getText(), textBoxDialog.getText());
    		}
    		else if(buttonText.equalsIgnoreCase("Draw " + LDXMLTags.Arrow)) {
    			DrawingWindow.canvas.startArrowEdit(((JMenuItem)e.getSource()).getText());
    		}
    	}
    }

    class PullDownBorder extends AbstractBorder {
        /**
    	 *
    	 */
    	private static final long serialVersionUID = 2521307474750331106L;
    	static final int WIDTH = 16;

        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            g.translate(x + w - WIDTH, y - 1);

            int w2 = WIDTH / 2;
            int h2 = h / 2;
            g.setColor(Color.black);

            g.drawLine(w2 - 5, h2 - 2, w2 + 4, h2 - 2);
            g.drawLine(w2 - 4, h2 - 1,w2 + 3,h2 - 1);
            g.drawLine(w2 - 3, h2, w2 + 2, h2);
            g.drawLine(w2 - 2, h2 + 1, w2 + 1, h2 + 1);
            g.drawLine(w2 - 1, h2 + 2, w2, h2 + 2);

            g.setColor(Color.GRAY);
            g.drawLine(0, 0, 0, h);
            g.translate(-(x + w - WIDTH), -(y - 1));

            if(isMouseOvered) {
            	g.setColor(Color.GRAY);
            	g.drawRect(1, 1, w - 1, h - 1);
            }
        }

        public Insets getBorderInsets(Component c){
            return new Insets(4,4,4,WIDTH + 4);
        }
    }
}




