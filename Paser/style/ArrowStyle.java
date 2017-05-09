package style;

import java.awt.Color;

import org.jdom.Element;

import enums.Enums.ArrowHeadType;

public class ArrowStyle {
	public String id;
	public float radius, width, startHeadSize, endHeadSize;
	public ArrowHeadType startHeadType, endHeadType;
	public Color arrowColor;
	public Element xmlElement;

	public ArrowStyle() {
		this.radius = 20;
		this.width = 1F;
		this.startHeadSize = 1F;
		this.endHeadSize = 1F;
		this.startHeadType = ArrowHeadType.NONE;
		this.endHeadType = ArrowHeadType.OPEN;
		this.arrowColor = Color.BLACK;
	}

	public ArrowStyle(float radius, float width, float startHeadSize, float endHeadSize, ArrowHeadType startHeadType, ArrowHeadType endHeadType, Color arrowColor) {
		this.radius = radius;
		this.width = width;
		this.startHeadSize = startHeadSize;
		this.endHeadSize = endHeadSize;
		this.startHeadType = startHeadType;
		this.endHeadType = endHeadType;
		this.arrowColor = arrowColor;
	}
}
