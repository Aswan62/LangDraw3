package textEditor;



import itemList.DrawingList;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import mainTextPaser.ArrowDomPaser;
import mainTextPaser.BodyPaser;
import mainTextPaser.TextBoxPaser;

import org.jdesktop.swingx.painter.BusyPainter;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import render.DetectLocation;
import render.DrawWordAndMark;
import render.ImageCanvas;
import render.RenderQuality;
import stylePaser.StyleDomPaser;
import windows.DrawingWindow;
import windows.EditorWindow;
import enums.LDAttributes;
import enums.LDXMLTags;

public class PaserThread extends Thread{
	static String oldText = "";
	static private boolean isSourceChanged = false;
	static public boolean fouceUpdate = false;
	ImageCanvas canvas;
	private static String oldStyleText;
	SAXBuilder builder = new SAXBuilder();
	private static String sourceText;
	private BufferedImage bufferedImage;
	private Rectangle2D.Float transformedBounds;
	private BusyPainter painter;
	private boolean isError = false;

	public void checkTextUpdate() {
		sourceText = EditorWindow.sourceText;
		if(sourceText != null && !sourceText.equals(oldText)) {
			oldText = sourceText;
			if(sourceText.isEmpty()) sourceText = " ";
			isSourceChanged = true;
		} else {
			isSourceChanged = false;
		}
	}

	@Override
	public void run(){
		while(true) {
			if(!fouceUpdate) checkTextUpdate();
			else {
				isSourceChanged = true;
				fouceUpdate = false;
			}

			try{
		        if(isSourceChanged && sourceText != null) {
		        	if(DrawingWindow.busyLabel != null) {
			    		painter = DrawingWindow.busyLabel.getBusyPainter();
			    		painter.setHighlightColor(Color.CYAN);
			    		painter.setBaseColor(Color.blue);
			    		DrawingWindow.busyLabel.setBusyPainter(painter);
			    		DrawingWindow.busyLabel.setDelay(200);
			    		DrawingWindow.busyLabel.setBusy(true);
					}

					isSourceChanged = false;
					isError = false;
					parseText(sourceText);
		        	Rectangle2D.Float bounds = new Rectangle2D.Float();
		        	bounds = DetectLocation.setWordFormatLocation(bounds, DrawingList.Words);
		        	bounds = DetectLocation.setBorderLocation(bounds, DrawingList.Borders);
		        	bounds = DetectLocation.setMarkFormatLocation(bounds, DrawingList.Words, DrawingList.Marks);
		        	bounds = DetectLocation.setArrowLocation(bounds,  DrawingList.Arrows);
		        	bounds = DetectLocation.setTextBoxLocation(bounds, DrawingList.TextBoxes);
		        	transformedBounds = (Rectangle2D.Float) bounds.clone();
		        	transformedBounds.setRect(bounds.x * ImageCanvas.getImageScale().getScaleX(), bounds.y* ImageCanvas.getImageScale().getScaleY(), bounds.width * ImageCanvas.getImageScale().getScaleX(), bounds.height * ImageCanvas.getImageScale().getScaleY());
		        	RenderQuality.setHighQuality();

		        	if(!bounds.isEmpty()) {
			        	bufferedImage = new BufferedImage((int)transformedBounds.getWidth(), (int)transformedBounds.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
			        	Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();
			        	g2.setTransform(ImageCanvas.getImageScale());
			        	g2.setRenderingHints(RenderQuality.getCurrentQuality());
			        	g2 = (Graphics2D) DrawWordAndMark.fillBackground(bounds, g2);
			        	g2 = (Graphics2D) DrawWordAndMark.drawBorder(DrawingList.Borders , g2);
			        	g2 = (Graphics2D) DrawWordAndMark.drawWords(DrawingList.Words, g2);
			        	g2 = (Graphics2D) DrawWordAndMark.drawMarks(DrawingList.Marks, g2);
			        	g2 = (Graphics2D) DrawWordAndMark.drawArrows(DrawingList.Arrows, g2);
			        	g2 = (Graphics2D) DrawWordAndMark.drawTextBoxes(DrawingList.TextBoxes, g2);
			        	ImageCanvas.setSourceImage(bufferedImage, transformedBounds.x, transformedBounds.y);
		        	}
		        } else {
		        	if(DrawingWindow.busyLabel != null && !isError) {
		        		painter = DrawingWindow.busyLabel.getBusyPainter();
			    		painter.setHighlightColor(Color.GRAY);
			    		painter.setBaseColor(Color.LIGHT_GRAY);
			    		DrawingWindow.busyLabel.setBusyPainter(painter);
			    		DrawingWindow.busyLabel.setBusy(false);
		        	}
	        	}
	    	}catch(Exception e){
	    		e.printStackTrace();

	    		isError = true;
	    		if(DrawingWindow.busyLabel != null) {
		    		painter = DrawingWindow.busyLabel.getBusyPainter();
		    		painter.setHighlightColor(Color.pink);
		    		painter.setBaseColor(Color.red);
		    		DrawingWindow.busyLabel.setBusyPainter(painter);
		    		DrawingWindow.busyLabel.setDelay(50);
		    		DrawingWindow.busyLabel.setBusy(true);
				}
	    		bufferedImage = new BufferedImage(10, 10, BufferedImage.TYPE_4BYTE_ABGR);
	    		if(transformedBounds != null) ImageCanvas.setSourceImage(bufferedImage, transformedBounds.x, transformedBounds.y);
	    	}finally {
	    		try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
		}
	}

	private static void setIndexes(String xmlSource) {
		if(xmlSource != null) {
			int index = 0;
			boolean isInTag = false, isInMark = false, isMark = false;
			int j = 0, k = 0;
			index = xmlSource.indexOf("<"+LDXMLTags.Body+">");
			String stack = "";

			if(index>0) {
				for(int i = index; i < xmlSource.length(); i++) {
					stack = stack.concat(String.valueOf(xmlSource.charAt(i)));

					if(stack.endsWith("<")) {
						isInTag = true;
					}
					if(isInTag && stack.endsWith("<" + LDXMLTags.InMark)) {
						isInMark = true;
					}
					if(isInTag && stack.endsWith("<" + LDXMLTags.Mark)) {
						isMark = true;
					}

					if(!stack.endsWith(LDAttributes.Show + "=\"") && stack.endsWith("\"")) {
						isInTag = true;
					}


					if(isInTag || stack.endsWith("\r") || stack.endsWith("\n")) {}
					else if(isMark && !isInTag) {
						int length = DrawingList.Marks.get(k).getTextLength();
						DrawingList.Marks.get(k).setCharIndex(index + stack.length());
						i += length -1;
						k+=1;
						isMark = false;
					} else {
						if(j < DrawingList.Words.size()) {
							int length = DrawingList.Words.get(j).getTextLength();
							DrawingList.Words.get(j).setCharIndex(index + stack.length());
							i += length - 1;
							j+=1;
							isInMark = false;
						}
					}

					if(stack.endsWith(">")) {
						isInTag = false;
					}
					if((isMark || isInMark) && stack.endsWith(LDAttributes.Show + "=\"")) {
						isInTag = false;
					}
				}
			}
		}
	}

	public static void parseText(String source){
		int startIndex, endIndex;
		source = source.replace("\r", "");
		source = source.replace("\n", "");
	    startIndex = source.indexOf("<" + LDXMLTags.Style);
	    endIndex = source.indexOf("</" + LDXMLTags.Style + ">") + ("</" + LDXMLTags.Style + ">").length();

	    if(startIndex != -1 && endIndex != -1)
	    {
	    	String styleText = source.substring(startIndex, endIndex);
	    	if(!styleText.equals(oldStyleText)) {
	    		StyleDomPaser stylePaser = new StyleDomPaser(styleText);
	    		stylePaser.paseStyles();
	    		oldStyleText = styleText;
	    	}
	    }

	    startIndex = source.indexOf("<" + LDXMLTags.Arrows + ">");
    	endIndex = source.indexOf("</" + LDXMLTags.Arrows + ">") + ("</" + LDXMLTags.Arrows + ">").length();
	    if(startIndex != -1 && endIndex != -1)
	    {
	    	String arrowText = source.substring(startIndex, endIndex);
	    	ArrowDomPaser arrowPaser =new ArrowDomPaser(arrowText);
	    	arrowPaser.paseArrows();
	    }

	    startIndex = source.indexOf("<" + LDXMLTags.TextBoxes + ">");
    	endIndex = source.indexOf("</" + LDXMLTags.TextBoxes + ">") + ("</" + LDXMLTags.TextBoxes + ">").length();
	    if(startIndex != -1 && endIndex != -1)
	    {
	    	String textBoxText = source.substring(startIndex, endIndex);
	    	try {
				new TextBoxPaser(textBoxText);
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
	    }

	    startIndex = source.indexOf("<" + LDXMLTags.Body + ">");
    	endIndex = source.indexOf("</" + LDXMLTags.Body + ">") + ("</" + LDXMLTags.Body + ">").length();
	    if(startIndex != -1 && endIndex != -1)
	    {
	    	SAXParserFactory spf = SAXParserFactory.newInstance();
    		SAXParser sp;
			try {
				sp = spf.newSAXParser();

		    	String bodyText = source.substring(startIndex, endIndex);
		    	BodyPaser bodyPaser =new BodyPaser();
				sp.parse(new InputSource(new StringReader(bodyText)),bodyPaser);
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
	    }

	    setIndexes(source);

	}
}
