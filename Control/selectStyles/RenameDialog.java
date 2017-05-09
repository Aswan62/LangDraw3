package selectStyles;

import javax.swing.JComponent;

import org.jdom.Element;

import style.StyleSetter;
import enums.LDAttributes;


public class RenameDialog extends IDNameDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8356401822746732322L;
	private Element oldElement, newElement;
	private String oldName,newName;

	public RenameDialog(JComponent parent, String oldName, String newName, Element target) {
		super("Do you want to replace an ID element included in the body?");
		oldElement = target;
		this.oldName = oldName;
		this.newName = newName;

		newElement = (Element) oldElement.clone();
		newElement = newElement.setAttribute(LDAttributes.ID, this.newName);

	
		this.setVisible(true);
	}

	@Override
	public void dialogYes() {
		StyleSetter.setStyleValue(oldElement, newElement);
		StyleSetter.replaceID(oldName, newName);
	}

	@Override
	public void dialogNo() {
		StyleSetter.setStyleValue(oldElement, newElement);
	}
}
