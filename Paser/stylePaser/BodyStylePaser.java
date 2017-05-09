package stylePaser;


import org.jdom.Element;
import style.BaseStyle;



public class BodyStylePaser extends BaseStylePaser {


	public BodyStylePaser(Element element, BaseStyle bodyStyle) {
		super(element, bodyStyle);
	}

	public void parse() {
		super.parse();

		baseStyle.overrideFontFamily = true;
		baseStyle.overrideFontFamily2Byte = true;
		baseStyle.overrideFontColor = true;
		baseStyle.overrideFontSize = true;
		baseStyle.overrideFontStyle = true;
		baseStyle.useVerticalAlign = false;
	}
}
