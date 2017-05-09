package stylePaser;

import org.jdom.Element;

import style.TextBoxStyle;

public class TextBoxStylePaser extends BaseStylePaser {

	private TextBoxStyle textBoxStyle;

	public TextBoxStylePaser(Element element, TextBoxStyle textBoxStyle) {
		super(element, textBoxStyle);
		this.textBoxStyle = textBoxStyle;
	}

	public void parse() {
		super.parse();
		baseStyle.overrideFontFamily = true;
		baseStyle.overrideFontFamily2Byte = true;
		baseStyle.overrideFontColor = true;
		baseStyle.overrideFontSize = true;
		baseStyle.overrideFontStyle = true;

		setPadding();
	}

	protected void setPadding() {
		textBoxStyle.padding = AttributeToObjectConverter.getPadding(element);
	}

}
