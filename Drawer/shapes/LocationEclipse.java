package shapes;

import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;

public class LocationEclipse extends AbstractShape {

	public Point2D center;
	private float radius, tempRadius;

	public LocationEclipse(Point2D p, float radius) {
		this.center = new Point2D.Double(p.getX(), p.getY());
		this.radius = radius;
		this.tempRadius = radius;
	}

	@Override
	protected Shape getShape() {
		return new Arc2D.Double(center.getX() - radius/2, center.getY() - radius/2, radius, radius, 0, 360, Arc2D.CHORD);
	}

	@Override
	public boolean contains(Point2D p) {
		boolean result = super.contains(p);
		if(result) {
			radius = 2 * tempRadius;
		} else {
			radius = tempRadius;
		}
		return result;
	}



}
