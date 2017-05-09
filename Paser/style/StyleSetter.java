package style;


import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.List;


import enums.LDAttributes;
import enums.LDXMLTags;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import windows.EditorWindow;

public class StyleSetter {


	static public void removeStyleValue(Element removeElement) {
		String xmlSource = "";

		if(removeElement != null) {

			xmlSource = EditorWindow.getSourceText();
			int start = xmlSource.indexOf("<" + LDXMLTags.Style);
			int end = xmlSource.indexOf("</" + LDXMLTags.Style + ">") + ("</" + LDXMLTags.Style + ">").length();
			if(start > 1 && end > 1) {
				String styleSource = xmlSource.substring(start, end);
				Document doc = null;
				Element root = null;
				try {
					SAXBuilder builder = new SAXBuilder();
					doc = builder.build(new ByteArrayInputStream(styleSource.getBytes("UTF-8")));

					root= doc.getRootElement();
				} catch (Exception e) {

				}

				String targetID = removeElement.getAttributeValue(LDAttributes.ID);

				Element targetElement = null;

				if(removeElement != null) {
					List<Element> nodes = root.getChildren(removeElement.getName());
					for(Element element: nodes) {
						String currentID = element.getAttributeValue(LDAttributes.ID);
						if(targetID == null && currentID == null) {
							targetElement = element;
							break;
						}
						if(targetID != null && currentID != null && targetID.equalsIgnoreCase(currentID)) {
							targetElement = element;
							break;
						}
					}

					if(targetElement != null){
						root.removeContent(targetElement);
					}
				}

				XMLOutputter outputter = new XMLOutputter();
				outputter.setFormat(Format.getPrettyFormat());
			    try {
			    	StringWriter writer = new StringWriter();
			    	outputter.output(root, writer);
			    	styleSource = writer.toString();
			    }
			    catch (Exception e) {
			    }
				xmlSource = xmlSource.substring(0, start) + styleSource + xmlSource.substring(end);

				EditorWindow.setSourceText(xmlSource);
				EditorWindow.setSourceToBodyText(xmlSource);
			}
		}
	}

	static public void deleteID(String id) {
		String xmlSource = "";
		if(id != null) {
			xmlSource = EditorWindow.getSourceText();
			xmlSource = xmlSource.replace(" ID=\"" + id + "\"", "");

			EditorWindow.setSourceText(xmlSource);
			EditorWindow.setSourceToBodyText(xmlSource);
		}
	}

	static public void replaceID(String oldID, String newID) {
		String bodySource = "";
		if(oldID != null && newID != null) {
			bodySource = EditorWindow.getBodyText();

			if(bodySource.contains("ID=\"" + oldID + "\""))
				bodySource = bodySource.replace("ID=\"" + oldID + "\"","ID=\"" + newID + "\"");
			else
				bodySource = bodySource.replace("<" + oldID + ">", "<" + oldID + " ID=\"" + newID + "\"" + ">");

			EditorWindow.setBodyText(bodySource);
		}

	}

	public static void setStyleValue(Element oldElement, Element newElement) {
		String xmlSource = "";

		xmlSource = EditorWindow.getSourceText();
		int start = xmlSource.indexOf("<" + LDXMLTags.Style);
		int end = xmlSource.indexOf("</" + LDXMLTags.Style + ">") + ("</" + LDXMLTags.Style + ">").length();
		if(start > 1 && end > 1) {
			String styleSource = xmlSource.substring(start, end);
			Document doc = null;
			Element root = null;
			try {
				SAXBuilder builder = new SAXBuilder();
				doc = builder.build(new ByteArrayInputStream(styleSource.getBytes("UTF-8")));

				root= doc.getRootElement();
			} catch (Exception e) {

			}

			Element targetElement = null;

			if(oldElement != null) {
				String targetID = oldElement.getAttributeValue(LDAttributes.ID);
				List<Element> nodes = root.getChildren(oldElement.getName());
				for(Element element: nodes) {
					String currentID = element.getAttributeValue(LDAttributes.ID);
					if(targetID == null && currentID == null) {
						targetElement = element;
						break;
					}
					if(targetID != null && currentID != null && targetID.equalsIgnoreCase(currentID)) {
						targetElement = element;
						break;
					}
				}
			}

			int targetIndex = -1;
			if(targetElement != null){
				targetIndex = root.indexOf(targetElement);
				root.removeContent(targetElement);
			}
			if(newElement != null){
				if(targetIndex > 0)
					root.addContent(targetIndex, newElement);
				else
					root.addContent(newElement);
			}

			XMLOutputter outputter = new XMLOutputter();
			outputter.setFormat(Format.getPrettyFormat());
		    try {
		    	StringWriter writer = new StringWriter();
		    	outputter.output(root, writer);
		    	styleSource = writer.toString();
		    }
		    catch (Exception e) {
		    }
			xmlSource = xmlSource.substring(0, start) + styleSource + xmlSource.substring(end);

			EditorWindow.setSourceText(xmlSource);
		}	      
	}  
}
