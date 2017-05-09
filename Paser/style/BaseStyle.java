package style;

import java.awt.Color;

import org.jdom.Element;

import enums.Enums.SpanVerticalAlign;
import enums.Enums.TextDecoration;
import format.BorderSetting;

public class BaseStyle implements Cloneable {
	public String id;
	public String fontFamily;
	public String fontFamily2Byte;
	public float fontSize;
	public int fontStyle;
	public Color fontColor;
	public TextDecoration decoration;
	public Color decorationColor;
	public BorderSetting borderSetting;
	public SpanVerticalAlign spanVerticalAlign;
	public Element xmlElement;
	public boolean overrideFontFamily, overrideFontFamily2Byte,
			overrideFontSize, overrideFontColor, overrideFontStyle,
			drawDecoration, useVerticalAlign;

	public BaseStyle() {
		this.id = null;
		this.overrideFontFamily = false;
		this.overrideFontFamily2Byte = false;
		this.overrideFontSize = false;
		this.overrideFontColor = false;
		this.drawDecoration = false;
		this.useVerticalAlign = false;
		this.borderSetting = new BorderSetting();
	}

	@Override
	public BaseStyle clone() {
		try {
			return (BaseStyle) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.toString());
		}
	}

}