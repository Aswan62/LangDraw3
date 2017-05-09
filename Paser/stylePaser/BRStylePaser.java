package stylePaser;

import org.jdom.Element;

import enums.LDAttributes;

import style.BRStyle;

final public class BRStylePaser extends BaseStylePaser {

	private BRStyle brStyle;

	public BRStylePaser(Element element, BRStyle brStyle) {
		super(element, null);
		this.brStyle = brStyle;
		this.element = element;
		this.brStyle.xmlElement = element;
	}

	public void parse() {
		setID();
		setLineHeight();
	}

	protected void setID() {
		brStyle.id = element.getAttributeValue(LDAttributes.ID);
	}

	protected void setLineHeight() {
		try {
			brStyle.lineHeight = element.getAttribute(LDAttributes.LineHeight).getFloatValue();
		} catch (Exception e) {
			brStyle.lineHeight = Float.NaN;
		}
	}
}
