package render;

import itemList.DrawingList;
import itemList.StyleList;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import style.ArrowStyle;
import windows.DrawingWindow;
import windows.MainWindow;
import enums.Enums.Direction;
import enums.Enums.EditDrawingMode;
import format.ArrowFormat;
import format.BaseFormat;
import format.TextBoxFormat;
import format.WordFormat;


public class ImageCanvas extends JPanel implements ActionListener{

	/**
	 *
	 */
	private static final long serialVersionUID = 4654068913841568927L;
	static private BufferedImage sourceImage = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
	static public EditDrawingMode currentMode = EditDrawingMode.None;
	private Point startPoint, endPoint;
	private float startPointRadius = 0;
	boolean increase = true;
	private ArrowView arrowView;
	private Timer timer;
	int xMargin, yMargin;
	static float offsetX, offsetY;
	private TextBoxView textBoxView;
	private EditView editView;
	private HoverView hoverView;
	static private AffineTransform imageScale;
	private AffineTransform mouseDiffAt;
	private static Line2D caretLine;
	private JScrollPane parent;

	
	static public void changeCaretIndex(int index) {
		float x1 = 0, x2 = 0, y1 = 0, y2 = 0;
		for(int i = 0; i < DrawingList.Words.size() - 1; i++) {
			WordFormat target = DrawingList.Words.get(i+1);
			if(index < target.getCharIndex()) {
				x1 = target.getBounds().x;
				x2 = target.getBounds().x;
				y1 = target.getBounds().y;
				y2 = target.getBounds().y + target.getHeight();
				break;
			}
		}
		caretLine = new Line2D.Float(x1, y1, x2, y2);
	}
	
	static public AffineTransform getImageScale() {
		return imageScale;
	}

	static public void setImageScale(AffineTransform scale) {
		imageScale = scale;
	}

	synchronized public static BufferedImage getSourceImage() {
		return sourceImage;
	}

	synchronized public static void setSourceImage(BufferedImage sourceImage, float x, float y) {
		ImageCanvas.sourceImage = sourceImage;
		offsetX = x;
		offsetY = y;
	}

	public ImageCanvas(JScrollPane parent) {
		this.parent = parent;
		setBackground(Color.WHITE);

		imageScale = AffineTransform.getScaleInstance(1, 1);
		xMargin = 40;
		yMargin = 40;
		currentMode = EditDrawingMode.None;

		mouseDiffAt = AffineTransform.getTranslateInstance(-xMargin + offsetX, -yMargin + offsetY);
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				Point p = (Point) e.getPoint();
				mouseDiffAt.transform(p, p);

				if(!DrawingWindow.isLabelBusy()) {
					if(currentMode == EditDrawingMode.setArrowLayout && arrowView != null) {
						changeCursor(arrowView.hover(p));
						repaint();
					}
					if(currentMode == EditDrawingMode.setTextBoxLayout && textBoxView != null) {
						textBoxView.hover(p);
						repaint();
					}
					if(currentMode == EditDrawingMode.selectMode && editView != null) {
						editView.hover(p);
						repaint();
					}
					editorHoverToDo(p);
				}
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				Point p = e.getPoint();
				mouseDiffAt.transform(p, p);

				if(currentMode == EditDrawingMode.setArrowLayout && arrowView != null) {
					if(arrowView.isMouseSelected()) arrowView.move(p, imageScale);
					repaint();
				}
				if(currentMode == EditDrawingMode.setTextBoxLayout && textBoxView != null) {
					if(textBoxView.isMouseSelected()) textBoxView.move(p, imageScale);
					repaint();
				}
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Point p = e.getPoint();
				mouseDiffAt.transform(p, p);

				int clickcount = e.getClickCount();
				arrowClickToDo(p, clickcount);
				textBoxClickToDo(p, clickcount);
				editorClickToDo(p, clickcount);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
				hoverView.setParameter(null);
			}
		});

		hoverView = new HoverView(this);

		timer = new Timer(40,this);
	}



	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		RenderQuality.setHighQuality();
		g2.setRenderingHints(RenderQuality.getCurrentQuality());
		
		g2.drawImage(sourceImage, xMargin , yMargin, this);

		Rectangle2D prefectureBounds = new Rectangle2D.Double(0,0,sourceImage.getWidth(),sourceImage.getHeight());

		
		if(MainWindow.isDrawBorder()) {
			g2.setStroke(new BasicStroke(1));
			g2.setColor(Color.BLACK);
			g2.drawRect(xMargin, yMargin, sourceImage.getWidth(), sourceImage.getHeight());
		}
		
		if(currentMode == EditDrawingMode.None) {
			changeCursor(Cursor.DEFAULT_CURSOR);
		} else {

		}

		if(mouseDiffAt != null)
			g2.translate(-mouseDiffAt.getTranslateX(), -mouseDiffAt.getTranslateY());

		if(caretLine != null) {
			g2.setStroke(new BasicStroke(2));
			g2.setColor(Color.BLACK);
			g2.draw(caretLine);
		}
		
		if(currentMode == EditDrawingMode.None && hoverView != null) {
			hoverView.draw(g2);
		}
		else {
			g2.setColor(new Color(255,255,255,200));
			g2.fillRect((int)offsetX, (int)offsetY, sourceImage.getWidth() + 5, sourceImage.getHeight() + 5);
			if(currentMode == EditDrawingMode.setArrowEnd) {
				Ellipse2D.Float ellipse2d = new Ellipse2D.Float(startPoint.x - startPointRadius / 2, startPoint.y - startPointRadius / 2, startPointRadius, startPointRadius);
				g2.setColor(Color.BLACK);
				g2.setStroke(new BasicStroke(1));
				g2.draw(ellipse2d);
			}
			else if(currentMode == EditDrawingMode.setArrowLayout) {
				prefectureBounds = arrowView.draw(g2, prefectureBounds, imageScale);
			}
			else if(currentMode == EditDrawingMode.setTextBoxLayout) {
				prefectureBounds = textBoxView.draw(g2, prefectureBounds, imageScale);
			}
			else if(currentMode == EditDrawingMode.selectMode) {
				prefectureBounds = editView.draw(g2, prefectureBounds, imageScale);
			} else { }
		}
		
		if(mouseDiffAt != null) {
			g2.translate(mouseDiffAt.getTranslateX(), mouseDiffAt.getTranslateY());


			if(-(prefectureBounds.getMinX() - offsetX) > 40) {
				xMargin = (int)Math.floor(-(prefectureBounds.getMinX() - offsetX));
			} else {
				xMargin = 40;
			}

			if(-(prefectureBounds.getMinY() - offsetY) > 40) {
				yMargin = (int)Math.floor(-(prefectureBounds.getMinY() - offsetY));
			} else {
				yMargin = 40;
			}

			mouseDiffAt = AffineTransform.getTranslateInstance(-xMargin + offsetX, -yMargin + offsetY);
		}

		this.setPreferredSize(new Dimension((int)prefectureBounds.getWidth() + xMargin + 40, (int)prefectureBounds.getHeight() + yMargin + 40));
		
		parent.revalidate();
	}

	public void startArrowEdit(String styleID) {
		currentMode = EditDrawingMode.setArrowStart;
		changeCursor(Cursor.CROSSHAIR_CURSOR);
		ArrowStyle arrowStyle = StyleList.ArrowStyleList.get(styleID);
		arrowView = new ArrowView(arrowStyle);
	}

	public void startTextBoxEdit(String styleID, String sourceText) {
		textBoxView = new TextBoxView(this, sourceText, styleID);
		currentMode = EditDrawingMode.setTextBoxStart;
		changeCursor(Cursor.CROSSHAIR_CURSOR);
	}

	public void actionPerformed(ActionEvent e) {
		if(currentMode == EditDrawingMode.setArrowEnd) {
			if(increase) startPointRadius += 1;
			else startPointRadius -= 1;
			if(startPointRadius > 15) increase = false;
			if(startPointRadius <= 0) increase = true;
			repaint();
		}
	}

	private void setArrowElement() {
		if(arrowView != null) {
			arrowView.setArrowElemnt(imageScale);
		}
	}

	private void setTextBoxElement() {
		if(textBoxView != null) {
			textBoxView.setTextBoxElement();
		}
	}

	private void arrowClickToDo(Point p, int clickcount) {
		if(clickcount == 1) {
			if(currentMode == EditDrawingMode.setArrowStart) {
				startPoint = p;
				currentMode = EditDrawingMode.setArrowEnd;
				repaint();
				timer.start();
			}
			else if(currentMode == EditDrawingMode.setArrowEnd) {
				timer.stop();
				endPoint = p;
				currentMode = EditDrawingMode.setArrowLayout;
				arrowView.setArrow(startPoint, endPoint, 30, Direction.Horizontal, imageScale);
				repaint();
			}
			else if(currentMode == EditDrawingMode.setArrowLayout) {
				arrowView.select(p);
				if(arrowView.isLayoutConfirmed()) {
					currentMode = EditDrawingMode.None;
					changeCursor(Cursor.DEFAULT_CURSOR);
					setArrowElement();
					xMargin = 40;
					yMargin = 40;
					arrowView = null;
				}
				repaint();
			}
		}
		else if(clickcount == 2) {
			if(currentMode == EditDrawingMode.setArrowLayout) {
				arrowView.doubleClick(p, imageScale);
			}
		}
	}

	private void editorHoverToDo(Point p) {
		if(currentMode == EditDrawingMode.None) {

			Point2D.Float selectPoint = new Point2D.Float(p.x, p.y);

			ArrayList<Rectangle2D> hoverdRects = new ArrayList<Rectangle2D>();
			for(ArrowFormat af :DrawingList.Arrows) {
				Rectangle2D rect = (Rectangle2D) af.getTransformedBounds(imageScale).clone();
				if(rect.contains(selectPoint)) {
					hoverdRects.add(rect);
					break;
				}
			}

			for(TextBoxFormat tb :DrawingList.TextBoxes) {
				Rectangle2D rect = (Rectangle2D) tb.getTransformedTextBoxRect(imageScale).clone();
				if(rect != null && rect.contains(selectPoint)) {
					hoverdRects.add(rect);
					break;
				}
			}
			hoverView.setParameter(hoverdRects);
		}
	}

	private void editorClickToDo(Point p, int clickcount) {
		if(currentMode == EditDrawingMode.None && clickcount == 2) {

			Point2D.Float selectPoint = new Point2D.Float(p.x, p.y);

			ArrayList<ArrowFormat> selectedArrows = new ArrayList<ArrowFormat>();
			for(ArrowFormat af :DrawingList.Arrows) {
				Rectangle2D rect = (Rectangle2D) af.getTransformedBounds(imageScale).clone();

				if(rect.contains(selectPoint))
					selectedArrows.add(af);
			}

			ArrayList<TextBoxFormat> selectedTextBoxes = new ArrayList<TextBoxFormat>();
			for(TextBoxFormat tb :DrawingList.TextBoxes) {
				Rectangle2D rect = (Rectangle2D) tb.getTransformedTextBoxRect(imageScale).clone();
				if(rect.contains(selectPoint))
					selectedTextBoxes.add(tb);
			}

			if(selectedArrows.size() + selectedTextBoxes.size() > 1) {
				editView = new EditView(this, selectedArrows, selectedTextBoxes, p);
				currentMode = EditDrawingMode.selectMode;
			}
			else {
				if(selectedArrows.size() == 1) {
					arrowView = new ArrowView(selectedArrows.get(0), imageScale);
					currentMode = EditDrawingMode.setArrowLayout;
					repaint();
				}
				else if(selectedTextBoxes.size() == 1) {
					textBoxView = new TextBoxView(this, selectedTextBoxes.get(0), imageScale);
					currentMode = EditDrawingMode.setTextBoxLayout;
					repaint();
				}
			}
		}
		if(currentMode == EditDrawingMode.selectMode) {
			if(editView.select(p)) {
				BaseFormat format = editView.getSelectedFormat();
				if(format == null) {
					currentMode = EditDrawingMode.None;
					changeCursor(Cursor.DEFAULT_CURSOR);
					repaint();
				}
				else if(format.getClass().getName().equals(TextBoxFormat.class.getName())) {
					textBoxView = new TextBoxView(this, (TextBoxFormat) format, imageScale);
					currentMode = EditDrawingMode.setTextBoxLayout;
					repaint();
				}
				else if(format.getClass().getName().equals(ArrowFormat.class.getName())) {
					arrowView = new ArrowView((ArrowFormat) format, imageScale);
					currentMode = EditDrawingMode.setArrowLayout;
					repaint();
				}
			}
		}
	}

	private void textBoxClickToDo(Point p, int clickcount) {
		if(clickcount == 1) {
			if(currentMode == EditDrawingMode.setTextBoxStart) {
				textBoxView.setPosition(p, imageScale);
				currentMode = EditDrawingMode.setTextBoxLayout;
				repaint();
			}
			else if(currentMode == EditDrawingMode.setTextBoxLayout) {
				textBoxView.select(p, imageScale);
				if(textBoxView.isLayoutConfirmed()) {
					currentMode = EditDrawingMode.None;
					changeCursor(Cursor.DEFAULT_CURSOR);
					setTextBoxElement();
					xMargin = 40;
					yMargin = 40;
					textBoxView = null;
				}
				repaint();
			}
		}
	}

	public void changeCursor(int cursor) {
		this.setCursor(Cursor.getPredefinedCursor(cursor));
	}	
}