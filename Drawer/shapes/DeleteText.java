package shapes;

import java.awt.Font;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class DeleteText extends AbstractShape {

	private Point2D point;
	public DeleteText(Rectangle2D rectangle2d) {
		this.point = new Point2D.Float((float)rectangle2d.getMinX() + 40, (float)rectangle2d.getMaxY() + 60);
	}

	@Override
	protected Shape getShape() {
		Font font = new Font("serif", Font.PLAIN, 100);
		FontRenderContext frc = new FontRenderContext(null, true, true);
		Shape shape = new TextLayout("Delete", font, frc).getOutline(AffineTransform.getTranslateInstance(-2,0));
		float scale = (float) (shape.getBounds2D().getHeight() / 16D);
		shape = AffineTransform.getScaleInstance(1F/scale, 1F/scale).createTransformedShape(shape);
		shape = AffineTransform.getTranslateInstance(point.getX(), point.getY() - shape.getBounds2D().getMaxY()).createTransformedShape(shape);
		return shape;
	}
}
