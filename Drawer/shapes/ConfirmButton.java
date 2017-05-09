package shapes;

import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class ConfirmButton extends AbstractShape {

	Point2D.Float topLeft;
	Rectangle2D textRect;

	public ConfirmButton(Rectangle2D rectangle2d, Rectangle2D textBounds) {
		topLeft = new Point2D.Float((float)rectangle2d.getMinX() + 10, (float)rectangle2d.getMaxY() + 10);
		this.textRect = textBounds;
		this.textRect = new Rectangle2D.Double(textBounds.getX() - 2, textBounds.getY() - 2, textBounds.getWidth() + 4, textBounds.getHeight() + 4);
	}

	@Override
	protected Shape getShape() {
		Rectangle2D.Float rect = new Rectangle2D.Float(topLeft.x, topLeft.y, 20, 20);
		Rectangle2D.union(textRect, rect, rect);
		GeneralPath gp = new GeneralPath();
		gp.append(rect, false);
		gp.moveTo(topLeft.x + 3, topLeft.y + 8);
		gp.quadTo(topLeft.x + 6, topLeft.y + 8, topLeft.x + 8, topLeft.y + 17);
		gp.quadTo(topLeft.x + 10, topLeft.y + 3, topLeft.x + 17, topLeft.y + 3);
		return gp;
	}

}
