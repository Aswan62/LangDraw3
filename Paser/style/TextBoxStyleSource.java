package style;

import org.jdom.Element;

import enums.LDAttributes;
import enums.LDXMLTags;
import enums.Padding;

public class TextBoxStyleSource  extends BaseStyleSource {

	private Padding padding;

	public TextBoxStyleSource() {
		super(LDXMLTags.TextBox);
	}

	public Padding getPadding() {
		return padding;
	}

	public void setPadding(Padding padding) {
		this.padding = padding;
		replaceToNewElement();
	}

	public void setPaddingTop(float top) {
		this.padding.top = top;
		replaceToNewElement();
	}

	public void setPaddingRight(float right) {
		this.padding.right = right;
		replaceToNewElement();
	}

	public void setPaddingBottom(float bottom) {
		this.padding.bottom = bottom;
		replaceToNewElement();
	}

	public void setPaddingLeft(float left) {
		this.padding.left = left;
		replaceToNewElement();
	}

	public void UpdateBaseStyleSource(TextBoxStyle textboxStyle) {
		super.UpdateBaseStyleSource(textboxStyle);
		if(textboxStyle != null){
			this.padding = textboxStyle.padding;

			if(textboxStyle.borderSetting != null) {
				this.borderSetting = textboxStyle.borderSetting;
			}
		}
	}

	@Override
	public Element getNewElement() {
		Element element = super.getNewElement();
		element.setAttribute(LDAttributes.Padding, padding.toString());

		return element;
	}
}
