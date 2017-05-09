package stylePaser;

import org.jdom.Element;

import enums.Enums.HorizontalAlign;
import enums.Enums.VerticalAlign;
import enums.LDAttributes;

import style.MarkStyle;

final public class MarkStylePaser extends BodyStylePaser {

	private MarkStyle markStyle;

	public MarkStylePaser(Element element, MarkStyle markStyle) {
		super(element, markStyle);
		this.markStyle = markStyle;
	}

	@Override
	public void parse() {
		super.parse();
		setMargin();
		setVertical();
		setHorizontal();
	}

	protected void setMargin() {
		try {
			markStyle.margin = element.getAttribute(LDAttributes.MarkMargin).getFloatValue();
		} catch (Exception e) {
			markStyle.margin = Float.NaN;
		}
	}

	protected void setVertical() {
		String value = element.getAttributeValue(LDAttributes.VerticalAlign);
		if(value != null) {
			if(value.equalsIgnoreCase(VerticalAlign.SUPER.toString())) {
				markStyle.verticalPosition = VerticalAlign.SUPER;
			}
			else if(value.equalsIgnoreCase(VerticalAlign.SUB.toString())) {
				markStyle.verticalPosition = VerticalAlign.SUB;
			} else {

			}
		}
	}

	protected void setHorizontal() {
		String value = element.getAttributeValue(LDAttributes.HorizontalAlign);
		if(value != null) {
			if(value.equalsIgnoreCase(HorizontalAlign.LEFT.toString())) {
				markStyle.horizontalPosition = HorizontalAlign.LEFT;
			}
			else if(value.equalsIgnoreCase(HorizontalAlign.RIGHT.toString())) {
				markStyle.horizontalPosition = HorizontalAlign.RIGHT;
			}
			else if(value.equalsIgnoreCase(HorizontalAlign.CENTER.toString())) {
				markStyle.horizontalPosition = HorizontalAlign.CENTER;
			} else {

			}
		}
	}

}
