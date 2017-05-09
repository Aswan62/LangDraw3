package stylePaser;

import java.awt.Color;
import java.awt.geom.Point2D;

import org.jdom.Attribute;
import org.jdom.Element;


import enums.Enums.BorderForm;
import enums.Enums.TextDecoration;
import enums.LDAttributes;
import enums.Padding;
import format.BorderSetting;

public class AttributeToObjectConverter {


	static public Color getColor(Element element, String colorTagName, String opacityTagName)
	{
		Color color = new Color(0);
		int alpha = 255;
		try {
			color = Color.decode(element.getAttributeValue(colorTagName));
		} catch (Exception e) {
			color = null;
		}

		try {
			if(opacityTagName != null) alpha = (int)(element.getAttribute(opacityTagName).getFloatValue() * 255);
		} catch (Exception e) {
			alpha = 255;
		}
		if(alpha > 255) alpha = 255;
		if(color != null) color = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);

		return color;
	}

	static public void setBorderSetting(Element element, BorderSetting border)
	{
		float width;
		try {
			width = element.getAttribute(LDAttributes.BorderWidth).getFloatValue();
		} catch (Exception ex) {
			width = 0;
		}

		Color borderColor = getColor(element, LDAttributes.BorderColor, LDAttributes.BorderColorOpacity);
		Color backgroundColor = getColor(element, LDAttributes.BackgroundColor, LDAttributes.BackgroundColorOpacity);

		if(backgroundColor != null){
			border.setFillBackground(true);
			border.setBackgroundColor(backgroundColor);
		} else {
			border.setFillBackground(false);
		}

		if(width > 0 && borderColor != null) {
			border.setDrawBorder(true);
			border.setBorderColor(borderColor);
			border.setWidth(width);
		}
		else {
			border.setDrawBorder(false);
		}

		BorderForm leftForm;
		String leftFormText = element.getAttributeValue(LDAttributes.BorderLeftForm);
		if(leftFormText != null) {
			if(leftFormText.equalsIgnoreCase(BorderForm.LINEAR.toString())){
				leftForm = BorderForm.LINEAR;
			}
			else if(leftFormText.equalsIgnoreCase(BorderForm.CURVE1.toString())){
				leftForm = BorderForm.CURVE1;
			}
			else if(leftFormText.equalsIgnoreCase(BorderForm.CURVE2.toString())){
				leftForm = BorderForm.CURVE2;
			}
			else if(leftFormText.equalsIgnoreCase(BorderForm.TURNED.toString())){
				leftForm = BorderForm.TURNED;
			} 
			else if(leftFormText.equalsIgnoreCase(BorderForm.NONE.toString())){
					leftForm = BorderForm.NONE;
			} else {
				leftForm = BorderForm.LINEAR;
			}
		} else leftForm = BorderForm.LINEAR;

		border.setBorderLeftForm(leftForm);

		BorderForm rightForm;
		String rightFormText = element.getAttributeValue(LDAttributes.BorderRightForm);
		if(rightFormText != null) {
			if(rightFormText.equalsIgnoreCase(BorderForm.LINEAR.toString())){
				rightForm = BorderForm.LINEAR;
			}
			else if(rightFormText.equalsIgnoreCase(BorderForm.CURVE1.toString())){
				rightForm = BorderForm.CURVE1;
			}
			else if(rightFormText.equalsIgnoreCase(BorderForm.CURVE2.toString())){
				rightForm = BorderForm.CURVE2;
			}
			else if(rightFormText.equalsIgnoreCase(BorderForm.TURNED.toString())){
				rightForm = BorderForm.TURNED;
			}
			else if(rightFormText.equalsIgnoreCase(BorderForm.NONE.toString())){
				rightForm = BorderForm.NONE;
			} else {
				rightForm = BorderForm.LINEAR;
			}
		} else rightForm = BorderForm.LINEAR;

		border.setBorderRightForm(rightForm);
	}

	static public TextDecoration getTextAttribute(Element element)
	{
		TextDecoration result = null;
		String style = element.getAttributeValue(LDAttributes.TextDecoration);
		if(style != null) {
			style = style.toUpperCase();
			result = TextDecoration.valueOf(style);
		}

		return result;
	}

	static public float getFloat(Element element, String attributeName)
	{
		float result = 0;
		Attribute attribute = element.getAttribute(attributeName);
		if(attribute != null) {
			try {
				result = attribute.getFloatValue();
			} catch (Exception e) {
				result = Float.NaN;
			}
		} else {
			result = Float.NaN;
		}

		return result;
	}


	static public Point2D.Float getPoint2D(Element element, String attributeName)
	{
		Point2D.Float point = new Point2D.Float();
		String val = element.getAttributeValue(attributeName);
		if(val != null) {
			String[] coordinates = val.split(",");
			if(coordinates.length == 2) {
				float x = Float.parseFloat(coordinates[0]);
				float y = Float.parseFloat(coordinates[1]);
				point = new Point2D.Float(x, y);
			}
		}
		return point;
	}


	static public Padding getPadding(Element element)
	{
		Padding padding = new Padding();
		String val = element.getAttributeValue(LDAttributes.Padding);
		if(val != null) {
			String[] paddings = val.split(",");
			if(paddings.length == 1) {
				padding.top = Float.parseFloat(paddings[0]);
				padding.bottom = padding.top;
				padding.left = padding.top;
				padding.right = padding.top;
			}
			else if(paddings.length == 2) {
				padding.top = Float.parseFloat(paddings[0]);
				padding.bottom = padding.top;
				padding.left = Float.parseFloat(paddings[1]);
				padding.right = padding.left;
			}
			else if(paddings.length == 3) {
				padding.top = Float.parseFloat(paddings[0]);
				padding.left = Float.parseFloat(paddings[1]);
				padding.right = padding.left;
				padding.bottom = Float.parseFloat(paddings[2]);
			}
			else if(paddings.length == 4) {
				padding.top = Float.parseFloat(paddings[0]);
				padding.right = Float.parseFloat(paddings[1]);
				padding.bottom = Float.parseFloat(paddings[2]);
				padding.left = Float.parseFloat(paddings[3]);
			}
		}
		return padding;
	}
}
