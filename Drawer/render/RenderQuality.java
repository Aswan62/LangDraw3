package render;

import java.awt.RenderingHints;
import java.util.HashMap;

public class RenderQuality {
	static private HashMap< RenderingHints.Key, Object> Quality = new HashMap< RenderingHints.Key, Object>();

	static public void setHighQuality() {
		Quality.put(RenderingHints.KEY_ALPHA_INTERPOLATION  , RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		Quality.put(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON);
		Quality.put(RenderingHints.KEY_FRACTIONALMETRICS , RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		Quality.put(RenderingHints.KEY_INTERPOLATION  , RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		Quality.put(RenderingHints.KEY_RENDERING , RenderingHints.VALUE_RENDER_QUALITY);
		Quality.put(RenderingHints.KEY_STROKE_CONTROL , RenderingHints.VALUE_STROKE_NORMALIZE);
		Quality.put(RenderingHints.KEY_TEXT_ANTIALIASING , RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	}

	static public void setLowQuality() {
		Quality.remove(RenderingHints.KEY_ALPHA_INTERPOLATION );
		Quality.remove(RenderingHints.KEY_ANTIALIASING);
		Quality.remove(RenderingHints.KEY_FRACTIONALMETRICS);
		Quality.remove(RenderingHints.KEY_INTERPOLATION);
		Quality.remove(RenderingHints.KEY_RENDERING);
		Quality.remove(RenderingHints.KEY_STROKE_CONTROL);
		Quality.remove(RenderingHints.KEY_TEXT_ANTIALIASING);
	}

	static public HashMap<RenderingHints.Key, Object> getCurrentQuality() {
		return Quality;
	}
}
