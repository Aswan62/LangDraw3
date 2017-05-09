package format;

import org.jdom.Element;

import enums.LDAttributes;
import style.BRStyle;
import itemList.StyleList;

public class BRFormat extends BaseFormat {
	private float lineHeight;

	public BRFormat(Element element) {
		BRStyle brStyle = StyleList.BRStyleList.get(element.getAttributeValue(LDAttributes.ID));
		if(brStyle != null) {
			this.lineHeight = brStyle.lineHeight;
		}
		else {
			this.lineHeight = Float.NaN;
		}
	}

	public float getLineHight() {
		return lineHeight;
	}
}
