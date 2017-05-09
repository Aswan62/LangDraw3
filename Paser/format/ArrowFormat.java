package format;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.jdom.Element;

import enums.Enums.ArrowHeadType;
import enums.Enums.Direction;

import shapes.ArrowShape;
import shapes.HeadShape;
import style.ArrowStyle;

public class ArrowFormat extends BaseFormat {
	protected ArrowShape arrow;
	protected HeadShape startHead, endHead;
	public ArrowStyle arrowStyle;
	public Element xmlElement;

	public ArrowFormat(ArrowShape arrowShape, HeadShape start, HeadShape end, ArrowStyle style, Element element) {
		arrow = arrowShape;
		startHead = start;
		endHead = end;
		arrowStyle = style;
		xmlElement = element;
	}

	public Point2D.Float getStartPoint() {
		return arrow.getStartPoint();
	}

	public Point2D.Float getEndPoint() {
		return arrow.getEndPoint();
	}

	public float getDepth() {
		return arrow.getDepth();
	}

	public Direction getDirection() {
		return arrow.getDirection();
	}


	public void drawArrow(Graphics2D g2) {
		g2.setColor(arrowStyle.arrowColor);
		g2.setStroke(new BasicStroke(arrowStyle.width));
		g2.draw(arrow);

		if(arrowStyle.startHeadType == ArrowHeadType.OPEN) g2.draw(startHead);
		else if(arrowStyle.startHeadType == ArrowHeadType.FILL) g2.fill(startHead);

		if(arrowStyle.endHeadType == ArrowHeadType.OPEN) g2.draw(endHead);
		else if(arrowStyle.endHeadType == ArrowHeadType.FILL) g2.fill(endHead);
	}

	public Rectangle2D getBounds() {
		Rectangle2D bounds = arrow.getBounds2D();
		if(arrowStyle.startHeadType != ArrowHeadType.NONE)
			Rectangle2D.Float.union(bounds, startHead.getBounds2D(), bounds);
		if(arrowStyle.endHeadType != ArrowHeadType.NONE)
			Rectangle2D.Float.union(bounds, endHead.getBounds2D(), bounds);
		return bounds;
	}

	public Rectangle2D getTransformedBounds(AffineTransform at) {
		Rectangle2D bounds = arrow.getTransformedBounds(at);
		if(arrowStyle.startHeadType != ArrowHeadType.NONE)
			Rectangle2D.Float.union(bounds, startHead.getTransformedBounds(), bounds);
		if(arrowStyle.endHeadType != ArrowHeadType.NONE)
			Rectangle2D.Float.union(bounds, endHead.getTransformedBounds(), bounds);
		return bounds;
	}
}
