package mainTextPaser;

import java.util.*;

import org.jdom.Element;
import org.xml.sax.*;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.DefaultHandler;

import style.BaseStyle;
import style.MarkStyle;
import itemList.StyleList;
import itemList.DrawingList;

import enums.LDAttributes;
import enums.LDXMLTags;
import format.BRFormat;
import format.BorderSetting;
import format.MarkFormat;
import format.WordFormat;

public class BodyPaser extends DefaultHandler{

	private Stack<BaseStyle> parentStyleStack;
	private BaseStyle currentStyle;
	Stack<BaseStyle> styleStack = new Stack<BaseStyle>();
	Stack<MarkFormat> markStack = new Stack<MarkFormat>();
	Stack<BorderSetting> borderStack = new Stack<BorderSetting>();
	StringBuffer leaf = null;
	int currentLine = 0;
	private int wordsCount;
	private WordFormat wordFormat;

    public void startDocument() {
		DrawingList.Words = new ArrayList<WordFormat>();
    	DrawingList.Marks = new ArrayList<MarkFormat>();
    	DrawingList.LineWidths = new ArrayList<BRFormat>();
    	DrawingList.Borders = new ArrayList<BorderSetting>();

    	currentStyle = StyleList.BodyStyle;
    	styleStack = new Stack<BaseStyle>();
    	parentStyleStack = new Stack<BaseStyle>();
    	markStack = new Stack<MarkFormat>();
    	styleStack.add(currentStyle);
    	borderStack.add(currentStyle.borderSetting);

    	leaf = new StringBuffer();
    	currentLine = 0;
     	wordsCount = 0;
    }


    public void startElement(String namespaceURI,String localName,String qName,Attributes attrs)
    {
		if(!leaf.toString().isEmpty()) {
			BaseStyle style = styleStack.peek();
			for(int i = 0; i < leaf.length(); i++) {
    			wordFormat = new WordFormat(leaf.charAt(i), currentLine, style);
    			DrawingList.Words.add(wordFormat);
    			for(BorderSetting border :borderStack) {
    				border.setQualifiedTargets(wordFormat);
        		}
    			wordsCount += 1;
			}
		}
		leaf = new StringBuffer();

		AttributesImpl attributes = new AttributesImpl(attrs);
		Element element = new Element(qName);
		for(int i = 0; i < attributes.getLength(); i++) {
			element.setAttribute(attributes.getQName(i), attributes.getValue(i));
		}

		if(qName == LDXMLTags.Span){
			parentStyleStack.push(currentStyle);
			BaseStyle newStyle = StyleList.SpanStyleList.get(attrs.getValue(LDAttributes.ID));
			currentStyle = getCurrentStyle(currentStyle, newStyle);
			styleStack.push(currentStyle);

			BorderSetting border = currentStyle.borderSetting.clone();
			if(wordFormat != null) wordFormat.addLeftBorderWidth(border.getLineWidth());
    		for(BorderSetting otherBorder :borderStack) {
    			otherBorder.addInnerBorder(border);
    		}
			borderStack.push(border);
		}
		else if(qName == LDXMLTags.InMark){
			BaseStyle style = StyleList.InMarkStyleList.get(element.getAttributeValue(LDAttributes.ID));
			style = getCurrentStyle(currentStyle, style);
			String text = "";
			if(element.getAttributeValue(LDAttributes.Show) != null)
				text = element.getAttributeValue(LDAttributes.Show);
			DrawingList.Words.add(new WordFormat(text, currentLine, style));
			wordsCount += 1;
		}
		else if(qName == LDXMLTags.Mark){
			MarkStyle style = StyleList.MarkStyleList.get(element.getAttributeValue(LDAttributes.ID));
			String text = "";
			if(element.getAttributeValue(LDAttributes.Show) != null)
				text = element.getAttributeValue(LDAttributes.Show);
			markStack.push(new MarkFormat(text, style, wordsCount - 1));
		}
		else if(qName == LDXMLTags.BR){
			DrawingList.LineWidths.add(new BRFormat(element));
			currentLine += 1;

			for(int i = 0; i < borderStack.size(); i++) {
				BorderSetting border = borderStack.get(i).clone();
				border.setRightBorderOpened();
	    		if(border != null) {
	    			DrawingList.Borders.add(border);
	    		}
	    		border = borderStack.get(i).clone();
				border.setLeftBorderOpened();
				borderStack.set(i, border);
			}
		}
    }

    public void endElement(String namespaceURI,String localName,String qName)
    {
    	if(!leaf.toString().isEmpty()){
    		BaseStyle style = styleStack.peek();
    		for(int i = 0; i < leaf.length(); i++) {
    			wordFormat = new WordFormat(leaf.charAt(i), currentLine, style);
    			DrawingList.Words.add(wordFormat);
    			for(BorderSetting border :borderStack) {
    				border.setQualifiedTargets(wordFormat);
        		}
    			wordsCount += 1;
    		}
    	}
		leaf = new StringBuffer();

    	if(qName == LDXMLTags.Span){
    		styleStack.pop();
    		currentStyle = parentStyleStack.pop();
    		BorderSetting border = borderStack.pop();
			wordFormat.addLeftBorderWidth(border.getLineWidth());
			DrawingList.Borders.add(border);
    	}
		else if(qName == LDXMLTags.Mark){
			MarkFormat mf = markStack.pop();

			mf.setEnd(wordsCount);
			DrawingList.Marks.add(mf);
		}
    }

    public void endDocument()
    {
    }

    public void characters(char[] ch,int start,int length)
    {
    	if(leaf == null) {
    		leaf = new StringBuffer();
    	}
    	leaf.append(ch, start, length);
    }

	public static BaseStyle getCurrentStyle(BaseStyle parent, BaseStyle child) {
		BaseStyle currentStyle = parent.clone();

		if(child.overrideFontFamily) currentStyle.fontFamily = child.fontFamily;
		if(child.overrideFontFamily2Byte) currentStyle.fontFamily2Byte = child.fontFamily2Byte;
		if(child.overrideFontSize) currentStyle.fontSize = child.fontSize;
		if(child.overrideFontColor) currentStyle.fontColor = child.fontColor;
		if(child.overrideFontStyle) currentStyle.fontStyle = child.fontStyle;
		if(child.useVerticalAlign) currentStyle.spanVerticalAlign = child.spanVerticalAlign;

		if(child.borderSetting != null)
			currentStyle.borderSetting = child.borderSetting.clone();
		currentStyle.drawDecoration = child.drawDecoration;
		currentStyle.decoration = child.decoration;
		currentStyle.decorationColor = child.decorationColor;

		return currentStyle;
	}
}
