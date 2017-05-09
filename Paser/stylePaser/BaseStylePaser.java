package stylePaser;

import java.awt.Font;

import org.jdom.Element;

import enums.Enums.SpanVerticalAlign;
import enums.Enums.VerticalAlign;
import enums.LDAttributes;
import style.BaseStyle;

public class BaseStylePaser {

	protected Element element;
	protected BaseStyle baseStyle;

	public BaseStylePaser(Element element, BaseStyle spanStyle) {
		this.element = element;
		this.baseStyle = spanStyle;
	}

	public void parse() {
		setID();
		setFontFamily();
		setFontFamily2Byte();
		setFontSize();
		setFontStyle();
		setFontColor();
		setBorder();
		setTextDecration();
		setVerticalAlign();
		baseStyle.xmlElement = element;
	}

	protected void setID() {
		baseStyle.id = element.getAttributeValue(LDAttributes.ID);
	}

	protected void setFontFamily() {
		baseStyle.fontFamily = element.getAttributeValue(LDAttributes.FontFamily);
		if(baseStyle.fontFamily != null) baseStyle.overrideFontFamily = true;
		else baseStyle.overrideFontFamily = false;
	}

	protected void setFontFamily2Byte() {
		baseStyle.fontFamily2Byte = element.getAttributeValue(LDAttributes.FontFamily2Byte);
		if(baseStyle.fontFamily2Byte != null) baseStyle.overrideFontFamily2Byte = true;
		else baseStyle.overrideFontFamily2Byte = false;
	}

	protected void setFontStyle() {
		int fontStyle = 0;

		String style = element.getAttributeValue(LDAttributes.FontStyle);
		if(style != null) {
			style = style.toLowerCase();
			if(style.contains("bold"))
			{
				fontStyle += Font.BOLD;
			}
			if(style.contains("italic"))
			{
				fontStyle += Font.ITALIC;
			}
			baseStyle.fontStyle = fontStyle;
			baseStyle.overrideFontStyle = true;
		} else {
			baseStyle.overrideFontStyle = false;
		}
	}

	protected void setFontSize() {
		try {
			if(element.getAttribute(LDAttributes.FontSize) != null) {
				baseStyle.fontSize = element.getAttribute(LDAttributes.FontSize).getFloatValue();
				baseStyle.overrideFontSize = true;
			} else {
				baseStyle.overrideFontSize = false;
			}
		} catch (Exception ex) {
			baseStyle.fontSize = -1;
			baseStyle.overrideFontSize = true;
		}
	}

	protected void setFontColor() {
		baseStyle.fontColor = AttributeToObjectConverter.getColor(element, LDAttributes.FontColor, null);
		if(baseStyle.fontColor != null) baseStyle.overrideFontColor = true;
		else baseStyle.overrideFontColor = false;
	}

	protected void setBorder() {
		AttributeToObjectConverter.setBorderSetting(element, baseStyle.borderSetting);
	}

	protected void setTextDecration() {
		if(element.getAttribute(LDAttributes.TextDecoration) != null) {
			baseStyle.drawDecoration = true;

			baseStyle.decoration = AttributeToObjectConverter.getTextAttribute(element);

			baseStyle.decorationColor = AttributeToObjectConverter.getColor(element, LDAttributes.TextDecorationColor, LDAttributes.TextDecorationColorOpacity);
		} else {
			baseStyle.drawDecoration = false;
		}
	}

	private void setVerticalAlign() {
		if(element.getAttribute(LDAttributes.VerticalAlign) != null) {
			baseStyle.useVerticalAlign = true;
			if(element.getAttributeValue(LDAttributes.VerticalAlign).equalsIgnoreCase(VerticalAlign.SUPER.toString()))
				baseStyle.spanVerticalAlign = SpanVerticalAlign.SUPER;
			else if(element.getAttributeValue(LDAttributes.VerticalAlign).equalsIgnoreCase(VerticalAlign.SUB.toString()))
				baseStyle.spanVerticalAlign = SpanVerticalAlign.SUB;
			else
				baseStyle.spanVerticalAlign = SpanVerticalAlign.NONE;
		} else {
			baseStyle.useVerticalAlign = false;
		}
	}
}
