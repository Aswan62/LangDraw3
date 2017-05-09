package windows;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import selectStyles.StyleSelectorComponent;
import tabPane.StylePane;


public class StyleWindow extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 2667236727433268594L;
	public static boolean editByTextInput = true;
	public StylePane stylePane;

	public StyleWindow() {
		setTitle("Style manager");
		getContentPane().setLayout(new BorderLayout(0, 0));
		this.setMinimumSize(new Dimension(280, 300));
		stylePane = new StylePane();
		StyleSelectorComponent styleSelector = new StyleSelectorComponent(this);
		JSplitPane splitPane = new JSplitPane();
		splitPane.setContinuousLayout(true);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setTopComponent(styleSelector);
		splitPane.setBottomComponent(stylePane);
		getContentPane().add(splitPane, BorderLayout.CENTER);
		pack();
	}
}
