package render;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import shapes.SelectorRect;
import shapes.SelectorText;

import enums.Enums.TextDecoration;
import format.ArrowFormat;
import format.BaseFormat;
import format.MarkFormat;
import format.TextBoxFormat;
import format.WordFormat;

public class EditView {

	ArrayList<SelectorText> texts = new ArrayList<SelectorText>();
	ArrayList<SelectorRect> rects = new ArrayList<SelectorRect>();
	private BaseFormat selectedFormat;
	private BaseFormat hoveredFormat;
	private ImageCanvas canvas;

	public EditView(ImageCanvas canvas, ArrayList<ArrowFormat> arrows, ArrayList<TextBoxFormat> textBoxes, Point2D pointScreen) {
		this.canvas = canvas;
		Point2D startPoint = new Point2D.Double(pointScreen.getX() + 20, pointScreen.getY());

		for(int i=0; i < arrows.size(); i++) {
			SelectorText selectorText = new SelectorText("Arrow "+ Integer.toString(i+1), startPoint);
			texts.add(selectorText);
			SelectorRect selectorRect = new SelectorRect(arrows.get(i), selectorText.getTextRectangle2D());
			rects.add(selectorRect);

			startPoint = new Point2D.Double(startPoint.getX(), startPoint.getY() + selectorRect.getBounds2D().getHeight() + 5);
		}

		for(int i=0; i < textBoxes.size(); i++) {
			SelectorText selectorText = new SelectorText("TextBox "+ Integer.toString(i+1), startPoint);
			texts.add(selectorText);
			SelectorRect selectorRect = new SelectorRect(textBoxes.get(i), selectorText.getTextRectangle2D());
			rects.add(selectorRect);

			startPoint = new Point2D.Double(startPoint.getX(), startPoint.getY() + selectorRect.getBounds2D().getHeight() + 5);
		}

		SelectorText selectorText = new SelectorText("Cancel", startPoint);
		texts.add(selectorText);
		SelectorRect selectorRect = new SelectorRect(null, selectorText.getTextRectangle2D());
		rects.add(selectorRect);
	}

	public Rectangle2D draw(Graphics2D g, Rectangle2D bounds, AffineTransform scaleAt) {
		if(hoveredFormat != null) {
			if(hoveredFormat.getClass().getName() == ArrowFormat.class.getName()) {
				ArrowFormat af = (ArrowFormat) hoveredFormat;
				af.drawArrow(g);
			}
			else if(hoveredFormat.getClass().getName() == TextBoxFormat.class.getName()) {
				TextBoxFormat tf = (TextBoxFormat) hoveredFormat;
				g.setColor(tf.textBoxBorder.getBackgroundColor());
				g.fill(tf.getTextBoxRect());

				for(WordFormat wordFormat: tf.sourceTexts) {
					g.setColor(wordFormat.getFontColor());
					g.fill(wordFormat.getTextShape());

					g.setColor(wordFormat.getDecorationColor());
					if(wordFormat.getDecoration() == TextDecoration.OVERLINE) {
						g.setStroke(new BasicStroke(wordFormat.getOverlineWidth(),BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER));
						g.draw(wordFormat.getOverline());
					}
					else if(wordFormat.getDecoration() == TextDecoration.STRIKE) {
						g.setStroke(new BasicStroke(wordFormat.getStrikeWidth(),BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER));
						g.draw(wordFormat.getStrikeline());
					}
					else if(wordFormat.getDecoration() == TextDecoration.UNDERLINE) {
						g.setStroke(new BasicStroke(wordFormat.getUnderlineWidth(),BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER));
						g.draw(wordFormat.getUnderline());
					}
				}
				g.setColor(tf.textBoxBorder.getBorderColor());
				g.setStroke(new BasicStroke(tf.textBoxBorder.getLineWidth()));
				g.draw(tf.getTextBoxRect());
			}
			else if(hoveredFormat.getClass().getName() == WordFormat.class.getName()) {
				WordFormat wf = (WordFormat) hoveredFormat;
				g.setColor(wf.getFontColor());
				g.fill(wf.getTextShape());
			}
			else if(hoveredFormat.getClass().getName() == MarkFormat.class.getName()) {
				MarkFormat mf = (MarkFormat) hoveredFormat;
				g.setColor(mf.getFontColor());
				g.fill(mf.getTextShape());
			}
		}

		g.setStroke(new BasicStroke(1));
		for(SelectorRect selectorRect: rects) {
			g.setColor(Color.BLUE);
			g.draw(selectorRect);
			g.setColor(new Color(255, 255, 255, 180));
			g.fill(selectorRect);
			Rectangle2D.union(bounds, selectorRect.getBounds2D(), bounds);
		}

		g.setColor(Color.BLUE);
		for(SelectorText selectorText: texts) {
			g.fill(selectorText);
		}

		return bounds;
	}

	public boolean select(Point2D point) {
		for(SelectorRect selectorRect : rects) {
			if(selectorRect.contains(point)) {
				selectedFormat = selectorRect.getFormat();
				return true;
			}
		}
		return false;
	}

	public void hover(Point2D point) {
		for(SelectorRect selectorRect : rects) {
			if(selectorRect.contains(point)) {
				hoveredFormat = selectorRect.getFormat();
				canvas.changeCursor(Cursor.HAND_CURSOR);
				break;
			} else {
				hoveredFormat = null;
				canvas.changeCursor(Cursor.DEFAULT_CURSOR);
			}
		}
	}

	public BaseFormat getSelectedFormat() {
		return selectedFormat;
	}
}
