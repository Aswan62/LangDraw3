package style;

import java.awt.Color;

import org.jdom.Element;

import windows.StyleWindow;

import enums.Enums.ArrowHeadType;
import enums.LDAttributes;
import enums.LDXMLTags;

public class ArrowStyleSource extends ArrowStyle{
	public boolean isInitializing;
	private String id;

	public ArrowStyleSource() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Element getNewElement() {
		Element newElement = null;
		if(xmlElement != null) newElement = (Element) xmlElement.clone();
		else newElement = new Element(LDXMLTags.BR);

		if(id != null) newElement.setAttribute(LDAttributes.ID, id);
		newElement.setAttribute(LDAttributes.ArrowWidth, Float.toString(width));
		newElement.setAttribute(LDAttributes.Radius, Float.toString(radius));
		newElement.setAttribute(LDAttributes.StartHeadType, startHeadType.toString());
		newElement.setAttribute(LDAttributes.StartHeadSize, Float.toString(startHeadSize));
		newElement.setAttribute(LDAttributes.EndHeadType, endHeadType.toString());
		newElement.setAttribute(LDAttributes.EndHeadSize, Float.toString(endHeadSize));
		newElement.setAttribute(LDAttributes.ArrowColor, convertFromColorToString(arrowColor));
		return newElement;
	}

	public Element getOldElement() {
		return xmlElement;
	}

	protected void replaceToNewElement() {
		if(!isInitializing) {
			StyleWindow.editByTextInput = false;
			StyleSetter.setStyleValue(getOldElement(), getNewElement());
		}
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
		replaceToNewElement();
	}

	public float getArrowWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
		replaceToNewElement();
	}

	public ArrowHeadType getStartHeadType() {
		return startHeadType;
	}

	public void setStartHeadType(ArrowHeadType startHeadType) {
		this.startHeadType = startHeadType;
		replaceToNewElement();
	}

	public float getStartHeadSize() {
		return startHeadSize;
	}

	public void setStartHeadSize(float startHeadSize) {
		this.startHeadSize = startHeadSize;
		replaceToNewElement();
	}

	public ArrowHeadType getEndHeadType() {
		return endHeadType;
	}

	public void setEndHeadType(ArrowHeadType endHeadType) {
		this.endHeadType = endHeadType;
		replaceToNewElement();
	}

	public float getEndHeadSize() {
		return endHeadSize;
	}

	public void setEndHeadSize(float endHeadSize) {
		this.endHeadSize = endHeadSize;
		replaceToNewElement();
	}

	public Color getArrowColor() {
		return arrowColor;
	}

	public void setArrowColor(Color arrowColor) {
		this.arrowColor = arrowColor;
		replaceToNewElement();
	}

	private static String convertFromColorToString(Color color) {
		String result = "";
		if(color != null) result = "#" + Integer.toHexString(color.getRGB() & 0x00ffffff).toUpperCase();
		return result;
	}

	public void UpdateStyleSource(ArrowStyle arrowStyle) {
		if(arrowStyle != null){
			arrowColor = arrowStyle.arrowColor;
			width = arrowStyle.width;
			radius = arrowStyle.radius;
			startHeadSize = arrowStyle.startHeadSize;
			startHeadType = arrowStyle.startHeadType;
			endHeadSize = arrowStyle.endHeadSize;
			endHeadType = arrowStyle.endHeadType;
			xmlElement = arrowStyle.xmlElement;
		}
	}
}
