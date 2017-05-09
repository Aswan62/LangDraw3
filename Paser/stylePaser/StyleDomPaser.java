package stylePaser;

import itemList.StyleList;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;


import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import selectStyles.StyleSelectorComponent;
import style.ArrowStyle;
import style.BRStyle;
import style.BaseStyle;
import style.MarkStyle;
import style.TextBoxStyle;
import windows.DrawingWindow;
import windows.EditorWindow;
import windows.StyleWindow;

import enums.LDXMLTags;

public class StyleDomPaser {

	Element root;

	public StyleDomPaser(String xmlSource) {

		try {
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(new ByteArrayInputStream(xmlSource.getBytes("UTF-8")));
			root = doc.getRootElement();

			StyleList.SpanStyleList = new HashMap<String, BaseStyle>();
	    	StyleList.MarkStyleList = new HashMap<String, MarkStyle>();
	    	StyleList.InMarkStyleList = new HashMap<String, BaseStyle>();
	    	StyleList.BRStyleList = new HashMap<String, BRStyle>();
	    	StyleList.ArrowStyleList = new HashMap<String, ArrowStyle>();
	    	StyleList.TextBoxStyleList = new HashMap<String, TextBoxStyle>();

		} catch (Exception ex) {

		}
	}

	public void paseStyles() {
		Element bodyElement = root.getChild(LDXMLTags.Body);
		if(bodyElement != null) StyleList.BodyStyle = StyleTagPaser.getBody(bodyElement);

		List<Element> spanList = root.getChildren(LDXMLTags.Span);
		if(spanList != null) {
			for(Element element :spanList) {
				BaseStyle ss = StyleTagPaser.getSpan(element);
				StyleList.SpanStyleList.put(ss.id, ss);
			}
		}

		List<Element> inMarkList = root.getChildren(LDXMLTags.InMark);
		if(inMarkList != null) {
			for(Element element :inMarkList) {
				BaseStyle ss = StyleTagPaser.getSpan(element);
				StyleList.InMarkStyleList.put(ss.id, ss);
			}
		}

		List<Element> markList = root.getChildren(LDXMLTags.Mark);
		if(markList != null) {
			for(Element element :markList) {
				MarkStyle ms = StyleTagPaser.getMark(element);
				StyleList.MarkStyleList.put(ms.id, ms);
			}
		}

		List<Element> brList = root.getChildren(LDXMLTags.BR);
		if(brList != null) {
			for(Element element :brList) {
				BRStyle ss = StyleTagPaser.getBR(element);
				StyleList.BRStyleList.put(ss.id, ss);
			}
		}

		List<Element> arrowList = root.getChildren(LDXMLTags.Arrow);
		if(arrowList != null) {
			for(Element element :arrowList) {
				ArrowStyle as = StyleTagPaser.getArrow(element);
				StyleList.ArrowStyleList.put(as.id, as);
			}
		}

		List<Element> textBoxList = root.getChildren(LDXMLTags.TextBox);
		if(textBoxList != null) {
			for(Element element :textBoxList) {
				TextBoxStyle tbs = StyleTagPaser.getTextBox(element);
				StyleList.TextBoxStyleList.put(tbs.id, tbs);
			}
		}

		if(StyleWindow.editByTextInput) {
			EditorWindow.setStyles();
			DrawingWindow.setStyles();
			StyleSelectorComponent.setStyles();
		}
	}
}
