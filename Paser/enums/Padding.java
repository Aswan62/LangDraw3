package enums;

public class Padding {
	public float left, top, right, bottom;

	public float getHorizontal() {
		return left + right;
	}

	public float getVartical() {
		return top + bottom;
	}

	public boolean isAllValueSame() {
		if(left == top && right == bottom && left == right)
			return true;
		else
			return false;
	}

	@Override
	public String toString() {
		if(left == top && right == bottom && left == right) {
			return Float.toString(left);
		}
		else if(left == right && bottom == top) {
			return  Float.toString(top).concat(",").concat(Float.toString(left));
		}
		else if(left == right) {
			return  Float.toString(top).concat(",").concat(Float.toString(left)).concat(",").concat(Float.toString(bottom));
		}
		else {
			return  Float.toString(top).concat(",").concat(Float.toString(right)).concat(",").concat(Float.toString(bottom)).concat(",").concat(Float.toString(left));
		}
	}
}
