package format;


import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import style.MarkStyle;
import enums.Enums.HorizontalAlign;
import enums.Enums.VerticalAlign;

public class MarkFormat extends WordFormat {

	@Override
	public Rectangle2D.Float getBounds() {
		return border.getBorderBounds();
	}


	private int start, end;

	private VerticalAlign verticalPosition;
	private HorizontalAlign horizontalPosition;
	private float margin, borderWidth;
	private BorderSetting border;

	public BorderSetting getBorder() {
		return border;
	}

	public float getMargin() {
		return margin;
	}

	public VerticalAlign getVerticalPosition() {
		return verticalPosition;
	}

	public HorizontalAlign getHorizontalPosition() {
		return horizontalPosition;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public void setEnd(int end) {
		this.end = end;
	}


	public MarkFormat(String text, MarkStyle style, int count) {
		super(text, -1, style);
		start = count;
		margin = style.margin;
		verticalPosition = style.verticalPosition;
		horizontalPosition = style.horizontalPosition;
		borderWidth = style.borderSetting.getLineWidth();
		border = style.borderSetting.clone();
	}

	public void setMark(ArrayList<WordFormat> wordList) {

			float maxHeight = 0;
			ArrayList<Rectangle2D.Float> rectList = new ArrayList<Rectangle2D.Float>();

			Rectangle2D.Float rect = new Rectangle2D.Float();
			if(start > -1) {
				start += 1;
				rect.x = wordList.get(start).getLocation().x;
				rect.y = wordList.get(start).getLocation().y;
				rect.width = wordList.get(start).getWidth();
				maxHeight = wordList.get(start).getHeight();
			} else {
				rect.x = wordList.get(0).getLocation().x;
				rect.y = wordList.get(0).getLocation().y;
				rect.width = 0;
				maxHeight = wordList.get(0).getHeight();
			}

			for(int i = start + 1; i < end; i++) {
				WordFormat wordTarget = wordList.get(i);
				rect.width += wordTarget.getWidth();
				maxHeight = Math.max(wordTarget.getHeight(), maxHeight);
				if(i < wordList.size() - 1) {
					if(wordTarget.getLineNumber() != wordList.get(i + 1).getLineNumber()) {
						rect.height = maxHeight;
						rectList.add(rect);
						wordTarget = wordList.get(i + 1);
						rect = new Rectangle2D.Float();
						rect.x = wordTarget.getLocation().x;
						rect.y = wordTarget.getLocation().y;
						rect.width = 0;
						rect.height = Math.max(wordTarget.getHeight(), rect.height);
						maxHeight = 0;
					}
				}
			}

			rect.height = maxHeight;
			rectList.add(rect);

			float markX, markY;
			markX = 0;
			markY =0;

			switch(getVerticalPosition()) {
				case SUPER:
					markY =  rectList.get(0).y - getMargin();
					break;
				case SUB:
					markY =  rectList.get(0).y + getMargin();
				break;
			}

			switch (getHorizontalPosition()) {
				case LEFT:
					markX = rectList.get(0).x;
					break;
				case CENTER:
					float length = 0;

					for(Rectangle2D.Float targetRect : rectList) {
						length += targetRect.width;
					}
					length /= 2;

					int index = 0;
					for(Rectangle2D.Float targetRect : rectList) {
						length -= targetRect.width;
						if(length <= 0) {
							length += targetRect.width;
							break;
						}
						index += 1;
					}

					markX = rectList.get(index).x + length - getWidth() / 2;
					break;
				case RIGHT:
					markX = rectList.get(rectList.size()-1).x  + rectList.get(rectList.size()-1).width - getWidth();
					break;
			}

			setLocation(markX,markY);
			border.setMarkBorderArea(this);
	}


	public Graphics2D drawMark(Graphics2D g2) {
		if(border.isFillBackground()) {
			g2.setColor(border.getBackgroundColor());
			g2.fill(border.getBorderFillShape());
		}

		if(border.isDrawBorder() && border.getLineWidth() > 0) {
			g2.setColor(border.getBorderColor());
			g2.setStroke(new BasicStroke(border.getLineWidth()));
			g2.draw(border.getBorderRound());
		}

		g2.setColor(getFontColor());
		g2.fill(getTextShape());

		return g2;
	}


}
