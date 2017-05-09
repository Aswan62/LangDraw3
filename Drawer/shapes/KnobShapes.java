package shapes;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import enums.Enums.Direction;


public class KnobShapes extends AbstractShape {

	private Knob rightDepthKnob;
	private Knob leftDepthKnob;
	private Direction arrowDirection;

	public KnobShapes(Point2D start, Point2D end, Direction direction) {
		this.arrowDirection = direction;
		if(arrowDirection == Direction.Horizontal) {
			if(start.getX() < end.getX()) {
				rightDepthKnob = new Knob(start, Direction.RightToLeft);
				leftDepthKnob = new Knob(end, Direction.LeftToRight);
			} else {
				rightDepthKnob = new Knob(start, Direction.LeftToRight);
				leftDepthKnob = new Knob(end, Direction.RightToLeft);
			}
		}
		else if(arrowDirection == Direction.Vertical) {
			if(start.getY() < end.getY()) {
				rightDepthKnob = new Knob(start, Direction.BottomToTop);
				leftDepthKnob = new Knob(end, Direction.TopToBttom);
			} else {
				rightDepthKnob = new Knob(start, Direction.TopToBttom);
				leftDepthKnob = new Knob(end, Direction.BottomToTop);
			}
		}
	}

	public void setPoints(Point2D start, Point2D end, Direction direction) {
		this.arrowDirection = direction;
		if(arrowDirection == Direction.Horizontal) {
			if(start.getX() < end.getX()) {
				rightDepthKnob.update(start, Direction.RightToLeft);
				leftDepthKnob.update(end, Direction.LeftToRight);
			} else {
				rightDepthKnob.update(start, Direction.LeftToRight);
				leftDepthKnob.update(end, Direction.RightToLeft);
			}
		}
		else if(arrowDirection == Direction.Vertical) {
			if(start.getY() < end.getY()) {
				rightDepthKnob.update(start, Direction.BottomToTop);
				leftDepthKnob.update(end, Direction.TopToBttom);
			} else {
				rightDepthKnob.update(start, Direction.TopToBttom);
				leftDepthKnob.update(end, Direction.BottomToTop);
			}
		}
	}

	@Override
	protected Shape getShape() {
		GeneralPath gp = new GeneralPath(rightDepthKnob.getShape());
		gp.append(leftDepthKnob.getShape(), false);
		return gp;
	}

	public Rectangle2D getBounds2D() {
		return getShape().getBounds2D();
	}

	@Override
	public boolean contains(Point2D p) {
		boolean result = super.contains(p);
		this.leftDepthKnob.setOnMouse(result);
		this.rightDepthKnob.setOnMouse(result);
		return result;
	}

	public Direction getDirection() {
		return arrowDirection;
	}
}

class Knob extends AbstractShape {

	Point2D start, middleStart, bottomStart, bottomEnd, topStart, topEnd;
	float width, height, spacing;
	boolean inOnMouse;
	private Direction direction;

	protected Knob(Point2D p, Direction direction) {
		this.direction = direction;
		this.start = p;
	}

	protected void update(Point2D start, Direction direction) {
		this.start = start;
		this.direction = direction;

	}

	protected void setOnMouse(boolean isOnMouse) {
		this.inOnMouse = isOnMouse;
	}

	@Override
	protected Shape getShape() {
		if(direction == Direction.LeftToRight || direction ==  Direction.TopToBttom) {
			width = 10;
			height = 5;
			spacing = 10;
		} else {
			width = -10;
			height = 5;
			spacing = -10;
		}

		if(inOnMouse) {
			width *= 2;
			height *= 2;
		}

		this.middleStart = new Point2D.Double(start.getX() + spacing, start.getY());
		this.topStart = new Point2D.Double(middleStart.getX() + spacing, start.getY() - height);
		this.topEnd = new Point2D.Double(topStart.getX() + width, topStart.getY());
		this.bottomStart = new Point2D.Double(middleStart.getX() + spacing, start.getY() + height);
		this.bottomEnd = new Point2D.Double(bottomStart.getX() + width, bottomStart.getY());

		Shape result;
		GeneralPath gp = new GeneralPath();
		gp.moveTo(start.getX(), start.getY());
		gp.lineTo(middleStart.getX(), middleStart.getY());
		gp.lineTo(topStart.getX(), topStart.getY());
		gp.lineTo(topEnd.getX(), topEnd.getY());
		gp.lineTo(bottomEnd.getX(), bottomEnd.getY());
		gp.lineTo(bottomStart.getX(), bottomStart.getY());
		gp.lineTo(middleStart.getX(), middleStart.getY());
		result = gp;

		if(direction ==  Direction.TopToBttom || direction ==  Direction.BottomToTop) {
			AffineTransform at = AffineTransform.getRotateInstance(Math.PI/2D, start.getX(), start.getY());
			result = at.createTransformedShape(result);
		}

		return result;
	}


}
