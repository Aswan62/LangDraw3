package format;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import enums.Enums.*;

import render.ImageCanvas;
import render.RenderQuality;
import style.BaseStyle;

public class WordFormat extends BaseFormat {

	final static protected String prohibitedCharsStart = "!%),.:;?]}¢°’”‰′″℃、。々〉》」』】〕゛゜ゝゞ・ヽヾ！％），．：；？］｝｡｣､･ﾞﾟ￠";
	final static protected String prohibitedCharsEnd = "$([\\{£¥‘“〈《「『【〔＄（［｛｢￡￥";

	protected BaseStyle style;
	static private BufferedImage im = new BufferedImage(100, 100, BufferedImage.TYPE_4BYTE_ABGR);
	protected AttributedCharacterIterator attributedText;
	protected Font font, font2Byte;
	protected Color fontColor, decorationColor;
	protected TextDecoration decoration;
	protected Point2D.Float location = new Point2D.Float();
	public void setLocation(Point2D.Float p) {
		setLocation(p.x, p.y);
	}

	public void setLocation(float x, float y) {
		this.location = new Point2D.Float(x, y);
	}

	public Point2D.Float getLocation() {
		return location;
	}

	public Rectangle2D.Float getBounds() {
		return new Rectangle2D.Float(location.x, location.y - getMaxAscent(), this.width, this.height);
	}

	public Rectangle2D.Float getTransformedBounds() {
		return new Rectangle2D.Float((float)(location.x * ImageCanvas.getImageScale().getScaleX()), (float)((location.y - getMaxAscent()) * ImageCanvas.getImageScale().getScaleY()), (float)(width * ImageCanvas.getImageScale().getScaleX()), (float)(height * ImageCanvas.getImageScale().getScaleY()));
	}


	protected Shape textShape;
	protected int lineNumber;
	protected int dummyLineNumber;

	protected float width;
	protected float leftWidth;
	protected float space;
	protected float height;
	protected float ascent;
	protected float maxAscent;
	protected float descent;
	protected float underlinePosition;
	protected float underlineWidth;
	protected float strikePosition;
	protected float strikeWidth;
	protected float overlinePosition;
	protected float overlineWidth;
	protected boolean isStartProhibited, isEndProhibited;
	protected int charIndex;

	public WordFormat(char c, int lineNumber, BaseStyle style) {
		initilize(String.valueOf(c), lineNumber, style);

		if(prohibitedCharsStart.indexOf(c) != -1)
			isStartProhibited = true;
		else
			isStartProhibited = false;

		if(prohibitedCharsEnd.indexOf(c) != -1)
			isEndProhibited = true;
		else
			isEndProhibited = false;
	}

	public WordFormat(String text, int lineNumber, BaseStyle style) {
		initilize(text, lineNumber, style);
		isStartProhibited = false;
		isEndProhibited = false;
	}

	protected void initilize(String text, int lineNumber, BaseStyle style) {
		this.style = style;
		this.lineNumber = lineNumber;
		this.dummyLineNumber = lineNumber;
		this.fontColor = style.fontColor;
		this.decoration = style.decoration;
		this.decorationColor = style.decorationColor;
		font = new Font(style.fontFamily, style.fontStyle, 30);
		font = font.deriveFont(style.fontSize);

		font2Byte =  new Font(style.fontFamily2Byte, style.fontStyle, 30);
		font2Byte = font2Byte.deriveFont(style.fontSize);

		FontRenderContext frc = new FontRenderContext(null, true, true);

		AttributedString attrStr = new AttributedString(text);
		for(int i = 0; i < text.length(); i++) {
        	char c = text.charAt( i );
        	if(( c<='\u007e' ) || ( c=='\u00a5' ) || ( c=='\u203e' )|| ( c>='\uff61' && c<='\uff9f' ))
        	{
        		attrStr.addAttribute(TextAttribute.FONT, font, i, i + 1);

        	}
        	else
        	{
        		attrStr.addAttribute(TextAttribute.FONT, font2Byte, i, i + 1);

        	}
		}
		if(text.length() > 0)
			attrStr.addAttribute(TextAttribute.FOREGROUND, fontColor, 0, text.length());
		this.attributedText = attrStr.getIterator();
		if(text.length() > 0)
			this.textShape = new TextLayout(this.attributedText, frc).getOutline(null);
		else
			this.textShape = null;

		setParameter();
	}

	protected void setParameter(){
		Graphics2D g = (Graphics2D)im.getGraphics();
		g.setRenderingHints(RenderQuality.getCurrentQuality());
		FontMetrics fm, fm2byte;
		LineMetrics lineMetrics;
		Rectangle2D.Float bounds;
		float width;
		width = 0;
		leftWidth = 0;
		fm = g.getFontMetrics(font);
		fm2byte = g.getFontMetrics(font2Byte);
		for(int i = 0; i < attributedText.getEndIndex() ; i++) {
			char c = attributedText.setIndex(i);
			String str = String.valueOf(c);
			if(( c<='\u007e' ) || ( c=='\u00a5' ) || ( c=='\u203e' )|| ( c>='\uff61' && c<='\uff9f' )) {
				bounds = (Rectangle2D.Float) fm.getStringBounds(str, g);
				lineMetrics = font.getLineMetrics(str, 0, 1, g.getFontRenderContext());
				this.maxAscent = Math.max(this.maxAscent, fm.getMaxAscent());
				this.height = Math.max(this.height, fm.getHeight());
			} else {
				bounds = (Rectangle2D.Float) fm2byte.getStringBounds(str, g);
				lineMetrics = font2Byte.getLineMetrics(str, 0, 1, g.getFontRenderContext());
				this.maxAscent = Math.max(this.maxAscent, fm2byte.getMaxAscent());
				this.height = Math.max(this.height, fm2byte.getHeight());
			}

			width += bounds.width;
			this.underlinePosition = lineMetrics.getUnderlineOffset();
			this.underlineWidth = lineMetrics.getUnderlineThickness();
			this.strikePosition = lineMetrics.getStrikethroughOffset();
			this.strikeWidth = lineMetrics.getStrikethroughThickness();
			this.overlinePosition = - lineMetrics.getAscent();
			this.overlineWidth = lineMetrics.getUnderlineThickness();
		}

		this.ascent = Math.max(fm.getMaxAscent(), fm2byte.getMaxAscent());
		this.descent = Math.max(fm.getMaxDescent(), fm.getMaxDescent());
		this.width = width;
	}


	public Font getFont() {
		return font;
	}

	public Font getFont2Byte() {
		return font2Byte;
	}

	public Color getFontColor() {
		return fontColor;
	}
	public TextDecoration getDecoration() {
		return decoration;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void resetLineNumber() {
		lineNumber = dummyLineNumber;
	}

	public float getWidth() {
		return width;
	}

	public float getWidthWithBorder() {
		return width + leftWidth;
	}

	public void addLeftBorderWidth(float width) {
		leftWidth += width + width / 2;
	}

	public float getHeight() {
		return height;
	}

	public float getAscent() {
		return ascent;
	}

	public float getMaxAscent() {
		return maxAscent;
	}

	public float getDescent() {
		return descent;
	}

	public float getUnderlinePosition() {
		return underlinePosition;
	}

	public float getUnderlineWidth() {
		return underlineWidth;
	}

	public float getStrikePosition() {
		return strikePosition;
	}

	public float getStrikeWidth() {
		return strikeWidth;
	}

	public float getOverlinePosition() {
		return overlinePosition;
	}

	public float getOverlineWidth() {
		return overlineWidth;
	}

	public Color getDecorationColor() {
		return decorationColor;
	}

	public boolean isStartProhibited() {
		return isStartProhibited;
	}

	public boolean isEndProhibited() {
		return isEndProhibited;
	}

	public boolean isUseVerticalAlign() {
		return style.useVerticalAlign;
	}

	public SpanVerticalAlign getVerticalAlign() {
		return style.spanVerticalAlign;
	}

	public WordFormat getDividedWordFormat(float targetWidth) {
		int startIndex = this.attributedText.getBeginIndex();
		char targetChar = this.attributedText.last();
		int isProhabited = prohibitedCharsStart.indexOf(targetChar);
		String dividedAfterText = String.valueOf(targetChar);

		while (isProhabited != -1) {
			if(startIndex == this.attributedText.getIndex())
				return null;
			else {
				targetChar = this.attributedText.previous();
				isProhabited = prohibitedCharsStart.indexOf(targetChar);
				dividedAfterText = String.valueOf(targetChar).concat(dividedAfterText);
			}
		}

		int currentIndex = this.attributedText.getIndex()-1;
		String dividedBeforeText = String.valueOf(this.attributedText.first());
		for(int i = this.attributedText.getBeginIndex(); i < currentIndex ; i++ ) {
			dividedBeforeText = dividedBeforeText.concat(String.valueOf(this.attributedText.next()));
		}
		this.initilize(dividedBeforeText, lineNumber, this.style);

		return new WordFormat(dividedAfterText, lineNumber + 1, style);
	}

	public int getCharIndex() {
		return charIndex;
	}

	public void setCharIndex(int charIndex) {
		this.charIndex = charIndex;
	}

	public int getTextLength() {
		return attributedText.getEndIndex();
	}

	public Shape getTextShape() {
		if(textShape != null)
			return AffineTransform.getTranslateInstance(this.location.x, this.location.y).createTransformedShape(textShape);
		else
			return new Rectangle(0,0,0,0);
	}

	public Line2D.Float getOverline() {
		return new Line2D.Float(location.x, location.y + getOverlinePosition(), location.x + getWidth(), location.y + getOverlinePosition());
	}

	public Line2D.Float getUnderline() {
		return new Line2D.Float(location.x, location.y + getUnderlinePosition(), location.x + getWidth(), location.y + getUnderlinePosition());
	}

	public Line2D.Float getStrikeline() {
		return new Line2D.Float(location.x, location.y + getStrikePosition(), location.x + getWidth(), location.y + getStrikePosition());
	}
}
