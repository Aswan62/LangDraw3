package components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class CloseButtonTabbedPane extends JTabbedPane {
	private final Icon icon;
	private final Dimension buttonSize;
	public CloseButtonTabbedPane(Icon icon) {
		super();
		//icon = new CloseTabIcon();
		this.icon = icon;
		buttonSize = new Dimension(icon.getIconWidth(), icon.getIconHeight());
	}

	public void addTab(String title, final JComponent content, boolean isClosable) {
		JPanel tab = new JPanel(new BorderLayout());
		tab.setOpaque(false);
		JLabel label = new JLabel(title);
		tab.add(label,  BorderLayout.CENTER);

		if(isClosable) {
			label.setBorder(BorderFactory.createEmptyBorder(0,0,0,4));
			JButton button = new JButton(icon);
			button.setPreferredSize(buttonSize);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					removeTabAt(indexOfComponent(content));
				}
			});
			tab.add(button, BorderLayout.EAST);
			tab.setBorder(BorderFactory.createEmptyBorder(2,1,1,1));
		}

		super.addTab(null, content);
		setTabComponentAt(getTabCount()-1, tab);
	 }
}
