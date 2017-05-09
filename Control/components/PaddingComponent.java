package components;

import org.jdesktop.swingx.JXTaskPane;

import parts.TextBoxPadding;
import style.TextBoxStyleSource;

import javax.swing.UIManager;
import java.awt.BorderLayout;

public class PaddingComponent extends JXTaskPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4247088838415218980L;
	private TextBoxStyleSource textBoxStyleSource;
	private TextBoxPadding textBoxPadding;

	public PaddingComponent(TextBoxStyleSource source) {
		textBoxStyleSource = source;
		setTitle("TextBoxStyle");
		getContentPane().setBackground(UIManager.getColor("control"));

		textBoxPadding = new TextBoxPadding(textBoxStyleSource);
		add(textBoxPadding, BorderLayout.SOUTH);

	}

	public void initializeValue(TextBoxStyleSource source) {
		textBoxStyleSource = source;
		textBoxStyleSource.isInitializing = true;
		textBoxPadding.setPadding(textBoxStyleSource);
		textBoxStyleSource.isInitializing = false;
	}

}
