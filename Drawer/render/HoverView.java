package render;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.Timer;


public class HoverView implements ActionListener {
	private ArrayList<Rectangle2D> rects;
	private Color color;
	float opacity, diff;
	ImageCanvas canvas;
	private Timer timer;
	private Rectangle2D bounds;
	private Shape textShape, drawTextShape;
	private Rectangle2D roundShape;

	public HoverView(ImageCanvas canvas) {
		this.canvas = canvas;
		opacity = 0F;
		color = new Color(Color.YELLOW.getRed()/255F, Color.YELLOW.getGreen()/255F, Color.YELLOW.getBlue()/255F, opacity);
		timer = new Timer(50 , this);

		Font font = new Font("serif", Font.PLAIN, 100);
		FontRenderContext frc = new FontRenderContext(null, true, true);
		textShape = new TextLayout("Double click to edit", font, frc).getOutline(AffineTransform.getTranslateInstance(0,0));
		float scale = (float) (textShape.getBounds2D().getHeight() / 15D);
		textShape = AffineTransform.getScaleInstance(1F/scale, 1F/scale).createTransformedShape(textShape);

	}

	public void setParameter(ArrayList<Rectangle2D> rects) {
		if(rects != null) {
			bounds = new Rectangle2D.Double();
			this.rects = rects;
			if(rects.size() > 0)
				bounds =  (Rectangle2D) rects.get(0).clone();


			for(Rectangle2D rect: this.rects) {
				rect.setRect(rect.getX() - 2.5, rect.getY() - 2.5, rect.getWidth()+5, rect.getHeight()+5);
				Rectangle2D.union(bounds, rect, bounds);
			}
			drawTextShape = AffineTransform.getTranslateInstance(bounds.getX() - 3, bounds.getMinY() - 6).createTransformedShape(textShape);
			roundShape = drawTextShape.getBounds2D();
			roundShape.setRect(roundShape.getX() - 3, roundShape.getY() - 3, roundShape.getWidth() + 6, roundShape.getHeight() + 6);
			timer.start();
		}
		else {
			this.rects = rects;
		}
	}

	public void draw(Graphics2D g) {
		if(rects != null && rects.size() > 0) {
			g.setStroke(new BasicStroke(5));
			g.setColor(color);
			for(Rectangle2D rect: rects) {
				g.draw(rect);
			}

			g.setColor(Color.WHITE);
			g.fill(roundShape);
			g.setColor(Color.RED);
			g.fill(drawTextShape);
		}
	}

	public void actionPerformed(ActionEvent e) {
		opacity += diff;
		if(opacity >= 1) {
			opacity = 1F;
			diff = -0.1F;
		}
		else if(opacity <= 0) {
			opacity = 0F;
			diff = 0.1F;
		}
		color = new Color(Color.red.getRed()/255F, Color.red.getGreen()/255F, Color.red.getBlue()/255F, opacity);
		canvas.repaint();
	}

}
