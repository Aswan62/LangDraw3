package format;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import org.jdom.Element;

import enums.Enums.BorderForm;
import enums.LDAttributes;


public class BorderSetting implements Cloneable {
	private boolean drawBorder, fillBackground;
	public boolean isDrawBorder() {
		return drawBorder;
	}

	public boolean isFillBackground() {
		return fillBackground;
	}

	private ArrayList<WordFormat> qualifiedTargets;
	private ArrayList<BorderSetting> innerBorder;
	private float lineWidth;
	private Color borderColor, backgroundColor;
	private BorderForm leftForm, rightForm;
	private GeneralPath borderPath;
	private GeneralPath fillPath;

	public BorderSetting() {
		this.drawBorder = false;
		this.lineWidth = 0;
		this.borderColor = null;
		this.fillBackground = false;
		this.backgroundColor = null;
		qualifiedTargets = new ArrayList<WordFormat>();
		innerBorder = new ArrayList<BorderSetting>();
	}

	public void setQualifiedTargets(WordFormat target) {
		this.qualifiedTargets.add(target);
	}

	public void setLeftBorderOpened() {
		leftForm = BorderForm.NONE;
	}

	public void setRightBorderOpened() {
		rightForm = BorderForm.NONE;
	}

	public void addInnerBorder(BorderSetting border) {
		innerBorder.add(border);
	}

	@Override
	public BorderSetting clone() {
		try {
			BorderSetting result = (BorderSetting)super.clone();
			result.qualifiedTargets = new ArrayList<WordFormat>();
			result.innerBorder = new ArrayList<BorderSetting>();
			return result;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}


	public void setWidth(float width){
		this.lineWidth = width;
	}


	public float getLineWidth(){
		if(drawBorder) return lineWidth;
		else return 0;
	}

	public float getWidthLeftSide(){
		if(drawBorder) {
			if(leftForm != BorderForm.NONE) return getLineWidth();
			else return 0;
		}
		else return 0;
	}

	public float getWidthRightSide(){
		if(drawBorder) {
			if(rightForm != BorderForm.NONE) return getLineWidth();
			else return 0;
		}
		else return 0;
	}

	public float getHorizontal(){
		return getLineWidth() * 2;
	}

	public float getVartical(){
		return getLineWidth() * 2;
	}

	@Override
	public String toString() {
		return Float.toString(lineWidth);
	}

	public Shape getBorderRound() {
		return borderPath;
	}

	public Shape getBorderFillShape() {
		return new Area(fillPath);
	}

	public Rectangle2D.Float getBorderBounds() {
		float x, y, width, height;
		y = (float) (borderPath.getBounds2D().getY() - getLineWidth()/2);
		if(leftForm == BorderForm.NONE && rightForm == BorderForm.NONE) {
			x = (float) borderPath.getBounds2D().getX();
			y = (float) (borderPath.getBounds2D().getY() - getLineWidth()/2);
			width = (float) borderPath.getBounds2D().getWidth();
			height = (float) (borderPath.getBounds2D().getHeight() + getLineWidth());
		}
		else if(leftForm == BorderForm.NONE) {
			x = (float) borderPath.getBounds2D().getX();
			y = (float) (borderPath.getBounds2D().getY() - getLineWidth()/2);
			width = (float) (borderPath.getBounds2D().getWidth() + getLineWidth()/2);
			height = (float) (borderPath.getBounds2D().getHeight() + getLineWidth());
		}
		else if(rightForm == BorderForm.NONE) {
			x = (float) (borderPath.getBounds2D().getX() - getLineWidth()/2);
			y = (float) (borderPath.getBounds2D().getY() - getLineWidth()/2);
			width = (float) (borderPath.getBounds2D().getWidth() + getLineWidth()/2);
			height = (float) (borderPath.getBounds2D().getHeight() + getLineWidth());
		}
		else {
			x = (float) (borderPath.getBounds2D().getX() - getLineWidth()/2);
			y = (float) (borderPath.getBounds2D().getY() - getLineWidth()/2);
			width = (float) (borderPath.getBounds2D().getWidth() + getLineWidth());
			height = (float) (borderPath.getBounds2D().getHeight() + getLineWidth());
		}

		return new Rectangle2D.Float(x, y, width, height);
	}

	public BorderSetting setBorderArea() {
		if(qualifiedTargets.size() > 0) {
			Rectangle2D.Float bounds = qualifiedTargets.get(0).getBounds();
			for(WordFormat target: qualifiedTargets) {
				Rectangle2D.union(bounds, target.getBounds(), bounds);
			}

			for(BorderSetting border: innerBorder) {
				bounds.width += border.getHorizontal() * 3 / 2;
				bounds.height += border.getVartical() * 3 / 2;
				bounds.x -=  border.getLineWidth() + border.getLineWidth() / 2;
				bounds.y -=  border.getLineWidth() + border.getLineWidth() / 2;
			}

			setArea(bounds.x - getLineWidth()/2, bounds.y - getLineWidth()/2, bounds.width, bounds.height);

			return this;
		} else {
			return null;
		}
	}

	public void setMarkBorderArea(MarkFormat mf) {
		setArea(mf.getLocation().x - getLineWidth()/2, mf.getLocation().y - mf.getMaxAscent() - getLineWidth()/2, mf.getWidth(), mf.getHeight());
	}

	public void setTextBoxBorderArea(TextBoxFormat tf) {
		setArea((float)tf.getTopLeft(null).getX()  - getLineWidth()/2, (float)tf.getTopLeft(null).getY()  - getLineWidth()/2, tf.getWidth(), tf.getHeight());
	}

	private void setArea(float locationX, float locationY, float width, float height) {
		Point2D.Float topLeft = new Point2D.Float(locationX, locationY);
		Point2D.Float topRight = new Point2D.Float(locationX + width + getLineWidth() , topLeft.y);
		Point2D.Float bottomLeft = new Point2D.Float(topLeft.x , locationY + height + this.getLineWidth());
		Point2D.Float bottomRight = new Point2D.Float(topRight.x, bottomLeft.y);

		if(isDrawBorder() || isFillBackground()) {
			BorderShape leftFormShape = null, rightFormShape = null;
			borderPath = new GeneralPath();
			fillPath = borderPath;

			switch(leftForm) {
				case LINEAR :
					leftFormShape = new RectForm(topLeft, bottomLeft,  BorderSide.Left, height);
					borderPath.moveTo(leftFormShape.getTop().x, leftFormShape.getTop().y);
					borderPath.lineTo(leftFormShape.getBottom().x, leftFormShape.getBottom().y);
					break;
				case CURVE1:
					leftFormShape = new Curve1Form(topLeft, bottomLeft, BorderSide.Left, height, width);
					borderPath.moveTo(leftFormShape.getTop().x, leftFormShape.getTop().y);
					borderPath.quadTo(leftFormShape.getControlTop().x, leftFormShape.getControlTop().y, leftFormShape.getMiddlleTop().x, leftFormShape.getMiddlleTop().y);
					borderPath.lineTo(leftFormShape.getMiddlleBottom().x, leftFormShape.getMiddlleBottom().y);
					borderPath.quadTo(leftFormShape.getControlBottom().x, leftFormShape.getControlBottom().y, leftFormShape.getBottom().x, leftFormShape.getBottom().y);
					break;
				case CURVE2:
					leftFormShape = new Curve2Form(topLeft, bottomLeft, BorderSide.Left, height, width);
					borderPath.moveTo(leftFormShape.getTop().x, leftFormShape.getTop().y);
					borderPath.quadTo(leftFormShape.getControlTop().x, leftFormShape.getControlTop().y, leftFormShape.getMiddlleTop().x, leftFormShape.getMiddlleTop().y);
					borderPath.quadTo(leftFormShape.getControlBottom().x, leftFormShape.getControlBottom().y, leftFormShape.getBottom().x, leftFormShape.getBottom().y);
					break;
				case TURNED:
					leftFormShape = new TurnedForm(topLeft, bottomLeft, BorderSide.Left, height, width);
					borderPath.moveTo(leftFormShape.getTop().x, leftFormShape.getTop().y);
					borderPath.lineTo(leftFormShape.getMiddlleTop().x, leftFormShape.getMiddlleTop().y);
					borderPath.lineTo(leftFormShape.getBottom().x, leftFormShape.getBottom().y);
					break;
				case NONE:
					leftFormShape = new OpenedForm(topLeft, bottomLeft, BorderSide.Left, height, width);
					borderPath.moveTo(leftFormShape.getBottom().x, leftFormShape.getBottom().y);
					break;
			}

			switch(rightForm) {
				case LINEAR :
					rightFormShape = new RectForm(topRight, bottomRight, BorderSide.Right, height);
					borderPath.lineTo(rightFormShape.getBottom().x, rightFormShape.getBottom().y);
					borderPath.lineTo(rightFormShape.getTop().x, rightFormShape.getTop().y);
					break;
				case CURVE1 :
					rightFormShape = new Curve1Form(topRight, bottomRight, BorderSide.Right, height, width);
					borderPath.lineTo(rightFormShape.getBottom().x, rightFormShape.getBottom().y);
					borderPath.quadTo(rightFormShape.getControlBottom().x, rightFormShape.getControlBottom().y, rightFormShape.getMiddlleBottom().x, rightFormShape.getMiddlleBottom().y);
					borderPath.lineTo(rightFormShape.getMiddlleTop().x, rightFormShape.getMiddlleTop().y);
					borderPath.quadTo(rightFormShape.getControlTop().x, rightFormShape.getControlTop().y,  rightFormShape.getTop().x, rightFormShape.getTop().y);
					break;
				case CURVE2:
					rightFormShape = new Curve2Form(topRight, bottomRight,  BorderSide.Right, height, width);
					borderPath.lineTo(rightFormShape.getBottom().x, rightFormShape.getBottom().y);
					borderPath.quadTo(rightFormShape.getControlBottom().x, rightFormShape.getControlBottom().y, rightFormShape.getMiddlleBottom().x, rightFormShape.getMiddlleBottom().y);
					borderPath.quadTo(rightFormShape.getControlTop().x, rightFormShape.getControlTop().y, rightFormShape.getTop().x, rightFormShape.getTop().y);
					break;
				case TURNED:
					rightFormShape = new TurnedForm(topRight, bottomRight, BorderSide.Right, height, width);
					borderPath.lineTo(rightFormShape.getBottom().x, rightFormShape.getBottom().y);
					borderPath.lineTo(rightFormShape.getMiddlleTop().x, rightFormShape.getMiddlleTop().y);
					borderPath.lineTo(rightFormShape.getTop().x, rightFormShape.getTop().y);
					break;
				case NONE:
					rightFormShape = new OpenedForm(topRight, bottomRight, BorderSide.Right, height, width);
					borderPath.lineTo(rightFormShape.getBottom().x, rightFormShape.getBottom().y);
					borderPath.moveTo(rightFormShape.getTop().x, rightFormShape.getTop().y);
					break;
			}
			borderPath.lineTo(leftFormShape.getTop().x, leftFormShape.getTop().y);

			fillPath = (GeneralPath) borderPath.clone();
			fillPath.lineTo(leftFormShape.getBottom().x, leftFormShape.getBottom().y);
			fillPath.lineTo(rightFormShape.getBottom().x, rightFormShape.getBottom().y);
			fillPath.lineTo(rightFormShape.getTop().x, rightFormShape.getTop().y);

		} else {
			Rectangle2D rect = new Rectangle2D.Double(topLeft.x, topLeft.y, topRight.x - topLeft.x, bottomLeft.y - topLeft.y);
			borderPath = new GeneralPath(rect);
		}
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setDrawBorder(Boolean drawBorder) {
		this.drawBorder = drawBorder;
	}

	public void setFillBackground(Boolean fillBackground) {
		this.fillBackground = fillBackground;
	}

	public void setBackgroundColor(Color color) {
		this.backgroundColor = color;
	}

	public void setBorderColor(Color color) {
		this.borderColor = color;
	}

	public void setBorderLeftForm(int selectedIndex) {
		switch(selectedIndex) {
			case 0:
				this.leftForm = BorderForm.LINEAR;
				break;
			case 1:
				this.leftForm = BorderForm.CURVE1;
				break;
			case 2:
				this.leftForm = BorderForm.CURVE2;
				break;
			case 3:
				this.leftForm = BorderForm.TURNED;
				break;
			case 4:
				this.leftForm = BorderForm.NONE;
				break;
		}
	}

	public void setBorderLeftForm(BorderForm form) {
		this.leftForm = form;
	}

	public BorderForm getBorderLeftForm() {
		return this.leftForm;
	}

	public int getBorderLeftFormIndex() {
		switch(this.leftForm) {
		case LINEAR:
			return 0;
		case CURVE1:
			return 1;
		case CURVE2:
			return 2;
		case TURNED:
			return 3;
		case NONE:
			return 4;
		}
		return 0;
	}

	public void setBorderRightForm(int selectedIndex) {
		switch(selectedIndex) {
			case 0:
				this.rightForm = BorderForm.LINEAR;
				break;
			case 1:
				this.rightForm = BorderForm.CURVE1;
				break;
			case 2:
				this.rightForm = BorderForm.CURVE2;
				break;
			case 3:
				this.rightForm = BorderForm.TURNED;
				break;
			case 4:
				this.rightForm = BorderForm.NONE;
				break;
		}
	}

	public void setBorderRightForm(BorderForm form) {
		this.rightForm = form;
	}


	public BorderForm getBorderRightForm() {
		return this.rightForm;
	}

	public int getBorderRightFormIndex() {
		switch(this.rightForm) {
		case LINEAR:
			return 0;
		case CURVE1:
			return 1;
		case CURVE2:
			return 2;
		case TURNED:
			return 3;
		case NONE:
			return 4;
		}
		return 0;
	}

	public Element setAttribute(Element newElement) {

		if(fillBackground) {
			 newElement.setAttribute(LDAttributes.BackgroundColor, convertFromColorToString(backgroundColor));
			 newElement.setAttribute(LDAttributes.BackgroundColorOpacity, getOpacityToString(backgroundColor));
		} else {
			newElement.removeAttribute(LDAttributes.BackgroundColor);
			newElement.removeAttribute(LDAttributes.BackgroundColorOpacity);
		}

		if(drawBorder) {
			newElement.setAttribute(LDAttributes.BorderColor, convertFromColorToString(borderColor));
			newElement.setAttribute(LDAttributes.BorderColorOpacity, getOpacityToString(borderColor));
			newElement.setAttribute(LDAttributes.BorderWidth, Float.toString(lineWidth));
			newElement.setAttribute(LDAttributes.BorderLeftForm, leftForm.toString());
			newElement.setAttribute(LDAttributes.BorderRightForm, rightForm.toString());
		} else {
			newElement.removeAttribute(LDAttributes.BorderColor);
			newElement.removeAttribute(LDAttributes.BorderColorOpacity);
			newElement.removeAttribute(LDAttributes.BorderWidth);
			newElement.removeAttribute(LDAttributes.BorderLeftForm);
			newElement.removeAttribute(LDAttributes.BorderRightForm);
		}

		return newElement;
	}

	private static String convertFromColorToString(Color color) {
		String result = "";
		if(color != null) result = "#" + Integer.toHexString(color.getRGB() & 0x00ffffff).toUpperCase();
		return result;
	}

	private static String getOpacityToString(Color color) {
		if(color != null) return Float.toString((float)Math.round((float)color.getAlpha() / 255 * 10) / 10);
		else return "0";
	}

	class RectForm extends BorderShape {
		public RectForm(Point2D.Float top, Point2D.Float bottom, BorderSide side, float height){
			this.top =  new Point2D.Float(top.x, top.y);
			this.bottom = new Point2D.Float(bottom.x, bottom.y);
		}
	}

	class Curve1Form extends BorderShape {
		public Curve1Form(Point2D.Float top, Point2D.Float bottom, BorderSide side, float height, float width){
			float depth = height/2;
			if(depth > width/2) depth = width/2;
			this.borderSide = side;
			if(borderSide == BorderSide.Left) {
				this.top =  new Point2D.Float(top.x + depth, top.y);
				this.bottom = new Point2D.Float(bottom.x + depth, bottom.y);
			} else {
				this.top =  new Point2D.Float(top.x - depth, top.y);
				this.bottom = new Point2D.Float(bottom.x - depth, bottom.y);
			}
			this.controlTop = new Point2D.Float(top.x, top.y);
			this.middlleTop = new Point2D.Float(top.x, top.y + height/3);

			this.controlBottom = new Point2D.Float(bottom.x, bottom.y);
			this.middlleBottom = new Point2D.Float(bottom.x, bottom.y - depth);
		}
	}

	class Curve2Form extends BorderShape {
		public Curve2Form(Point2D.Float top, Point2D.Float bottom, BorderSide side, float height, float width){
			float depth = height;
			if(depth > width/2) depth = width/2;
			this.borderSide = side;
			if(borderSide == BorderSide.Left) {
				this.top = new Point2D.Float(top.x + depth, top.y);
				this.bottom = new Point2D.Float(bottom.x + depth, bottom.y);
			} else {
				this.top = new Point2D.Float(top.x - depth, top.y);
				this.bottom = new Point2D.Float(bottom.x - depth, bottom.y);
			}
			this.controlTop = new Point2D.Float(top.x, top.y);
			this.middlleTop = new Point2D.Float(top.x, (top.y + bottom.y)/2);
			this.middlleBottom = middlleTop;
			this.controlBottom = new Point2D.Float(bottom.x, bottom.y);
		}
	}

	class TurnedForm extends BorderShape {
		public TurnedForm(Point2D.Float top, Point2D.Float bottom, BorderSide side, float height, float width){
			float depth = height/6;
			if(depth > width/2) depth = width/2;
			this.borderSide = side;
			if(borderSide == BorderSide.Left) {
				this.top = new Point2D.Float(top.x + depth, top.y);
				this.bottom = new Point2D.Float(bottom.x + depth, bottom.y);
			} else {
				this.top = new Point2D.Float(top.x - depth, top.y);
				this.bottom = new Point2D.Float(bottom.x - depth, bottom.y);
			}

			this.middlleTop = new Point2D.Float(top.x, (top.y + bottom.y)/2);
			this.middlleBottom = middlleTop;
			this.borderSide = side;
		}
	}

	class OpenedForm extends BorderShape {
		public OpenedForm(Point2D.Float top, Point2D.Float bottom, BorderSide side, float height, float width){
			float depth = height/4;
			if(depth > width/2) depth = width/2;

			this.top = top;
			this.middlleTop = top;

			this.bottom = bottom;
			this.middlleBottom = bottom;

			this.borderSide = side;
		}
	}

	abstract class BorderShape {
		Point2D.Float top, bottom, controlTop, middlleTop, middlleBottom, controlBottom;
		BorderSide borderSide;

		public Point2D.Float getTop() {
			return top;
		}

		public Point2D.Float getBottom() {
			return bottom;
		}

		public Point2D.Float getControlTop() {
			return controlTop;
		}

		public Point2D.Float getMiddlleTop() {
			return middlleTop;
		}

		public Point2D.Float getMiddlleBottom() {
			return middlleBottom;
		}

		public Point2D.Float getControlBottom() {
			return controlBottom;
		}

		public BorderSide getBorderSide() {
			return borderSide;
		}
	}
}

enum BorderSide {Left, Right};
