package style;

import enums.Enums.HorizontalAlign;
import enums.Enums.VerticalAlign;


public class MarkStyle extends BaseStyle {
	public VerticalAlign verticalPosition;
	public HorizontalAlign horizontalPosition;
	public float margin;

	public MarkStyle(){
		super();
		verticalPosition = VerticalAlign.SUB;
		horizontalPosition = HorizontalAlign.CENTER;
	}
}
