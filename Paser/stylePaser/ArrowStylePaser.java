package stylePaser;

import org.jdom.Element;

import enums.LDAttributes;
import enums.Enums.ArrowHeadType;

import style.ArrowStyle;

public class ArrowStylePaser extends BaseStylePaser {

	private ArrowStyle arrowStyle;

	public ArrowStylePaser(Element element, ArrowStyle arrowStyle) {
		super(element, null);
		this.arrowStyle = arrowStyle;
		this.arrowStyle.xmlElement = element;
	}

	public void parse() {
		setID();
		setRadius();
		setColor();
		setWidth();
		setArrowSize();
		setArrowShape();
	}

	protected void setID() {
		arrowStyle.id = element.getAttributeValue(LDAttributes.ID);
	}

	protected void setRadius() {
		try {
			arrowStyle.radius = element.getAttribute(LDAttributes.Radius).getFloatValue();
		} catch (Exception e) {
			arrowStyle.radius = 0;
		}
	}

	protected void setColor() {
		arrowStyle.arrowColor = AttributeToObjectConverter.getColor(element, LDAttributes.ArrowColor, null);
	}

	protected void setWidth() {
		try {
			arrowStyle.width = element.getAttribute(LDAttributes.ArrowWidth).getFloatValue();
		} catch (Exception e) {
			arrowStyle.width = 1;
		}
	}

	protected void setArrowSize() {
		try {
			arrowStyle.startHeadSize = element.getAttribute(LDAttributes.StartHeadSize).getFloatValue();
		} catch (Exception e) {
			arrowStyle.startHeadSize = 1;
		}

		try {
			arrowStyle.endHeadSize = element.getAttribute(LDAttributes.EndHeadSize).getFloatValue();
		} catch (Exception e) {
			arrowStyle.endHeadSize = 1;
		}
	}

	protected void setArrowShape() {
		String start = element.getAttributeValue(LDAttributes.StartHeadType);
		if(ArrowHeadType.NONE.toString().equalsIgnoreCase(start)) {
			arrowStyle.startHeadType = ArrowHeadType.NONE;
		}
		else if(ArrowHeadType.OPEN.toString().equalsIgnoreCase(start)) {
			arrowStyle.startHeadType = ArrowHeadType.OPEN;
		}
		else if(ArrowHeadType.FILL.toString().equalsIgnoreCase(start)) {
			arrowStyle.startHeadType = ArrowHeadType.FILL;
		}
		else  arrowStyle.startHeadType = ArrowHeadType.NONE;

		String end = element.getAttributeValue(LDAttributes.EndHeadType);
		if(ArrowHeadType.NONE.toString().equalsIgnoreCase(end)) {
			arrowStyle.endHeadType = ArrowHeadType.NONE;
		}
		else if(ArrowHeadType.OPEN.toString().equalsIgnoreCase(end)) {
			arrowStyle.endHeadType = ArrowHeadType.OPEN;
		}
		else if(ArrowHeadType.FILL.toString().equalsIgnoreCase(end)) {
			arrowStyle.endHeadType = ArrowHeadType.FILL;
		}
		else arrowStyle.endHeadType = ArrowHeadType.NONE;

	}

}
