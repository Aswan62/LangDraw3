package mainTextPaser;

import itemList.DrawingList;
import itemList.StyleList;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import shapes.ArrowShape;
import shapes.HeadShape;
import style.ArrowStyle;
import stylePaser.AttributeToObjectConverter;

import enums.LDAttributes;
import enums.LDXMLTags;
import enums.Enums.Direction;
import format.ArrowFormat;

public class ArrowDomPaser {
	Element root;
	public ArrowDomPaser(String xmlSource) {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(new ByteArrayInputStream(xmlSource.getBytes("UTF-8")));
			root= doc.getRootElement();
		} catch (Exception ex) {
			System.out.println("error");
		}

    	DrawingList.Arrows = new ArrayList<ArrowFormat>();
	}

	public void paseArrows() {
		List<Element> arrowList = root.getChildren(LDXMLTags.Arrow);
		if(arrowList != null) {
			for(Element element :arrowList) {
				ArrowFormat af = getArrow(element);
				DrawingList.Arrows.add(af);
			}
		}
	}

	private static ArrowFormat getArrow(Element element) {

		float depth = 0;
		ArrowStyle style = StyleList.ArrowStyleList.get(element.getAttributeValue(LDAttributes.ID));
		Point2D.Float start = AttributeToObjectConverter.getPoint2D(element, LDAttributes.StartPoint);
		Point2D.Float end = AttributeToObjectConverter.getPoint2D(element, LDAttributes.EndPoint);
		try {
			depth = element.getAttribute(LDAttributes.Depth).getFloatValue();
		} catch(Exception e) {
			depth = 0;
		}

		Direction arrowDirection = Direction.Horizontal;
		String direction = element.getAttributeValue(LDAttributes.DepthDirection);
		if(Direction.Vertical.toString().equalsIgnoreCase(direction)) {
			arrowDirection = Direction.Vertical;
		}

		ArrowShape arrowShape = new ArrowShape(start, end, style.radius, depth, arrowDirection);
		HeadShape startHead = new HeadShape(arrowShape.getStartPoint(), arrowShape.getStartMiddle(), arrowShape.getStartControl(), arrowShape.getStartBottom(), style.width, style.startHeadType, style.startHeadSize, new AffineTransform());
		HeadShape endHead = new HeadShape(arrowShape.getEndPoint(), arrowShape.getEndMiddle(), arrowShape.getEndControl(), arrowShape.getEndBottom(), style.width, style.endHeadType, style.endHeadSize, new AffineTransform());

		ArrowFormat arrowFormat = new ArrowFormat(arrowShape, startHead, endHead, style, element);

		return arrowFormat;
	}
}
