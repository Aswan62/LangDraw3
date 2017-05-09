package render;

import itemList.StyleList;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import enums.Enums.TextDecoration;
import format.ArrowFormat;
import format.BorderSetting;
import format.MarkFormat;
import format.TextBoxFormat;
import format.WordFormat;



public class DrawWordAndMark {

	public static Graphics fillBackground(Rectangle2D.Float rect, Graphics2D g2) {
		g2.translate(-rect.x, -rect.y);
		g2.setRenderingHints(RenderQuality.getCurrentQuality());
		g2.setColor(StyleList.BodyStyle.borderSetting.getBackgroundColor());
		g2.fill(rect);
		return g2;
	}

	public static Graphics drawWords(ArrayList<WordFormat> words, Graphics2D g2) {
		if(words != null) {
			for(WordFormat wf: words) {
				try {
					g2.setColor(wf.getFontColor());
					g2.fill(wf.getTextShape());

					g2.setColor(wf.getDecorationColor());
					if(wf.getDecoration() == TextDecoration.OVERLINE) {
						g2.setStroke(new BasicStroke(wf.getOverlineWidth(),BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER));
						g2.draw(wf.getOverline());
					}
					else if(wf.getDecoration() == TextDecoration.STRIKE) {
						g2.setStroke(new BasicStroke(wf.getStrikeWidth(),BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER));
						g2.draw(wf.getStrikeline());
					}
					else if(wf.getDecoration() == TextDecoration.UNDERLINE) {
						g2.setStroke(new BasicStroke(wf.getUnderlineWidth(),BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER));
						g2.draw(wf.getUnderline());
					}
				}catch (Exception ex) { }
			}
		}
		return g2;
	}

	public static Graphics drawBorder(ArrayList<BorderSetting> borderList, Graphics2D g2) {
		if(borderList != null) {
			for(BorderSetting border: borderList) {
				try {
					if(border.isFillBackground()) {
						g2.setColor(border.getBackgroundColor());
						g2.fill(border.getBorderFillShape());
					}
				}catch (Exception ex) { }
			}

			for(BorderSetting border: borderList) {
				try {
					if(border.isDrawBorder() && border.getLineWidth() > 0) {
						g2.setColor(border.getBorderColor());
						g2.setStroke(new BasicStroke(border.getLineWidth()));
						g2.draw(border.getBorderRound());
					}
				}catch (Exception ex) { }
			}
		}
		return g2;
	}

	public static Graphics drawMarks(ArrayList<MarkFormat> marks, Graphics2D g2) {
		if(marks != null) {
			for(MarkFormat mf: marks) {
				g2 = mf.drawMark(g2);
			}
		}

		return g2;
	}

	public static Graphics drawArrows(ArrayList<ArrowFormat> arrows, Graphics2D g2) {
		if(arrows != null) {
			for(ArrowFormat af: arrows) {
				af.drawArrow(g2);
			}
		}

		return g2;
	}

	public static Graphics drawTextBoxes(ArrayList<TextBoxFormat> textBoxes, Graphics2D g2) {
		if(textBoxes != null) {
			for(TextBoxFormat textBoxFormat: textBoxes) {

				if(textBoxFormat.textBoxBorder.isFillBackground()) {
					g2.setColor(textBoxFormat.textBoxBorder.getBackgroundColor());
					g2.fill(textBoxFormat.textBoxBorder.getBorderFillShape());
				}

				for(WordFormat wordFormat: textBoxFormat.sourceTexts) {
					g2.setColor(wordFormat.getFontColor());
					g2.fill(wordFormat.getTextShape());

					g2.setColor(wordFormat.getDecorationColor());
					if(wordFormat.getDecoration() == TextDecoration.OVERLINE) {
						g2.setStroke(new BasicStroke(wordFormat.getOverlineWidth(),BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER));
						g2.draw(wordFormat.getOverline());
					}
					else if(wordFormat.getDecoration() == TextDecoration.STRIKE) {
						g2.setStroke(new BasicStroke(wordFormat.getStrikeWidth(),BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER));
						g2.draw(wordFormat.getStrikeline());
					}
					else if(wordFormat.getDecoration() == TextDecoration.UNDERLINE) {
						g2.setStroke(new BasicStroke(wordFormat.getUnderlineWidth(),BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER));
						g2.draw(wordFormat.getUnderline());
					}
				}

				if(textBoxFormat.textBoxBorder.getLineWidth() > 0) {
					g2.setColor(textBoxFormat.textBoxBorder.getBorderColor());
					g2.setStroke(new BasicStroke(textBoxFormat.textBoxBorder.getLineWidth()));
					g2.draw(textBoxFormat.textBoxBorder.getBorderRound());
				}
			}
		}

		return g2;
	}

	public static Graphics drawWaterMark(Rectangle2D.Float bounds, Graphics2D g2) {
		Font font = new Font("serif", Font.ITALIC + Font.BOLD, 100);
		FontRenderContext frc = new FontRenderContext(null, true, true);
		Shape shape = new TextLayout(" LangDraw 3 ", font, frc).getOutline(null);
		double heigt = shape.getBounds2D().getHeight();
		double width = shape.getBounds2D().getWidth();
		double theta = Math.atan(bounds.getHeight()/bounds.getWidth());
		double diagonal = Math.sqrt(Math.pow(bounds.getWidth(), 2)+Math.pow(bounds.getHeight(), 2));
		double scale =  width  / (diagonal);
		shape = AffineTransform.getScaleInstance(1F/scale, 1F/scale).createTransformedShape(shape);
		shape = AffineTransform.getRotateInstance(theta).createTransformedShape(shape);

		float diff = (float) (heigt*2F);
		int number = (int) Math.floor(diagonal/diff);
		shape = AffineTransform.getTranslateInstance(diff * number/2F, -diff * number/2F).createTransformedShape(shape);
		g2.setColor(new Color(0,0,0,10));
		for(int i = 0; i <= number; i++) {
			g2.fill(shape);
			shape = AffineTransform.getTranslateInstance(- diff, diff).createTransformedShape(shape);
		}

		return g2;
	}
}