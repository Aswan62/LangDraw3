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

import org.jdom.Element;

import shapes.ArrowShape;
import shapes.ConfirmButton;
import shapes.ConfirmText;
import shapes.DeleteButton;
import shapes.DeleteText;
import shapes.HeadShape;
import shapes.KnobShapes;
import shapes.LocationEclipse;
import style.ArrowStyle;
import textEditor.TextEditor;

import enums.Enums.ArrowHeadType;
import enums.Enums.Direction;
import enums.LDAttributes;
import enums.LDXMLTags;
import format.ArrowFormat;


public class ArrowView {
	private ArrowShape arrow;
	private HeadShape startHead, endHead;
	private Point origin;
	private LocationEclipse startEclipse, endEclipse;
	private EventSourceArrow eventSource;
	private KnobShapes depthKnob;
	private ConfirmButton confirmButton;
	private ConfirmText confirmText;
	private ArrowStyle arrowStyle;
	private Rectangle2D bounds;
	private boolean isReplace;
	private boolean isDelete;
	private Element oldElement;
	private DeleteText deleteText;
	private DeleteButton deleteButton;

	public boolean isMouseSelected() {
		if(eventSource == EventSourceArrow.Start || eventSource == EventSourceArrow.End || eventSource == EventSourceArrow.Knobs || eventSource == EventSourceArrow.Position)
			return true;
		else
			return false;
	}

	public boolean isLayoutConfirmed() {
		if(eventSource == EventSourceArrow.Confirm || eventSource == EventSourceArrow.Delete)
			return true;
		else
			return false;
	}

	public ArrowView(ArrowStyle style){
		if(style == null) style = new ArrowStyle();
		this.arrowStyle = style;
	}

	public void setArrow(Point start, Point end, float depth, Direction arrowDirection, AffineTransform scaleAt) {
		arrow = new ArrowShape(start, end, (float) (arrowStyle.radius * scaleAt.getScaleX()), depth, arrowDirection);
		startHead = new HeadShape(arrow.getStartPoint(), arrow.getStartMiddle(), arrow.getStartControl(), arrow.getStartBottom(), arrowStyle.width, arrowStyle.startHeadType, arrowStyle.startHeadSize, scaleAt);
		endHead = new HeadShape(arrow.getEndPoint(), arrow.getEndMiddle(), arrow.getEndControl(), arrow.getEndBottom(), arrowStyle.width, arrowStyle.endHeadType, arrowStyle.endHeadSize, scaleAt);
		startEclipse = new LocationEclipse(start, 10);
		endEclipse = new LocationEclipse(end, 10);
		depthKnob = new KnobShapes(arrow.getStartControl(), arrow.getEndControl(), arrowDirection);
		bounds = arrow.getBounds2D();
		Rectangle2D.union(bounds, depthKnob.getBounds2D(), bounds);
		confirmText  = new ConfirmText(bounds);
		confirmButton = new ConfirmButton(bounds, confirmText.getBounds2D());
		deleteText = new DeleteText(bounds);
		deleteButton = new DeleteButton(bounds, deleteText.getBounds2D());
		eventSource = EventSourceArrow.None;

		isDelete = false;
		isReplace = false;
	}

	public ArrowView(ArrowFormat arrowFormat, AffineTransform scaleAt) {
		this.arrowStyle = arrowFormat.arrowStyle;
		this.oldElement = arrowFormat.xmlElement;

		Point2D start = new Point2D.Double();
		scaleAt.transform(arrowFormat.getStartPoint(), start);

		Point2D end = new Point2D.Double();
		scaleAt.transform(arrowFormat.getEndPoint(), end);

		float depth = arrowFormat.getDepth();
		depth = (float) (depth * scaleAt.getScaleX());

		this.arrow = new ArrowShape(start,end, (float) (arrowStyle.radius * scaleAt.getScaleX()), depth, arrowFormat.getDirection());
		startHead = new HeadShape(arrow.getStartPoint(), arrow.getStartMiddle(), arrow.getStartControl(), arrow.getStartBottom(), arrowStyle.width, arrowStyle.startHeadType, arrowStyle.startHeadSize, scaleAt);
		endHead = new HeadShape(arrow.getEndPoint(), arrow.getEndMiddle(), arrow.getEndControl(), arrow.getEndBottom(), arrowStyle.width, arrowStyle.endHeadType, arrowStyle.endHeadSize, scaleAt);

		startEclipse = new LocationEclipse(arrow.getStartPoint(), 10);
		endEclipse = new LocationEclipse(arrow.getEndPoint(), 10);
		depthKnob = new KnobShapes(arrow.getStartControl(), arrow.getEndControl(), arrow.getDirection());
		bounds = arrow.getBounds2D();
		Rectangle2D.union(bounds, depthKnob.getBounds2D(), bounds);
		confirmText  = new ConfirmText(bounds);
		confirmButton = new ConfirmButton(bounds, confirmText.getBounds2D());
		deleteText = new DeleteText(bounds);
		deleteButton = new DeleteButton(bounds, deleteText.getBounds2D());
		eventSource = EventSourceArrow.None;

		isDelete = false;
		isReplace = true;
	}

	public Rectangle2D draw(Graphics2D g2, Rectangle2D bounds, AffineTransform scaleAt) {
		g2.setColor(arrowStyle.arrowColor);
		g2.setStroke(new BasicStroke((float) (arrowStyle.width * scaleAt.getScaleX())));
		if(startHead.getType() == ArrowHeadType.FILL)
			g2.fill(startHead);
		else if(startHead.getType() == ArrowHeadType.OPEN)
			g2.draw(startHead);

		if(endHead.getType() == ArrowHeadType.FILL)
			g2.fill(endHead);
		else if(endHead.getType() == ArrowHeadType.OPEN)
			g2.draw(endHead);

		g2.draw(arrow);
		g2.setColor(Color.BLUE);
		g2.setStroke(new BasicStroke(1));
		g2.draw(depthKnob);
		g2.fill(confirmText);
		g2.draw(confirmButton);
		g2.fill(deleteText);
		g2.draw(deleteButton);
		g2.draw(startEclipse);
		g2.draw(endEclipse);
		Rectangle2D.union(bounds, arrow.getBounds2D(), bounds);
		Rectangle2D.union(bounds, depthKnob.getBounds2D(), bounds);
		Rectangle2D.union(bounds, confirmButton.getBounds2D(), bounds);
		Rectangle2D.union(bounds, deleteButton.getBounds2D(), bounds);

		return bounds;
	}

	public int hover(Point p) {

		if(startEclipse.contains(p)) {
			return Cursor.CROSSHAIR_CURSOR;
		}
		else if(endEclipse.contains(p)) {
			return Cursor.CROSSHAIR_CURSOR;
		}
		else if(depthKnob.contains(p)) {
			if(depthKnob.getDirection() == Direction.Horizontal)
				return Cursor.N_RESIZE_CURSOR;
			else
				return Cursor.W_RESIZE_CURSOR;
		}
		else if(confirmButton.contains(p) || confirmText.contains(p)) {
			return Cursor.HAND_CURSOR;
		}
		else if(deleteButton.contains(p) || deleteText.contains(p)){
			return Cursor.HAND_CURSOR;
		}
		else if(arrow.getBounds2D().contains(p)) {
			return Cursor.MOVE_CURSOR;
		} else {
			return Cursor.DEFAULT_CURSOR;
		}
	}

	public void select(Point p) {

		if(startEclipse.contains(p)) {
			origin = (Point) p.clone();
			eventSource = EventSourceArrow.Start;
		}
		else if(endEclipse.contains(p)) {
			origin = (Point) p.clone();
			eventSource = EventSourceArrow.End;
		}
		else if(depthKnob.contains(p)){
			origin = (Point) p.clone();
			eventSource = EventSourceArrow.Knobs;
		}
		else if(arrow.getBounds2D().contains(p)){
			origin = (Point) p.clone();
			eventSource = EventSourceArrow.Position;
		}
		else if(confirmButton.contains(p) || confirmText.contains(p)){
			origin = null;
			eventSource = EventSourceArrow.Confirm;
		}
		else if(deleteButton.contains(p) || deleteText.contains(p)){
			origin = null;
			eventSource = EventSourceArrow.Delete;
			isDelete = true;
		}
		else {
			origin = null;
			eventSource = EventSourceArrow.None;
		}
	}

	public void doubleClick(Point p, AffineTransform transform) {
		if(depthKnob.contains(p)){
			arrow.chengeDirection();
			depthKnob.setPoints(arrow.getStartControl(), arrow.getEndControl(), arrow.getDirection());
			bounds = arrow.getTransformedBounds(transform);
			Rectangle2D.union(bounds, depthKnob.getBounds2D(), bounds);
			confirmText = new ConfirmText(bounds);
			confirmButton = new ConfirmButton(bounds, confirmText.getBounds2D());
			deleteText = new DeleteText(bounds);
			deleteButton = new DeleteButton(bounds, deleteText.getBounds2D());
			startHead = new HeadShape(arrow.getStartPoint(), arrow.getStartMiddle(), arrow.getStartControl(), arrow.getStartBottom(), arrowStyle.width, arrowStyle.startHeadType, arrowStyle.startHeadSize, transform);
			endHead = new HeadShape(arrow.getEndPoint(), arrow.getEndMiddle(), arrow.getEndControl(), arrow.getEndBottom(), arrowStyle.width, arrowStyle.endHeadType, arrowStyle.endHeadSize, transform);
		}
	}

	public void move(Point p, AffineTransform scaleAt) {
		if(origin != null) {
			if(eventSource == EventSourceArrow.Start) {
				arrow.translateStartPoint(p.x - origin.x,  p.y - origin.y);
			}
			else if(eventSource == EventSourceArrow.End) {
				arrow.translateEndPoint(p.x - origin.x,  p.y - origin.y);
			}
			else if(eventSource ==  EventSourceArrow.Knobs) {
				arrow.translateDepth(p.x - origin.x, p.y - origin.y);
			}
			else if(eventSource == EventSourceArrow.Position) {
				arrow.translateArrow(p.x - origin.x,  p.y - origin.y);
			}

			startEclipse.center = arrow.getStartPoint();
			endEclipse.center = arrow.getEndPoint();
			depthKnob.setPoints(arrow.getStartControl(), arrow.getEndControl(), arrow.getDirection());
			bounds = arrow.getBounds2D();
			Rectangle2D.union(bounds, depthKnob.getBounds2D(), bounds);
			confirmText = new ConfirmText(bounds);
			confirmButton = new ConfirmButton(bounds, confirmText.getBounds2D());
			deleteText = new DeleteText(bounds);
			deleteButton = new DeleteButton(bounds, deleteText.getBounds2D());
			startHead = new HeadShape(arrow.getStartPoint(), arrow.getStartMiddle(), arrow.getStartControl(), arrow.getStartBottom(), arrowStyle.width, arrowStyle.startHeadType, arrowStyle.startHeadSize, scaleAt);
			endHead = new HeadShape(arrow.getEndPoint(), arrow.getEndMiddle(), arrow.getEndControl(), arrow.getEndBottom(), arrowStyle.width, arrowStyle.endHeadType, arrowStyle.endHeadSize, scaleAt);
			origin = (Point) p.clone();
		}
	}

	public void translate(double dx, double dy, AffineTransform transform) {
		arrow.translateArrow(dx, dy);
		startHead = new HeadShape(arrow.getStartPoint(), arrow.getStartMiddle(), arrow.getStartControl(), arrow.getStartBottom(), arrowStyle.width, arrowStyle.startHeadType, arrowStyle.startHeadSize, transform);
		endHead = new HeadShape(arrow.getEndPoint(), arrow.getEndMiddle(), arrow.getEndControl(), arrow.getEndBottom(), arrowStyle.width, arrowStyle.endHeadType, arrowStyle.endHeadSize, transform);
		startEclipse.center = arrow.getStartPoint();
		endEclipse.center = arrow.getEndPoint();
		depthKnob.setPoints(arrow.getStartControl(), arrow.getEndControl(), arrow.getDirection());
		bounds = arrow.getBounds2D();
		Rectangle2D.union(bounds, depthKnob.getBounds2D(), bounds);
		confirmText = new ConfirmText(bounds);
		confirmButton = new ConfirmButton(bounds, confirmText.getBounds2D());
		deleteText = new DeleteText(bounds);
		deleteButton = new DeleteButton(bounds, deleteText.getBounds2D());
	}


	public void setArrowElemnt(AffineTransform at){
		if(isDelete) {
			if(isReplace) {
				TextEditor.replaceElement(oldElement, null);
			} else {
				return;
			}
		}

		Element newElement = new Element(LDXMLTags.Arrow);
		Point2D.Float start = arrow.getStartPoint();

		try {
			at.inverseTransform(start, start);
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
		}
		newElement.setAttribute(LDAttributes.StartPoint, pointToString(start));

		Point2D.Float end = arrow.getEndPoint();

		try {
			at.inverseTransform(end, end);
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
		}
		newElement.setAttribute(LDAttributes.EndPoint,  pointToString(end));


		float depth = arrow.getDepth();
		depth /= at.getScaleX();
		newElement.setAttribute(LDAttributes.Depth, Float.toString(depth));
		newElement.setAttribute(LDAttributes.DepthDirection, arrow.getDirection().toString());
		
		if(arrowStyle.id != null) newElement.setAttribute(LDAttributes.ID, arrowStyle.id);
		
		if(!isReplace) {
			TextEditor.setArrowValue(newElement);
		} else {
			TextEditor.replaceElement(oldElement, newElement);
		}
	}

	private String pointToString(Point2D.Float p) {
		return Float.toString(p.x) + "," + Float.toString(p.y);
	}
}

enum EventSourceArrow { None, Knobs, Start, End, Confirm, Position, Delete }

