package stylePaser;

import org.jdom.Element;


import style.ArrowStyle;
import style.BRStyle;
import style.BaseStyle;
import style.MarkStyle;
import style.TextBoxStyle;

public class StyleTagPaser {

	static BaseStylePaser attributePaser;

	static public BaseStyle getBody(Element element) {
		BaseStyle bodyStyle = new BaseStyle();
		attributePaser = new BodyStylePaser(element, bodyStyle);
		attributePaser.parse();
		return bodyStyle;
	}

	public static BaseStyle getSpan(Element element) {
		BaseStyle spanStyle = new BaseStyle();
		attributePaser = new BaseStylePaser(element, spanStyle);
		attributePaser.parse();
		return spanStyle;
	}

	public static MarkStyle getMark(Element element) {
		MarkStyle markStyle = new MarkStyle();
		attributePaser = new MarkStylePaser(element, markStyle);
		attributePaser.parse();
		return markStyle;
	}

	public static BRStyle getBR(Element element) {
		BRStyle brStyle = new BRStyle();
		attributePaser = new BRStylePaser(element, brStyle);
		attributePaser.parse();
		return brStyle;
	}

	public static ArrowStyle getArrow(Element element) {
		ArrowStyle arrowStyle = new ArrowStyle();
		attributePaser = new ArrowStylePaser(element, arrowStyle);
		attributePaser.parse();
		return arrowStyle;
	}

	public static TextBoxStyle getTextBox(Element element) {
		TextBoxStyle textBoxStyle = new TextBoxStyle();
		attributePaser = new TextBoxStylePaser(element, textBoxStyle);
		attributePaser.parse();
		return textBoxStyle;
	}
}
