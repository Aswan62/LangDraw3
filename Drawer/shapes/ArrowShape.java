package shapes;

import java.awt.geom.*;

import enums.Enums.Direction;


public class ArrowShape extends AbstractShape {

	private Point2D.Float startTop, startMiddle, startControl, startBottom, endControl, endBottom, endMiddle, endTop;
	private float bottom, radius, radiusDummy, depth, dummyDepth, dummyStartX, dummyEndX, dummyStartY, dummyEndY;
	private Direction direction;

	public ArrowShape(Point2D start, Point2D end, float radius, float depth, Direction direction) {
		this.direction = direction;
		this.radius = radius;
		this.depth = depth;
		startTop = new  Point2D.Float();
		endTop = new  Point2D.Float();
		startMiddle = new Point2D.Float();
		endMiddle = new Point2D.Float();
		startBottom = new Point2D.Float();
		endBottom = new Point2D.Float();

		startTop.x = (float) start.getX();
		startTop.y = (float) start.getY();
		endTop.x = (float) end.getX();
		endTop.y = (float) end.getY();

		this.radiusDummy = radius;
		this.dummyDepth = depth;
		dummyStartX = startTop.x;
		dummyStartY = startTop.y;
		dummyEndX = endTop.x;
		dummyEndY = endTop.y;

		setLayout();
	}

	private void setLayout() {

		if(direction == Direction.Horizontal) {
			if(Math.abs(startTop.x - endTop.x) < radiusDummy * 2) {
				this.radius = Math.abs(startTop.x - endTop.x)/2;
			} else {
			 	radius = radiusDummy;
			}

			bottom = startTop.y + depth;

			if(startTop.x < endTop.x) {
				startBottom.x = startTop.x + radius;
				endBottom.x = endTop.x - radius;
			} else {
				startBottom.x = startTop.x - radius;
				endBottom.x = endTop.x + radius;
			}
			startBottom.y = bottom;
			endBottom.y = startBottom.y;

			if(Math.abs(depth) <= radius) {
				startMiddle.y = startTop.y;
			} else {
				if(depth > radius) {
					startMiddle.y = bottom - radius;
				} else if(-depth > radius) {
					startMiddle.y = bottom + radius;
				}
			}

			startMiddle.x = startTop.x;
			endMiddle.x = endTop.x;

			if(endTop.y > bottom + radius) {
				endMiddle.y = bottom + radius;
			}
			else if(endTop.y < bottom - radius) {
				endMiddle.y = bottom - radius;
			}
			else {
				endMiddle.y = endTop.y;
			}

			startControl = new Point2D.Float(startTop.x, startBottom.y);
			endControl = new Point2D.Float(endTop.x, endBottom.y);
		}
		else if(direction == Direction.Vertical) {
			if(Math.abs(startTop.y - endTop.y) < radiusDummy * 2) {
				this.radius = Math.abs(startTop.y - endTop.y)/2;
			} else {
			 	radius = radiusDummy;
			}

			bottom = startTop.x + depth;

			if(startTop.y < endTop.y) {
				startBottom.y = startTop.y + radius;
				endBottom.y = endTop.y - radius;
			} else {
				startBottom.y = startTop.y - radius;
				endBottom.y = endTop.y + radius;
			}
			startBottom.x = bottom;
			endBottom.x = startBottom.x;

			if(Math.abs(depth) <= radius) {
				startMiddle.x = startTop.x;
			} else {
				if(depth > radius) {
					startMiddle.x = bottom - radius;
				} else if(-depth > radius) {
					startMiddle.x = bottom + radius;
				}
			}

			startMiddle.y = startTop.y;
			endMiddle.y = endTop.y;

			if(endTop.x > bottom + radius) {
				endMiddle.x = bottom + radius;
			}
			else if(endTop.x < bottom - radius) {
				endMiddle.x = bottom - radius;
			}
			else {
				endMiddle.x = endTop.x;
			}

			startControl = new Point2D.Float(startBottom.x, startTop.y);
			endControl = new Point2D.Float(endBottom.x, endTop.y);
		}
	}

	public void chengeDirection() {
		if(direction == Direction.Horizontal) {
			direction = Direction.Vertical;
		}
		else if(direction == Direction.Vertical) {
			direction = Direction.Horizontal;
		}

		dummyDepth = depth;
		dummyStartX = startTop.x;
		dummyStartY = startTop.y;
		dummyEndX = endTop.x;
		dummyEndY = endTop.y;

		setLayout();
	}

	public Direction getDirection() {
		return direction;
	}

	public void translateStartPoint(int dx, int dy) {
		if(direction == Direction.Horizontal) {
			dummyDepth -= dy;

			startTop.x += dx;
			dummyStartY += dy;


			if(Math.abs(dummyDepth) < 10) {
				startTop.y = dummyStartY + dummyDepth;
				depth = 0;
			}
			else {
				depth = dummyDepth;
				startTop.y = dummyStartY;
			}
		} else {
			dummyDepth -= dx;

			startTop.y += dy;
			dummyStartX += dx;


			if(Math.abs(dummyDepth) < 10) {
				startTop.x = dummyStartX + dummyDepth;
				depth = 0;
			}
			else {
				depth = dummyDepth;
				startTop.x = dummyStartX;
			}
		}
		setLayout();
	}

	public void translateEndPoint(int dx, int dy) {
		if(direction == Direction.Horizontal) {
			endTop.x += dx;
			dummyEndY += dy;

			if(Math.abs(startTop.y + depth - dummyEndY) < 10) {
				endTop.y = startTop.y + depth;
			}
			else
				endTop.y = dummyEndY;
		} else {
			endTop.y += dy;
			dummyEndX += dx;

			if(Math.abs(startTop.x + depth - dummyEndX) < 10) {
				endTop.x = startTop.x + depth;
			}
			else
				endTop.x = dummyEndX;
		}

		setLayout();
	}

	public void translateDepth(int dx, int dy) {
		if(direction == Direction.Horizontal) {
			dummyDepth += dy;

			if(Math.abs(dummyDepth) < 10) depth = 0;
			else if(Math.abs(startTop.y + dummyDepth - endTop.y) < 10) {
				 depth = endTop.y - startTop.y;
			} else {
				depth = dummyDepth;
			}
		} else {
			dummyDepth += dx;

			if(Math.abs(dummyDepth) < 10) depth = 0;
			else if(Math.abs(startTop.x + dummyDepth - endTop.x) < 10) {
				 depth = endTop.x - startTop.x;
			} else {
				depth = dummyDepth;
			}
		}

		setLayout();
	}

	public void translateArrow(double dx, double dy) {
		startTop.x += dx;
		startTop.y += dy;
		if(Math.abs(dummyDepth) > 10) dummyStartY = startTop.y;
		endTop.x += dx;
		endTop.y += dy;
		if(Math.abs(startTop.y + depth - dummyEndY) > 10) dummyEndY = endTop.y;

		setLayout();
	}

	public Point2D.Float getStartPoint() {
		return startTop;
	}

	public Point2D.Float getEndPoint() {
		return endTop;
	}

	public Point2D.Float getStartMiddle() {
		return startMiddle;
	}

	public Point2D.Float getEndMiddle() {
		return endMiddle;
	}

	public Point2D.Float getStartControl() {
		return startControl;
	}

	public Point2D.Float getEndControl() {
		return endControl;
	}

	public Point2D.Float getStartBottom() {
		return startBottom;
	}

	public Point2D.Float getEndBottom() {
		return endBottom;
	}

	public float getRadius() {
		return radius;
	}

	public float getDepth() {
		return depth;
	}

	@Override
	protected GeneralPath getShape() {
		GeneralPath path = new GeneralPath();
		path.moveTo(startTop.x, startTop.y);
		path.lineTo(startMiddle.x, startMiddle.y);
		path.quadTo(startControl.x, startControl.y, startBottom.x, startBottom.y);
		path.lineTo(endBottom.x, endBottom.y);
		path.quadTo(endControl.x, endControl.y, endMiddle.x, endMiddle.y);
		path.lineTo(endTop.x, endTop.y);
		return path;
	}

	public Rectangle2D getBounds2D() {
		Rectangle2D rect = (Rectangle2D) getShape().getBounds2D().clone();
		rect.setRect(rect.getX()-5, rect.getY()-5, rect.getWidth()+10, rect.getHeight()+10);
		return rect;
	}

	public Rectangle2D getTransformedBounds(AffineTransform at) {
		Rectangle2D rect = (Rectangle2D) getShape().getBounds2D().clone();
		rect.setRect(rect.getX() * at.getScaleX(), rect.getY() * at.getScaleY(), rect.getWidth() * at.getScaleX(), rect.getHeight() * at.getScaleY());
		return rect;
	}

}
