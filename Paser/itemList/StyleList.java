package itemList;

import java.util.HashMap;
import java.util.Map;

import style.ArrowStyle;
import style.BRStyle;
import style.BaseStyle;
import style.MarkStyle;
import style.TextBoxStyle;

public class StyleList {
	static public BaseStyle BodyStyle;
	static public Map<String, BaseStyle> SpanStyleList = new HashMap<String, BaseStyle>();
	static public Map<String, MarkStyle> MarkStyleList = new HashMap<String, MarkStyle>();
	static public Map<String, BaseStyle> InMarkStyleList = new HashMap<String, BaseStyle>();
	static public Map<String, BRStyle> BRStyleList = new HashMap<String, BRStyle>();
	static public Map<String, ArrowStyle> ArrowStyleList = new HashMap<String, ArrowStyle>();
	static public Map<String, TextBoxStyle> TextBoxStyleList = new HashMap<String, TextBoxStyle>();
}
