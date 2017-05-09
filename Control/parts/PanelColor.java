package parts;

import javax.swing.JColorChooser;
import javax.swing.JPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.border.BevelBorder;

import enums.LDAttributes;

import style.ArrowStyleSource;
import style.BaseStyleSource;

final public class PanelColor extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = -8359060550934993590L;
	private Color color = null, colorDummy = null;
	private JPanel panel;
	private boolean isFocused;
	private BaseStyleSource baseSource;
	private ArrowStyleSource arrowSource;

	public PanelColor(ArrowStyleSource source) {
		arrowSource = source;
		setLayout();
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showDialog();
				arrowSource.setArrowColor(getColor());
			}
		});

		this.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {

			}

			public void keyReleased(KeyEvent e) {

			}

			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_SPACE) {
					showDialog();
					arrowSource.setArrowColor(getColor());
				}
			}
		});

		this.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				isFocused = false;
				repaint();
			}

			public void focusGained(FocusEvent e) {
				isFocused = true;
				repaint();
			}
		});
	}

	public PanelColor(BaseStyleSource source, final String type) {
		baseSource = source;

		setLayout();

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showDialog();
				if(type == LDAttributes.BackgroundColor)
					baseSource.setBackgroundColor(getColor());
				else if(type == LDAttributes.BorderColor)
					baseSource.setBorderColor(getColor());
				else if(type == LDAttributes.FontColor)
					baseSource.setFontColor(getColor());
				else if(type == LDAttributes.TextDecorationColor)
					baseSource.setDecorationColor(getColor());
			}
		});

		this.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) { }

			public void keyReleased(KeyEvent e) { }

			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_SPACE) {
					showDialog();
					if(type == LDAttributes.BackgroundColor)
						baseSource.setBackgroundColor(getColor());
					else if(type == LDAttributes.BorderColor)
						baseSource.setBorderColor(getColor());
					else if(type == LDAttributes.FontColor)
						baseSource.setFontColor(getColor());
					else if(type == LDAttributes.TextDecorationColor)
						baseSource.setDecorationColor(getColor());
				}
			}
		});

		this.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				isFocused = false;
				repaint();
			}

			public void focusGained(FocusEvent e) {
				isFocused = true;
				repaint();
			}
		});
	}

	public void setSource(BaseStyleSource source) {
		baseSource = source;
	}

	public void setSource(ArrowStyleSource source) {
		arrowSource = source;
	}

	private void setLayout() {
		panel = this;
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		this.setFocusable(true);

		this.setPreferredSize(new Dimension(35,20));
	}

	public void setColor(Color color) {
		this.color = color;
		this.repaint();
	}

	public Color getColor() {
		return color;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.clearRect(0, 0, this.getSize().width, this.getSize().height);
		 ((Graphics2D) g).setStroke(new BasicStroke());
		if(this.color == null) {
			Point p = new Point(this.getSize().width, this.getSize().height);
			g.setColor(Color.RED);
			g.drawLine(0, 0, p.x, p.y);
		} else {
			g.setColor(this.color);
			g.fillRect(0, 0, this.getSize().width, this.getSize().height);
		}

		if(isFocused){
			  float dash[] = {3.0f, 1.0f};
			  g.setColor(getXorColor(color));
			  BasicStroke dashStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
			  ((Graphics2D) g).setStroke(dashStroke);
			  g.drawRect(2, 2, this.getSize().width - 4, this.getSize().height - 4);
		}

	}

	private Color getXorColor(Color color) {
		if(color != null) return new Color(color.getRed() ^ 255, color.getGreen() ^ 255, color.getBlue() ^ 255);
		else return null;
	}

	private void showDialog() {
		Color selectedColor = JColorChooser.showDialog(panel, "Select font color.", color);
		if(selectedColor != null) {
			color = selectedColor;
			repaint();
		}
	}


}
