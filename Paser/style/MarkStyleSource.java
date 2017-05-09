package style;


import org.jdom.Element;

import enums.Enums.HorizontalAlign;
import enums.Enums.VerticalAlign;
import enums.LDAttributes;
import enums.LDXMLTags;



public class MarkStyleSource extends BaseStyleSource {

	private VerticalAlign verticalPosition;
	private HorizontalAlign horizontalPosition;
	private float margin;

	public MarkStyleSource() {
		super(LDXMLTags.Mark);
		verticalPosition = VerticalAlign.SUB;
		horizontalPosition = HorizontalAlign.CENTER;
	}
	public VerticalAlign getVerticalPosition() {
		return verticalPosition;
	}
	public void setVerticalPosition(VerticalAlign verticalPosition) {
		this.verticalPosition = verticalPosition;
		replaceToNewElement();
	}
	public HorizontalAlign getHorizontalPosition() {
		return horizontalPosition;
	}
	public void setHorizontalPosition(HorizontalAlign horizontalPosition) {
		this.horizontalPosition = horizontalPosition;
		replaceToNewElement();
	}
	public float getMargin() {
		return margin;
	}
	public void setMargin(float margin) {
		this.margin = margin;
		replaceToNewElement();
	}

	public void UpdateBaseStyleSource(MarkStyle markStyle) {
		super.UpdateBaseStyleSource(markStyle);
		if(markStyle != null){
			horizontalPosition = markStyle.horizontalPosition;
			verticalPosition = markStyle.verticalPosition;
			margin = markStyle.margin;
		}
	}

	@Override
	public Element getNewElement() {
		Element element = super.getNewElement();
		element.setAttribute(LDAttributes.HorizontalAlign, horizontalPosition.toString());
		element.setAttribute(LDAttributes.VerticalAlign, verticalPosition.toString());
		element.setAttribute(LDAttributes.MarkMargin, Float.toString(margin));

		return element;
	}
}
