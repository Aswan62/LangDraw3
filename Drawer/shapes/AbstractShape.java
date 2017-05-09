package shapes;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public abstract class AbstractShape implements Shape {

	abstract protected Shape getShape();

	public boolean contains(double x, double y) {
		return getShape().contains(x,y);
	}

	public boolean contains(double x, double y, double w, double h) {
		return getShape().contains(x,y,w,h);
	}

	public boolean intersects(double x, double y, double w, double h) {
		return getShape().intersects(x,y,w,h);
	}

	public Rectangle getBounds() {
		return getShape().getBounds();
	}

	public boolean contains(Point2D p) {
		return getShape().contains(p);
	}

	public Rectangle2D getBounds2D() {
		return getShape().getBounds2D();
	}

	public boolean contains(Rectangle2D r) {
		return getShape().contains(r);
	}

	public boolean intersects(Rectangle2D r) {
		return intersects(r);
	}

	public PathIterator getPathIterator(AffineTransform at) {
		return getShape().getPathIterator(at);
	}

	public PathIterator getPathIterator(AffineTransform at, double flatness) {
		return getShape().getPathIterator(at,flatness);
	}

	public Shape getTranslatedShape(AffineTransform at) {
		return at.createTransformedShape(getShape());
	}

}
