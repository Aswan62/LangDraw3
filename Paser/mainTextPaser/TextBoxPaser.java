package mainTextPaser;

import itemList.DrawingList;
import itemList.StyleList;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.DefaultHandler;

import style.BaseStyle;
import style.TextBoxStyle;
import stylePaser.AttributeToObjectConverter;
import enums.LDAttributes;
import enums.LDXMLTags;
import format.BRFormat;
import format.BorderSetting;
import format.MarkFormat;
import format.TextBoxFormat;
import format.WordFormat;


public class TextBoxPaser {
	public TextBoxPaser(String textBoxSource) throws Exception {
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(new ByteArrayInputStream(textBoxSource.getBytes("UTF-8")));
		Element sourceElement = doc.getRootElement();
		List<Element> children = sourceElement.getChildren(LDXMLTags.TextBox);
		DrawingList.TextBoxes = new ArrayList<TextBoxFormat>();
		for(Element element: children) {
  			TextBoxFormat textBoxFormat = new TextBoxFormat();
  			textBoxFormat.xmlElement = element;
  			SAXParserFactory spf = SAXParserFactory.newInstance();
  			SAXParser sp = spf.newSAXParser();

			XMLOutputter xmlOutputter = new XMLOutputter(Format.getRawFormat());
			StringWriter writer = new StringWriter();
			xmlOutputter.output(element, writer);
  			TextBoxSaxPaser boxPaser =new TextBoxSaxPaser(textBoxFormat);
  			sp.parse(new InputSource(new StringReader(writer.toString())),boxPaser);
  			DrawingList.TextBoxes.add(textBoxFormat);
		}
	}

	public TextBoxPaser(String textBoxSource ,TextBoxFormat textBoxFormat) throws Exception {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();

		TextBoxSaxPaser boxPaser =new TextBoxSaxPaser(textBoxFormat);
		sp.parse(new InputSource(new StringReader(textBoxSource)),boxPaser);

		if(Float.isNaN(textBoxFormat.getWidth())) {
			textBoxFormat.setPreferredWidth();
		}
	}
}

class TextBoxSaxPaser extends DefaultHandler{
	private Stack<BaseStyle> parentStyleStack;
	private BaseStyle currentStyle;
	private TextBoxFormat textBoxFormat;
	Stack<BaseStyle> styleStack = new Stack<BaseStyle>();
	Stack<MarkFormat> markStack = new Stack<MarkFormat>();
	StringBuffer leaf = null;
	int currentLine = 0;
	private TextBoxStyle textBoxStyle;


	public TextBoxSaxPaser(TextBoxFormat textBoxFormat) {
		this.textBoxFormat = textBoxFormat;
	}

    /* XML文書の開始時の処理*/
    public void startDocument() {
    	currentStyle = textBoxStyle;
		styleStack.add(currentStyle);
    	textBoxFormat.sourceTexts = new ArrayList<WordFormat>();
    	textBoxFormat.wordBorders = new ArrayList<BorderSetting>();
    	styleStack = new Stack<BaseStyle>();
    	parentStyleStack = new Stack<BaseStyle>();
    	markStack = new Stack<MarkFormat>();

    	leaf = new StringBuffer();
    	currentLine = 0;
    }

    /* 要素の開始時の処理*/
    public void startElement(String namespaceURI,String localName,String qName,Attributes attrs)
    {
		if(!leaf.toString().isEmpty()) {
			BaseStyle style = styleStack.peek();
			for(int i = 0; i < leaf.length(); i++) {
    			WordFormat wordFormat = new WordFormat(leaf.charAt(i), currentLine, style);
    			textBoxFormat.sourceTexts.add(wordFormat);
			}
		}
		leaf = new StringBuffer();

		AttributesImpl attributes = new AttributesImpl(attrs);
		Element element = new Element(qName);
		for(int i = 0; i < attributes.getLength(); i++) {
			element.setAttribute(attributes.getQName(i), attributes.getValue(i));
		}

		if(qName == LDXMLTags.TextBox) {
			textBoxStyle = StyleList.TextBoxStyleList.get(element.getAttributeValue(LDAttributes.ID));
			textBoxFormat.textBoxBorder = textBoxStyle.borderSetting.clone();
			textBoxFormat.padding = textBoxStyle.padding;
			textBoxFormat.setBoxLocation(AttributeToObjectConverter.getPoint2D(element, LDAttributes.Position));
			textBoxFormat.setWidth(AttributeToObjectConverter.getFloat(element, LDAttributes.Width));
			currentStyle = textBoxStyle;
			styleStack.add(currentStyle);
		}
		else if(qName == LDXMLTags.Span){
			parentStyleStack.push(currentStyle);
			BaseStyle newStyle = StyleList.SpanStyleList.get(attrs.getValue(LDAttributes.ID));
			currentStyle = BodyPaser.getCurrentStyle(currentStyle, newStyle);
			styleStack.push(currentStyle);
		}
		else if(qName == LDXMLTags.BR){
			DrawingList.LineWidths.add(new BRFormat(element));
			currentLine += 1;
		}
    }

    /*要素の終了時の処理*/
    public void endElement(String namespaceURI, String localName,String qName)
    {
    	if(!leaf.toString().isEmpty()) {
    		BaseStyle style = styleStack.peek();
    		for(int i = 0; i < leaf.length(); i++) {
    			WordFormat wordFormat = new WordFormat(leaf.charAt(i), currentLine, style);
				textBoxFormat.sourceTexts.add(wordFormat);
    		}
    	}
		leaf = new StringBuffer();

    	if(qName == LDXMLTags.Span){
    		if(currentStyle.borderSetting != null) {
    			textBoxFormat.wordBorders.add(currentStyle.borderSetting);
    		}
    		styleStack.pop();
    		currentStyle = parentStyleStack.pop();
    	}
    }

    /* XML文書の終了時の処理*/
    public void endDocument()
    {
    }

    /* 文字列の処理*/
    public void characters(char[] ch,int start,int length)
    {
    	if(leaf == null) {
    		leaf = new StringBuffer();
    	}
    	leaf.append(ch, start, length);
    }
}
