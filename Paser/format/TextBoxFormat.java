package format;


import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.jdom.Element;

import enums.Enums.HorizontalAlign;
import enums.Padding;

public class TextBoxFormat extends BaseFormat {
	public ArrayList<WordFormat> sourceTexts;
	public ArrayList<BorderSetting> wordBorders;
	public BorderSetting textBoxBorder;
	HorizontalAlign align;
	boolean lineWrap;
	private float width;
	private Point2D boxLocation, topLeft, bottomLeft, topRight, bottomRight;

	public Point2D getBoxLocation(AffineTransform at) {
		Point2D result = (Point2D) boxLocation.clone();
		if(at != null) at.transform(boxLocation, result);
		return result;
	}

	public void setBoxLocation(Point2D p) {
		boxLocation = (Point2D) p.clone();
	}

	public Point2D getTopLeft(AffineTransform at) {
		Point2D result = (Point2D) topLeft.clone();
		if(at != null) at.transform(topLeft, result);
		return result;
	}

	public Point2D getBottomLeft(AffineTransform at) {
		Point2D result = (Point2D) bottomLeft.clone();
		if(at != null) at.transform(bottomLeft, result);
		return result;
	}

	public Point2D getTopRight(AffineTransform at) {
		Point2D result = (Point2D) topRight.clone();
		if(at != null) at.transform(topRight, result);
		return result;
	}

	public Point2D getBottomRight(AffineTransform at) {
		Point2D result = (Point2D) bottomRight.clone();
		if(at != null) at.transform(bottomRight, result);
		return result;
	}

	public Padding padding;
	private Rectangle2D.Float textBoxRect;


	public Rectangle2D.Float getTextBoxRect() {
		return textBoxRect;
	}

	public Rectangle2D getTransformedTextBoxRect(AffineTransform at) {
		Rectangle2D bounds = at.createTransformedShape(textBoxRect).getBounds2D();
		return bounds;
	}

	public Element xmlElement;

	public TextBoxFormat() {
		textBoxRect = new Rectangle2D.Float();
	}

	public void setLocation() {
		for(WordFormat wf: sourceTexts) {
			wf.resetLineNumber();
		}

		textBoxRect = new Rectangle2D.Float();
		Point2D.Float p = new Point2D.Float();
		float currentWidth = 0;
		float lineAscent = 0;
		WordFormat target;

		for(int i = 0; i < sourceTexts.size(); i++) {
			target = sourceTexts.get(i);

			currentWidth += target.getWidth();
			target.location = (Point2D.Float)p.clone();
			p.x += target.getWidth();
			lineAscent = Math.max(lineAscent, target.getMaxAscent());

			if(i != sourceTexts.size() - 1) {
				if(width - padding.getHorizontal() < currentWidth + sourceTexts.get(i + 1).getWidth()) {
					int index = i+1;
					while(index > 0) {
						if(sourceTexts.get(index).isStartProhibited || sourceTexts.get(index-1).isEndProhibited) {
							index -= 1;
						} else {
							break;
						}
					}
					for(int j = index; j < sourceTexts.size();j++) {
						sourceTexts.get(j).setLineNumber(sourceTexts.get(j).getLineNumber() + 1);
					}
				}
				if(target.getLineNumber() != sourceTexts.get(i + 1).getLineNumber()) {
					p.y += lineAscent;
					p.x = 0;
					lineAscent = 0;
					currentWidth = 0;
				}
			}
		}

		for(int i = 0; i < sourceTexts.size(); i++) {
			target = sourceTexts.get(i);
			Rectangle2D.Float.union(textBoxRect, target.getBounds(), textBoxRect);
		}


		float offsetX, offsetY;
		offsetX = (float) (boxLocation.getX() + padding.left - textBoxRect.x);
		offsetY = (float) (boxLocation.getY() + padding.top - textBoxRect.y);
		for(WordFormat wordFormat: sourceTexts) {
			wordFormat.location.x += offsetX;
			wordFormat.location.y += offsetY;
		}

		textBoxRect.x = (float) boxLocation.getX();
		textBoxRect.y = (float) boxLocation.getY();
		textBoxRect.width += padding.getHorizontal();
		textBoxRect.height += padding.getVartical();

		topLeft = new Point2D.Double(textBoxRect.x, textBoxRect.y);
		bottomLeft = new Point2D.Double(textBoxRect.x, textBoxRect.y + textBoxRect.height);
		topRight = new Point2D.Double(textBoxRect.x + textBoxRect.width, textBoxRect.y);
		bottomRight = new Point2D.Double(textBoxRect.x + textBoxRect.width, textBoxRect.y + textBoxRect.height);
	}

	public void setPreferredWidth() {
		float preferredWidth = 0;
		float currentWidth = 0;
		WordFormat target;
		for(int i = 0; i < sourceTexts.size(); i++) {
			target = sourceTexts.get(i);
			currentWidth += target.getWidth();
			if(i != sourceTexts.size() - 1) {
				if(target.getLineNumber() != sourceTexts.get(i + 1).getLineNumber()) {
					preferredWidth = Math.max(currentWidth, preferredWidth);
					currentWidth = 0;
				}
			}
		}

		preferredWidth = Math.max(currentWidth, preferredWidth);
		preferredWidth += padding.getHorizontal();

		this.width = preferredWidth;;
	}

	public float getMinimumWidth() {
		float minimumWidth = 0;
		WordFormat target;
		float  currentWidth = 0;
		boolean isEndProhibited = false;

		for(int i = 1; i < sourceTexts.size(); i++) {
			target = sourceTexts.get(i);

			if(target.isStartProhibited || isEndProhibited)
				currentWidth += target.getWidth();
			else
				currentWidth = target.getWidth();

			minimumWidth = Math.max(currentWidth, minimumWidth);

			isEndProhibited = target.isEndProhibited;
		}

		minimumWidth += padding.getHorizontal();
		return minimumWidth;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return (float) (bottomLeft.getY() - topLeft.getY());
	}

	public float getActualWidth() {
		if(textBoxRect != null)
			return textBoxRect.width;
		else
			return Float.NaN;
	}


	public void setWidth(float width) {
		if(!Float.isNaN(width)) {
			if(width > getMinimumWidth())
				this.width = width;
			else
				this.width = getMinimumWidth();
		} else
			this.width = Float.NaN;
	}
}