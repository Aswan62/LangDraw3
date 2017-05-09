package shapes;

import java.awt.Font;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class SelectorText extends AbstractShape {

	private String source;
	private Point2D point;

	public SelectorText(String text,Point2D p) {
		this.source = text;
		this.point = p;
	}

	@Override
	protected Shape getShape() {
		Font font = new Font("serif", Font.PLAIN, 100);
		FontRenderContext frc = new FontRenderContext(null, true, true);
		Shape shape = new TextLayout(source, font, frc).getOutline(AffineTransform.getTranslateInstance(0,0));
		float scale = (float) (shape.getBounds2D().getHeight() / 20D);
		shape = AffineTransform.getScaleInstance(1F/scale, 1F/scale).createTransformedShape(shape);
		shape = AffineTransform.getTranslateInstance(point.getX(), point.getY() - shape.getBounds2D().getMaxY()).createTransformedShape(shape);
		return shape;
	}

	public Rectangle2D getTextRectangle2D() {
		Rectangle2D rect = getShape().getBounds2D();
		rect.setRect(rect.getX() - 4, rect.getY() - 4, rect.getWidth() + 8, rect.getHeight() + 8);
		return rect;
	}
}
