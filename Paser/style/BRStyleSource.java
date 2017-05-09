package style;


import org.jdom.Element;

import windows.StyleWindow;

import enums.LDAttributes;
import enums.LDXMLTags;

public class BRStyleSource extends BRStyle{
	public boolean isInitializing;
	private String id;
	private float lineHeight;
	public BRStyleSource() {
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public float getLineHeight() {
		return lineHeight;
	}
	public void setLineHeight(float lineHeight) {
		this.lineHeight = lineHeight;
		replaceToNewElement();
	}

	public Element getNewElement() {
		Element newElement = null;
		if(xmlElement != null) newElement = (Element) xmlElement.clone();
		else newElement = new Element(LDXMLTags.BR);

		if(id != null) newElement.setAttribute(LDAttributes.ID, id);

		newElement.setAttribute(LDAttributes.LineHeight, Float.toString(lineHeight));
		return newElement;
	}

	public Element getOldElement() {
		return xmlElement;
	}

	protected void replaceToNewElement() {
		if(!isInitializing) {
			StyleWindow.editByTextInput = false;
			StyleSetter.setStyleValue(getOldElement(),getNewElement());
		}
	}

	public void UpdateBaseStyleSource(BRStyle brStyle) {
		if(brStyle != null) {
			this.isInitializing = false;
			this.id = brStyle.id;
			this.lineHeight = brStyle.lineHeight;
			xmlElement = brStyle.xmlElement;
		}
	}

}
