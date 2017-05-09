package shapes;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import render.ImageCanvas;

import enums.Enums.ArrowHeadType;

public class HeadShape extends AbstractShape {

	private GeneralPath headPath;
	private ArrowHeadType headType;

	public HeadShape(Point2D zero, Point2D middle, Point2D control, Point2D bottom, float arrowWidth, ArrowHeadType headType, float headSize, AffineTransform at) {
		this.headType = headType;

		float headLine = (float) (10 * headSize * at.getScaleX());
		float x =  (float) (headLine*Math.sin(30D/180D*Math.PI));
		float y =  (float) (headLine*Math.cos(30D/180D*Math.PI));

		double n = middle.distance(bottom) / headLine;

		double diffX = bottom.getX() + (2*n - 2) * control.getX() + (1 - 2*n) * middle.getX();

		double diffY = bottom.getY() + (2*n - 2) * control.getY() + (1 - 2*n) * middle.getY();


		headPath = new GeneralPath();
		if(headType != ArrowHeadType.NONE) {
			headPath.moveTo(zero.getX(), zero.getY());
			headPath.lineTo(zero.getX() - x, zero.getY() + y);
			if(headType == ArrowHeadType.OPEN) {
				headPath.moveTo(zero.getX(), zero.getY());
				headPath.lineTo(zero.getX() + x, zero.getY() + y);
			}
			else if(headType == ArrowHeadType.FILL) {
				headPath.lineTo(zero.getX() + x, zero.getY() + y);
				headPath.lineTo(zero.getX(), zero.getY());
			}
		}
		double theta = 0;
		if(zero.distance(middle) != 0) {
			if(zero.getX() - middle.getX() > 0) theta = Math.PI/2D;
			else if(zero.getX() - middle.getX() < 0) theta = - Math.PI/2D;
			else if(zero.getY() - middle.getY() > 0) theta = Math.PI;
		} else {
			if(bottom.getY() - middle.getY() >= 0)
				theta = Math.atan(-diffX/diffY);
			else
				theta = Math.atan(-diffX/diffY) + Math.PI;
		}

		headPath.transform(AffineTransform.getRotateInstance(theta, zero.getX(), zero.getY()));
	}


	public ArrowHeadType getType() {
		return this.headType;
	}

	@Override
	protected Shape getShape() {
		return headPath;
	}

	@Override
	public Rectangle2D getBounds2D() {
		return headPath.getBounds2D();
	}


	public Rectangle2D getTransformedBounds() {
		Rectangle2D rect = (Rectangle2D) getShape().getBounds2D().clone();
		rect.setRect(rect.getX() * ImageCanvas.getImageScale().getScaleX(), rect.getY() * ImageCanvas.getImageScale().getScaleY(), rect.getWidth() * ImageCanvas.getImageScale().getScaleX(), rect.getHeight() * ImageCanvas.getImageScale().getScaleY());
		return rect;
	}
}
