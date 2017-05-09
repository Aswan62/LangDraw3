package render;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import mainTextPaser.TextBoxPaser;

import shapes.ConfirmButton;
import shapes.ConfirmText;
import shapes.DeleteButton;
import shapes.DeleteText;
import shapes.KnobShapes;
import textEditor.TextEditor;
import enums.Enums.TextDecoration;
import enums.LDAttributes;
import enums.LDXMLTags;
import enums.Enums.Direction;
import format.TextBoxFormat;
import format.WordFormat;

public class TextBoxView {
	private Element sourceElement;
	private TextBoxFormat textBoxFormat;
	private KnobShapes knobsLeft, knobsRight;
	private Rectangle2D bounds;
	private ConfirmText confirmText;
	private ConfirmButton confirmButton;
	private ImageCanvas canvas;
	private Point origin;
	private EventSourceTextBox eventSource;
	private float newWidth;
	private boolean isReplace;
	private Element oldElement;
	private DeleteText deleteText;
	private DeleteButton deleteButton;
	private boolean isDelete;

	public TextBoxView(ImageCanvas canvas, String text, String styleID) {
		this.canvas = canvas;
		try {
			text = "<LD><" + LDXMLTags.TextBox + ">" + text + "</" + LDXMLTags.TextBox + ">" + "></LD>";
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(new ByteArrayInputStream(text.getBytes("UTF-8")));
			sourceElement = doc.getRootElement().getChild(LDXMLTags.TextBox);
		} catch (Exception e) {
			sourceElement = null;
			return;
		}
		
		List contents = sourceElement.cloneContent();
		sourceElement = new Element(LDXMLTags.TextBox);
		sourceElement.addContent(contents);
		
		if(styleID != null)
			sourceElement.setAttribute(LDAttributes.ID, styleID);
		
		eventSource = EventSourceTextBox.None;

		isDelete = false;
		isReplace = false;
	}

	public TextBoxView(ImageCanvas canvas, TextBoxFormat format, AffineTransform scaleAt) {
		this.canvas = canvas;
		this.textBoxFormat = format;
		Point2D location = format.getBoxLocation(null);
		this.textBoxFormat.setBoxLocation(location);
		this.textBoxFormat.setLocation();
		this.oldElement = (Element) textBoxFormat.xmlElement;
		this.sourceElement = (Element) textBoxFormat.xmlElement.clone();

		knobsLeft = new KnobShapes(textBoxFormat.getTopLeft(scaleAt), textBoxFormat.getBottomLeft(scaleAt), Direction.Vertical);
		knobsRight = new KnobShapes(textBoxFormat.getTopRight(scaleAt), textBoxFormat.getBottomRight(scaleAt), Direction.Vertical);
		bounds = (Rectangle2D) textBoxFormat.getTransformedTextBoxRect(scaleAt).clone();
		Rectangle2D.union(bounds, knobsLeft.getBounds2D(), bounds);
		Rectangle2D.union(bounds, knobsRight.getBounds2D(), bounds);
		confirmText  = new ConfirmText(bounds);
		confirmButton = new ConfirmButton(bounds, confirmText.getBounds2D());
		deleteText = new DeleteText(bounds);
		deleteButton = new DeleteButton(bounds, deleteText.getBounds2D());

		eventSource = EventSourceTextBox.None;

		isDelete = false;
		isReplace = true;
	}

	public void setPosition(Point location, AffineTransform scaleAt) {
		try {
			scaleAt.inverseTransform(location, location);
		} catch (NoninvertibleTransformException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		sourceElement.setAttribute(LDAttributes.Position, Float.toString(location.x).concat(",").concat(Float.toString(location.y)));

		textBoxFormat = new TextBoxFormat();
		try {
			XMLOutputter xmlOutputter = new XMLOutputter(Format.getRawFormat());
			StringWriter writer = new StringWriter();
			xmlOutputter.output(sourceElement, writer);
			new TextBoxPaser(writer.toString(), textBoxFormat);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		textBoxFormat.setLocation();

		knobsLeft = new KnobShapes(textBoxFormat.getTopLeft(scaleAt), textBoxFormat.getBottomLeft(scaleAt), Direction.Vertical);
		knobsRight = new KnobShapes(textBoxFormat.getTopRight(scaleAt), textBoxFormat.getBottomRight(scaleAt), Direction.Vertical);
		bounds = (Rectangle2D) textBoxFormat.getTransformedTextBoxRect(scaleAt).clone();
		Rectangle2D.union(bounds, knobsLeft.getBounds2D(), bounds);
		Rectangle2D.union(bounds, knobsRight.getBounds2D(), bounds);
		confirmText  = new ConfirmText(bounds);
		confirmButton = new ConfirmButton(bounds, confirmText.getBounds2D());
		deleteText = new DeleteText(bounds);
		deleteButton = new DeleteButton(bounds, deleteText.getBounds2D());
	}

	public Rectangle2D draw(Graphics2D g2, Rectangle2D bounds, AffineTransform at) {
		g2.transform(at);
		if(textBoxFormat.textBoxBorder.isFillBackground()) {
			g2.setColor(textBoxFormat.textBoxBorder.getBackgroundColor());
			g2.fill(textBoxFormat.getTextBoxRect());
		}

		for(WordFormat wordFormat: textBoxFormat.sourceTexts) {
			g2.setColor(wordFormat.getFontColor());
			g2.fill(wordFormat.getTextShape());

			g2.setColor(wordFormat.getDecorationColor());
			if(wordFormat.getDecoration() == TextDecoration.OVERLINE) {
				g2.setStroke(new BasicStroke(wordFormat.getOverlineWidth(),BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER));
				g2.draw(wordFormat.getOverline());
			}
			else if(wordFormat.getDecoration() == TextDecoration.STRIKE) {
				g2.setStroke(new BasicStroke(wordFormat.getStrikeWidth(),BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER));
				g2.draw(wordFormat.getStrikeline());
			}
			else if(wordFormat.getDecoration() == TextDecoration.UNDERLINE) {
				g2.setStroke(new BasicStroke(wordFormat.getUnderlineWidth(),BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER));
				g2.draw(wordFormat.getUnderline());
			}
		}

		if(textBoxFormat.textBoxBorder.getLineWidth() > 0) {
			g2.setColor(textBoxFormat.textBoxBorder.getBorderColor());
			g2.setStroke(new BasicStroke(textBoxFormat.textBoxBorder.getLineWidth()));
			g2.draw(textBoxFormat.getTextBoxRect());
		}

		try {
			g2.transform(at.createInverse());
		} catch (NoninvertibleTransformException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		g2.setColor(Color.BLUE);
		g2.setStroke(new BasicStroke(1));
		g2.draw(knobsLeft);
		g2.draw(knobsRight);
		g2.fill(confirmText);
		g2.draw(confirmButton);
		g2.fill(deleteText);
		g2.draw(deleteButton);

		Rectangle2D.union(bounds, knobsLeft.getBounds2D(), bounds);
		Rectangle2D.union(bounds, knobsRight.getBounds2D(), bounds);
		Rectangle2D.union(bounds, confirmButton.getBounds2D(), bounds);
		Rectangle2D.union(bounds, deleteButton.getBounds2D(), bounds);

		return bounds;
	}

	public void hover(Point p) {
		if(knobsLeft.contains(p)) {
			canvas.changeCursor(Cursor.W_RESIZE_CURSOR);
		}
		else if(knobsRight.contains(p)) {
			canvas.changeCursor(Cursor.W_RESIZE_CURSOR);
		}
		else if(confirmButton.contains(p) || confirmText.contains(p)) {
			canvas.changeCursor(Cursor.HAND_CURSOR);
		}
		else if(deleteButton.contains(p) || deleteButton.contains(p)) {
			canvas.changeCursor(Cursor.HAND_CURSOR);
		}
		else if(textBoxFormat.getTextBoxRect().contains(p)) {
			canvas.changeCursor(Cursor.MOVE_CURSOR);
		} else {
			canvas.changeCursor(Cursor.DEFAULT_CURSOR);
		}
	}

	public void select(Point p, AffineTransform transform) {
		if(knobsLeft.contains(p)){
			origin = (Point) p.clone();
			eventSource = EventSourceTextBox.LeftKnobs;
			newWidth = textBoxFormat.getActualWidth();
		}
		else if(knobsRight.contains(p)){
			origin = (Point) p.clone();
			eventSource = EventSourceTextBox.RightKnobs;
			newWidth = textBoxFormat.getActualWidth();
		}
		else if(textBoxFormat.getTransformedTextBoxRect(transform).contains(p)){
			origin = (Point) p.clone();
			eventSource =  EventSourceTextBox.Position;
		}
		else if(confirmButton.contains(p) || confirmText.contains(p)){
			origin = null;
			eventSource = EventSourceTextBox.Confirm;
		}
		else if(deleteButton.contains(p) || deleteText.contains(p)){
			origin = null;
			eventSource = EventSourceTextBox.Delete;
			isDelete = true;
		}
		else {
			origin = null;
			eventSource = EventSourceTextBox.None;
		}
	}

	public void move(Point p, AffineTransform transform) {
		if(origin != null) {
			if(eventSource == EventSourceTextBox.LeftKnobs) {
				Rectangle2D oldRect = (Rectangle2D) textBoxFormat.getTextBoxRect().clone();
				newWidth -= (p.x - origin.x);
				textBoxFormat.setWidth(newWidth);
				textBoxFormat.setLocation();
				textBoxFormat.setBoxLocation(new Point2D.Double(oldRect.getMaxX() - textBoxFormat.getActualWidth(), textBoxFormat.getBoxLocation(null).getY()));
				textBoxFormat.setLocation();
			}
			else if(eventSource == EventSourceTextBox.RightKnobs) {
				newWidth += (p.x - origin.x);
				textBoxFormat.setWidth(newWidth);
				textBoxFormat.setLocation();
			}
			else if(eventSource == EventSourceTextBox.Position) {
				textBoxFormat.setBoxLocation(new Point2D.Double(textBoxFormat.getBoxLocation(null).getX() + p.x - origin.x, textBoxFormat.getBoxLocation(null).getY() + p.y - origin.y));
				textBoxFormat.setLocation();
			}

			knobsLeft.setPoints(textBoxFormat.getTopLeft(transform), textBoxFormat.getBottomLeft(transform), Direction.Vertical);
			knobsRight.setPoints(textBoxFormat.getTopRight(transform), textBoxFormat.getBottomRight(transform), Direction.Vertical);
			bounds = (Rectangle2D) textBoxFormat.getTransformedTextBoxRect(transform).clone();
			Rectangle2D.union(bounds, knobsLeft.getBounds2D(), bounds);
			Rectangle2D.union(bounds, knobsRight.getBounds2D(), bounds);
			confirmText = new ConfirmText(bounds);
			confirmButton = new ConfirmButton(bounds, confirmText.getBounds2D());
			deleteText = new DeleteText(bounds);
			deleteButton = new DeleteButton(bounds, deleteText.getBounds2D());

			origin = (Point) p.clone();
		}
	}

	public boolean isMouseSelected() {
		if(eventSource == EventSourceTextBox.LeftKnobs || eventSource == EventSourceTextBox.RightKnobs || eventSource == EventSourceTextBox.Position)
			return true;
		else
			return false;
	}

	public boolean isLayoutConfirmed() {
		if(eventSource == EventSourceTextBox.Confirm || eventSource == EventSourceTextBox.Delete)
			return true;
		else
			return false;
	}

	public void setTextBoxElement() {
		if(isDelete) {
			if(isReplace) {
				TextEditor.replaceElement(oldElement, null);
			} else {
				return;
			}
		}

		String x = Float.toString((float) (textBoxFormat.getBoxLocation(null).getX()));
		String y = Float.toString((float) (textBoxFormat.getBoxLocation(null).getY()));
		String width = Float.toString((float) textBoxFormat.getWidth());

		sourceElement.setAttribute(LDAttributes.Position, x.concat(",").concat(y));
		sourceElement.setAttribute(LDAttributes.Width, width);

		if(!isReplace)
			TextEditor.setTextBoxValue(sourceElement);
		else
			TextEditor.replaceElement(oldElement, sourceElement);
	}

	public Rectangle2D getBounds(AffineTransform at) {
		bounds = (Rectangle2D) textBoxFormat.getTransformedTextBoxRect(at).clone();
		Rectangle2D.union(bounds, knobsLeft.getBounds2D(), bounds);
		Rectangle2D.union(bounds, knobsRight.getBounds2D(), bounds);
		Rectangle2D.union(bounds, confirmText.getBounds2D(), bounds);
		Rectangle2D.union(bounds, confirmButton.getBounds2D(), bounds);
		return bounds;
	}

	public void translate(int dx, int dy, AffineTransform at) {
		textBoxFormat.setBoxLocation(new Point2D.Double(textBoxFormat.getBoxLocation(null).getX() + dx, textBoxFormat.getBoxLocation(null).getY() + dy));
		textBoxFormat.setLocation();

		knobsLeft.setPoints(textBoxFormat.getTopLeft(at), textBoxFormat.getBottomLeft(at), Direction.Vertical);
		knobsRight.setPoints(textBoxFormat.getTopRight(at), textBoxFormat.getBottomRight(at), Direction.Vertical);
		bounds = (Rectangle2D) textBoxFormat.getTransformedTextBoxRect(at).clone();
		Rectangle2D.union(bounds, knobsLeft.getBounds2D(), bounds);
		Rectangle2D.union(bounds, knobsRight.getBounds2D(), bounds);
		confirmText = new ConfirmText(bounds);
		confirmButton = new ConfirmButton(bounds, confirmText.getBounds2D());
		deleteText = new DeleteText(bounds);
		deleteButton = new DeleteButton(bounds, deleteText.getBounds2D());
	}
}

enum EventSourceTextBox { None, LeftKnobs, RightKnobs, Confirm, Position, Delete }
