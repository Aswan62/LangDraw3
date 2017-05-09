package shapes;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;


import format.BaseFormat;

public class SelectorRect extends AbstractShape {

	private Rectangle2D rect;
	private BaseFormat format;

	public SelectorRect(BaseFormat format,Rectangle2D rect) {
		this.rect = rect;
		this.format = format;
	}

	public BaseFormat getFormat() {
		return format;
	}

	@Override
	protected Shape getShape() {
		return rect;
	}
}
