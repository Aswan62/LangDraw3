package render;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import enums.Enums.SpanVerticalAlign;
import format.ArrowFormat;
import format.BorderSetting;
import format.MarkFormat;
import format.TextBoxFormat;
import format.WordFormat;

import itemList.DrawingList;

public class DetectLocation {

	static public Rectangle2D.Float setBorderLocation(Rectangle2D.Float imageBounds, ArrayList<BorderSetting> borderList) {
		if(borderList != null) {
			for(BorderSetting border :borderList) {
				border = border.setBorderArea();
				if(border != null) Rectangle2D.Float.union(imageBounds, border.getBorderBounds(), imageBounds);
			}
		}
		return imageBounds;
	}

	static public Rectangle2D.Float setWordFormatLocation(Rectangle2D.Float imageBounds, ArrayList<WordFormat> wordList) {
		if(wordList != null)
		{
			Point2D.Float p = new Point2D.Float();
			float lineAscent = 0;
			WordFormat target;

			for(int i = 0; i < wordList.size(); i++) {
				target = wordList.get(i);
				Point2D.Float location = (Point2D.Float)p.clone();

				if(target.getVerticalAlign() == SpanVerticalAlign.SUPER) {
					if(i != 0) {
						WordFormat dummy = wordList.get(i-1);
						location.y -= dummy.getAscent()/2;
					} else {
						location.y -= target.getAscent()/2;
					}
				}
				else if(target.getVerticalAlign() == SpanVerticalAlign.SUB){
					location.y += target.getAscent()/2;
				}

				target.setLocation(location);
				Rectangle2D.Float.union(imageBounds, target.getBounds(), imageBounds);

				p.x += target.getWidthWithBorder();
				lineAscent = Math.max(lineAscent, target.getMaxAscent());

				if(i != wordList.size() - 1) {
					if(target.getLineNumber() != wordList.get(i + 1).getLineNumber()) {
						p.y += lineAscent;
						for(int j = target.getLineNumber(); j < wordList.get(i + 1).getLineNumber(); j++) {
							p.y += DrawingList.LineWidths.get(j).getLineHight();
						}
						p.x = 0;
						lineAscent = 0;
					}
				}
			}
		}

		if(wordList != null) {
			for(int i = 0; i < wordList.size(); i++) {
				WordFormat target = wordList.get(i);
				Rectangle2D.Float.union(imageBounds, target.getBounds(), imageBounds);
			}
		}
		return imageBounds;
	}

	static public Rectangle2D.Float setMarkFormatLocation(Rectangle2D.Float imageBounds, ArrayList<WordFormat> wordList, ArrayList<MarkFormat> markList) {

		if(wordList != null && markList != null)
		{
			for(MarkFormat mf: markList) {
				mf.setMark(wordList);
				Rectangle2D.Float.union(imageBounds, mf.getBounds(), imageBounds);
			}
		}

		return imageBounds;
	}

	static public Rectangle2D.Float setArrowLocation(Rectangle2D.Float imageBounds, ArrayList<ArrowFormat> arrowList) {

		for(ArrowFormat af: arrowList) {
			Rectangle2D.Float.union(imageBounds, af.getBounds(), imageBounds);
		}

		return imageBounds;
	}

	static public Rectangle2D.Float setTextBoxLocation(Rectangle2D.Float imageBounds, ArrayList<TextBoxFormat> textBoxList) {

		for(TextBoxFormat tf: textBoxList) {
			tf.setLocation();
			Rectangle2D.Float boder = tf.getTextBoxRect();
			if(tf.textBoxBorder != null) {
				tf.textBoxBorder.setTextBoxBorderArea(tf);
				boder = tf.textBoxBorder.getBorderBounds();
			}
			Rectangle2D.Float.union(imageBounds, boder, imageBounds);
		}

		return imageBounds;
	}
}
