package textEditor;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import windows.EditorWindow;

import enums.LDXMLTags;

public class TextEditor {
	public static void setArrowValue(Element arrow) {
		String result = "";
		String arrowSource = EditorWindow.sourceTextArea.getText();
		int start = arrowSource.indexOf("<" + LDXMLTags.Arrows + ">");
		int end = arrowSource.indexOf("</" + LDXMLTags.Arrows + ">") + ("</" + LDXMLTags.Arrows + ">").length();

		if(start > 0 && end > 0) {
			arrowSource = arrowSource.substring(start, end);
			Document doc = null;
			Element root = null;
			try {
				SAXBuilder builder = new SAXBuilder();
				doc = builder.build(new ByteArrayInputStream(arrowSource.getBytes("UTF-8")));

				root= doc.getRootElement();
			} catch (Exception e) {

			}

			root.addContent(arrow);

			XMLOutputter outputter = new XMLOutputter();
			outputter.setFormat(Format.getPrettyFormat());
		    try {
		    	StringWriter writer = new StringWriter();
		    	outputter.output(root, writer);
		    	result = writer.toString();
		    }
		    catch (Exception e) {
		    }

		    EditorWindow.sourceTextArea.setText(EditorWindow.sourceTextArea.getText().replace(arrowSource, result));
		}
	}

	public static void replaceElement(Element oldElement, Element newElement) {
		String newString = "", oldString = "";
		XMLOutputter outputter = new XMLOutputter();
		outputter.setFormat(Format.getRawFormat());
		StringWriter writer;

		if(oldElement != null) {
		    try {
		    	writer = new StringWriter();
		    	outputter.output(oldElement, writer);
		    	oldString = writer.toString();
		    }
		    catch (Exception e) {
		    }
		}

		if(newElement != null) {
		    try {
		    	writer = new StringWriter();
		    	outputter.output(newElement, writer);
		    	newString = writer.toString();
		    }
		    catch (Exception e) {
		    }
		}

		 EditorWindow.sourceTextArea.setText(EditorWindow.sourceTextArea.getText().replace(oldString, newString));
	}

	public static void setTextBoxValue(Element textBoxElement) {
		String result = "";
		String textBoxSource = EditorWindow.sourceTextArea.getText();
		int start = textBoxSource.indexOf("<" + LDXMLTags.TextBoxes + ">");
		int end = textBoxSource.indexOf("</" + LDXMLTags.TextBoxes + ">") + ("</" + LDXMLTags.TextBoxes + ">").length();

		if(start > 0 && end > 0) {
			textBoxSource = textBoxSource.substring(start, end);
			Document doc = null;
			Element root = null;
			try {
				SAXBuilder builder = new SAXBuilder();
				doc = builder.build(new ByteArrayInputStream(textBoxSource.getBytes("UTF-8")));

				root= doc.getRootElement();
			} catch (Exception e) {

			}
			root.addContent(textBoxElement);

			XMLOutputter outputter = new XMLOutputter();
			outputter.setFormat(Format.getRawFormat());
		    try {
		    	StringWriter writer = new StringWriter();
		    	outputter.output(root, writer);
		    	result = writer.toString();
		    }
		    catch (Exception e) {
		    }

		    EditorWindow.sourceTextArea.setText( EditorWindow.sourceTextArea.getText().replace(textBoxSource, result));
		}
	}
}
