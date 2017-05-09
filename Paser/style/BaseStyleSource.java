package style;

import java.awt.Color;
import java.awt.Font;

import org.jdom.Element;

import windows.StyleWindow;

import enums.Enums.SpanVerticalAlign;
import enums.FontStyles;
import enums.LDAttributes;
import enums.Enums.TextDecoration;

public class BaseStyleSource extends BaseStyle {
	public boolean isInitializing;
	private String tagName;

	public BaseStyleSource(String tagName) {
		super();
		this.tagName = tagName;
	}

	public void UpdateBaseStyleSource(BaseStyle baseStyle) {
		if(baseStyle != null) {
			this.isInitializing = false;
			this.tagName = baseStyle.xmlElement.getName();
			overrideFontFamily = baseStyle.overrideFontFamily;
			if(overrideFontFamily) fontFamily = baseStyle.fontFamily;
			else fontFamily = Font.SERIF;
			overrideFontFamily2Byte = baseStyle.overrideFontFamily2Byte;
			if(overrideFontFamily2Byte) fontFamily2Byte = baseStyle.fontFamily2Byte;
			else fontFamily2Byte = Font.SERIF;
			overrideFontSize = baseStyle.overrideFontSize;
			if(overrideFontSize) fontSize = baseStyle.fontSize;
			else fontSize = 30;
			overrideFontStyle = baseStyle.overrideFontStyle;
			if(overrideFontStyle) fontStyle = baseStyle.fontStyle;
			else fontStyle = Font.PLAIN;
			overrideFontColor = baseStyle.overrideFontColor;
			if(overrideFontColor) fontColor = baseStyle.fontColor;
			else fontColor = Color.BLACK;
			drawDecoration = baseStyle.drawDecoration;
			if(drawDecoration) decoration = baseStyle.decoration;
			else decoration = TextDecoration.UNDERLINE;
			decorationColor = baseStyle.decorationColor;
			useVerticalAlign = baseStyle.useVerticalAlign;
			if(useVerticalAlign) spanVerticalAlign = baseStyle.spanVerticalAlign;
			xmlElement = baseStyle.xmlElement;
			borderSetting = baseStyle.borderSetting.clone();
		}
	}

	public String getId() {
		return id;
	}


	public String getFontFamily() {
		return fontFamily;
	}

	public String getFontFamily2Byte() {
		return fontFamily2Byte;
	}

	public float getFontSize() {
		return fontSize;
	}
	public int getFontStyle() {
		return fontStyle;
	}

	public Color getFontColor() {
		return fontColor;
	}


	public TextDecoration getDecoration() {
		return decoration;
	}

	public Color getDecorationColor() {
		return decorationColor;
	}

	public Element getXmlElement() {
		return xmlElement;
	}

	public SpanVerticalAlign getSpanVertivalAlign() {
		return this.spanVerticalAlign;
	}

	public boolean isOverrideFontFamily() {
		return overrideFontFamily;
	}

	public boolean isOverrideFontFamily2Byte() {
		return overrideFontFamily2Byte;
	}

	public boolean isOverrideFontSize() {
		return overrideFontSize;
	}


	public boolean isOverrideFontColor() {
		return overrideFontColor;
	}

	public boolean isOverrideFontStyle() {
		return overrideFontStyle;
	}

	public boolean isDrawTextDecoration() {
		return drawDecoration;
	}

	public boolean isUseVertivalAlign() {
		return useVerticalAlign;
	}

	public String getName() {
		return tagName;
	}

	public void setDrawTextDecoration(boolean drawTextDecoration) {
		this.drawDecoration = drawTextDecoration;
		replaceToNewElement();
	}

	public void setID(String id) {
		this.id = id;
		replaceToNewElement();
	}

	public  void setOverrideFontFamily(boolean overrideFontFamily) {
		this.overrideFontFamily = overrideFontFamily;
	}

	public void setFontFamily(String fontFamily) {
		if(fontFamily != null) this.fontFamily = fontFamily;
		replaceToNewElement();
	}

	public void setOverrideFontFamily2Byte(boolean overrideFontFamily2Byte) {
		this.overrideFontFamily2Byte = overrideFontFamily2Byte;
	}

	public void setFontFamily2Byte(String fontFamily2Byte) {
		if(fontFamily2Byte != null)	this.fontFamily2Byte = fontFamily2Byte;
		replaceToNewElement();
	}

	public void setOverrideFontSize(boolean overrideFontSize) {
		this.overrideFontSize = overrideFontSize;
	}

	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
		replaceToNewElement();
	}

	public void setOverrideFontStyle(boolean overrideFontStyle) {
		this.overrideFontStyle = overrideFontStyle;
	}

	public void setFontStyle(int fontStyle) {
		this.fontStyle = fontStyle;
		replaceToNewElement();
	}

	public void setOverrideFontColor(boolean overrideFontColor) {
		this.overrideFontColor = overrideFontColor;
	}

	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
		replaceToNewElement();
	}

	public void setTextDecoration(TextDecoration textDecoration) {
		this.decoration = textDecoration;
		replaceToNewElement();
	}

	public void setDecorationColor(Color decorationColor) {
		this.decorationColor = decorationColor;
		replaceToNewElement();
	}

	public void setUseVerticalAlign(boolean useAlign) {
		this.useVerticalAlign = useAlign;
	}


	public void setVerticalAlign(SpanVerticalAlign verticalAlign) {
		this.spanVerticalAlign = verticalAlign;
		replaceToNewElement();
	}

	public void setDrawBorder(boolean drawBorder) {
		this.borderSetting.setDrawBorder(drawBorder);
		replaceToNewElement();
	}

	public void setFillBackground(boolean fillBackground) {
		this.borderSetting.setFillBackground(fillBackground);
		replaceToNewElement();
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.borderSetting.setBackgroundColor(backgroundColor);
		replaceToNewElement();
	}

	public void setBorderColor(Color borderColor) {
		this.borderSetting.setBorderColor(borderColor);
		replaceToNewElement();
	}

	public void setBorderWidth(float borderWidth) {
		this.borderSetting.setWidth(borderWidth);
		replaceToNewElement();
	}

	public void setBorderLeftForm(int selectedIndex) {
		this.borderSetting.setBorderLeftForm(selectedIndex);
		replaceToNewElement();
	}

	public void setBorderRightForm(int selectedIndex) {
		this.borderSetting.setBorderRightForm(selectedIndex);
		replaceToNewElement();
	}

	public Element getOldElement() {
		return xmlElement;
	}

	public Element getNewElement() {
		Element newElement = null;
		if(xmlElement != null) newElement = (Element) xmlElement.clone();
		else newElement = new Element(tagName);

		if(id != null) newElement.setAttribute(LDAttributes.ID, id);

		if(overrideFontFamily) newElement.setAttribute(LDAttributes.FontFamily, fontFamily);
		else newElement.removeAttribute(LDAttributes.FontFamily);

		if(overrideFontFamily2Byte) newElement.setAttribute(LDAttributes.FontFamily2Byte, fontFamily2Byte);
		else newElement.removeAttribute(LDAttributes.FontFamily2Byte);

		if(overrideFontSize) newElement.setAttribute(LDAttributes.FontSize, Float.toString(fontSize));
		else newElement.removeAttribute(LDAttributes.FontSize);

		if(overrideFontStyle) {
			switch (fontStyle) {
				case Font.PLAIN:
					newElement.setAttribute(LDAttributes.FontStyle, FontStyles.Plain);
					break;
				case Font.BOLD:
					newElement.setAttribute(LDAttributes.FontStyle, FontStyles.Bold);
					break;
				case Font.ITALIC:
					newElement.setAttribute(LDAttributes.FontStyle, FontStyles.Italic);
					break;
				case Font.ITALIC + Font.BOLD:
					newElement.setAttribute(LDAttributes.FontStyle, FontStyles.BoldItalic);
					break;
			}
		} else newElement.removeAttribute(LDAttributes.FontStyle);

		if(overrideFontColor) newElement.setAttribute(LDAttributes.FontColor, convertFromColorToString(fontColor));
		else newElement.removeAttribute(LDAttributes.FontColor);

		if(isFillBackground()){
			newElement.setAttribute(LDAttributes.BackgroundColor, convertFromColorToString(getBackgroundColor()));
			newElement.setAttribute(LDAttributes.BackgroundColorOpacity, getOpacityToString(getBackgroundColor()));
		} else {
			newElement.removeAttribute(LDAttributes.BackgroundColor);
			newElement.removeAttribute(LDAttributes.BackgroundColorOpacity);
		}


		if(drawDecoration) {
			newElement.setAttribute(LDAttributes.TextDecoration, decoration.toString());
			newElement.setAttribute(LDAttributes.TextDecorationColor, convertFromColorToString(decorationColor));
			newElement.setAttribute(LDAttributes.TextDecorationColorOpacity, getOpacityToString(decorationColor));
		} else {
			newElement.removeAttribute(LDAttributes.TextDecoration);
			newElement.removeAttribute(LDAttributes.TextDecorationColor);
			newElement.removeAttribute(LDAttributes.TextDecorationColorOpacity);
		}

		if(useVerticalAlign)  newElement.setAttribute(LDAttributes.VerticalAlign, spanVerticalAlign.toString());
		else newElement.removeAttribute(LDAttributes.VerticalAlign);

		if(borderSetting != null) newElement = this.borderSetting.setAttribute(newElement);

		return newElement;
	}

	protected void replaceToNewElement() {
		if(!isInitializing) {
			StyleWindow.editByTextInput = false;
			StyleSetter.setStyleValue(getOldElement(),getNewElement());
		}
	}

	private static String convertFromColorToString(Color color) {
		String result = "";
		if(color != null) result = "#" + Integer.toHexString(color.getRGB() & 0x00ffffff).toUpperCase();
		return result;
	}

	private static float getOpacityValue(Color color) {
		if(color != null) return (float)Math.round((float)color.getAlpha() / 255 * 10) / 10;
		else return 0;
	}

	private static String getOpacityToString(Color color) {
		return Float.toString(getOpacityValue(color));
	}

	public int getBackgroundAlpha() {
		if(this.borderSetting.getBackgroundColor() != null)
			return this.borderSetting.getBackgroundColor().getAlpha();
		else
			return 0;
	}

	public Color getBackgroundColor() {
		if(this.borderSetting != null)
			return this.borderSetting.getBackgroundColor();
		else
			return null;
	}

	public float getBorderWidth() {
		return this.borderSetting.getLineWidth();
	}

	public Color getBorderColor() {
		return this.borderSetting.getBorderColor();
	}

	public int getBorderAlpha() {
		if(this.borderSetting.getBorderColor() != null)
			return this.borderSetting.getBorderColor().getAlpha();
		else
			return 0;
	}

	public boolean isDrawBorder() {
		return borderSetting.isDrawBorder();
	}

	public int getBorderLeftFormIndex() {
		return borderSetting.getBorderLeftFormIndex();
	}

	public int getBorderRightFormIndex() {
		return borderSetting.getBorderRightFormIndex();
	}

	public boolean isFillBackground() {
		return borderSetting.isFillBackground();
	}
}
