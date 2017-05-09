package selectStyles;

import itemList.StyleList;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.jdesktop.swingx.JXPanel;
import org.jdom.Element;

import style.BaseStyle;
import style.StyleSetter;
import tabPane.StylePane;
import windows.EditorWindow;
import windows.StyleWindow;

import enums.LDAttributes;
import enums.LDXMLTags;


import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Set;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.Insets;
import javax.swing.UIManager;

public class StyleSelectorComponent extends JXPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = -535095612304419413L;
	private static JTree tree;
	private static DefaultMutableTreeNode root, bodyNode, brNode, spanNode, inMarkNode, markNode, textBoxNode, arrowNode;
	private StylePane stylePane;
	private JButton btnRename;
	private JButton btnDelete;
	private String targetName;
	private JButton btnClone;

	public StyleSelectorComponent(final StyleWindow styleWindow) {
		setMinimumSize(new Dimension(55, 110));
		this.stylePane = styleWindow.stylePane;
		root = new DefaultMutableTreeNode("Style");

	    bodyNode = new DefaultMutableTreeNode("Body");
	    brNode = new DefaultMutableTreeNode("BR");
	    spanNode = new DefaultMutableTreeNode("Span");
	    inMarkNode = new DefaultMutableTreeNode("InMark");
	    markNode = new DefaultMutableTreeNode("Mark");
	    arrowNode = new DefaultMutableTreeNode("Arrow");
	    textBoxNode = new DefaultMutableTreeNode("TextBox");


	    root.add(bodyNode);
	    root.add(brNode);
	    root.add(spanNode);
	    root.add(inMarkNode);
	    root.add(markNode);
	    root.add(arrowNode);
	    root.add(textBoxNode);

	    setLayout(new BorderLayout(0, 0));

		this.setPreferredSize(new Dimension(290,110));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		scrollPane.setMinimumSize(new Dimension(100, 23));
		tree = new JTree(root);
		tree.setBackground(UIManager.getColor("control"));
		tree.setMaximumSize(new Dimension(300, 19));
		tree.setShowsRootHandles(true);
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		ImageIcon icon = new ImageIcon();
		renderer.setLeafIcon(icon);
		ImageIcon closeIcon = new ImageIcon();
		ImageIcon openIcon = new ImageIcon();
		renderer.setClosedIcon(closeIcon);
		renderer.setOpenIcon(openIcon);
		renderer.setBackgroundNonSelectionColor(UIManager.getColor("control"));
		tree.setCellRenderer(renderer);
		
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				selectionChanged();
			}
		});
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setRootVisible(false);
		EditorWindow.expandAll(tree);
		scrollPane.setViewportView(tree);

	    JPanel panel = new JPanel();
	    add(panel, BorderLayout.SOUTH);

	    btnClone = new JButton("New Style");
	    btnClone.setMargin(new Insets(0, 0, 0, 0));
	    btnClone.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		pressCloneButton();
	    	}
	    });
	    panel.setLayout(new FormLayout(new ColumnSpec[] {
	    		FormFactory.PREF_COLSPEC,
	    		FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
	    		FormFactory.PREF_COLSPEC,
	    		FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
	    		FormFactory.PREF_COLSPEC,},
	    	new RowSpec[] {
	    		RowSpec.decode("21px"),}));
	    panel.add(btnClone, "1, 1, left, top");
	    btnClone.setAlignmentX(Component.CENTER_ALIGNMENT);

	    btnRename = new JButton("Rename");
	    btnRename.setMargin(new Insets(0, 0, 0, 0));
	    panel.add(btnRename, "3, 1, left, top");
	    btnRename.setAlignmentX(Component.CENTER_ALIGNMENT);
	    btnRename.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		pressRenameButton();
	    	}
	    });

	    btnDelete = new JButton("Delete");
	    btnDelete.setMargin(new Insets(0, 0, 0, 0));
	    panel.add(btnDelete, "5, 1, left, top");
	    btnDelete.setAlignmentX(Component.CENTER_ALIGNMENT);
	    btnDelete.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		pressDeleteButton();
	    	}
	    });

	    tree.setSelectionRow(0);
	}

	private void pressCloneButton() {
		TreePath path = tree.getSelectionPath();
			
		if(tree.getLastSelectedPathComponent() != null && tree.getSelectionPath().getParentPath() != null) {
			targetName = tree.getSelectionPath().getLastPathComponent().toString();
		}
		
		Element targetElement = detectTarget(targetName);
		if(targetElement.getAttribute(LDAttributes.ID) != null) {
			path = path.getParentPath();
		}
		
		String result = JOptionPane.showInputDialog(null, "Please input new ID.", this.btnClone.getText(), JOptionPane.QUESTION_MESSAGE);
		
		if(result != null && !result.isEmpty()) {
			if(!isIDExist(targetElement.getName(), result)) {
				Element newElement = (Element) targetElement.clone();
				newElement.setAttribute(LDAttributes.ID, result);
				StyleSetter.setStyleValue(null, newElement);
			} else {
				JOptionPane.showMessageDialog(null, "The same ID name exists. Please change the name and save again.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			try {
				Thread.sleep(500);
				setStyles();
				searchTree(tree, path, result);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));				
			}
		}
	}

	private void pressRenameButton() {
		TreePath path = tree.getSelectionPath().getParentPath();
		
		if(tree.getLastSelectedPathComponent() != null && tree.getSelectionPath().getParentPath() != null) {
			targetName = tree.getSelectionPath().getLastPathComponent().toString();
		}
		Element targetElement = detectTarget(targetName);

		String result = JOptionPane.showInputDialog(null, "Please input new ID.", targetName);

		if(result != null && !result.isEmpty() && result != targetName) {
			if(!isIDExist(targetElement.getName(), result)) {
				Element newElement = (Element) targetElement.clone();
				newElement.setAttribute(LDAttributes.ID, result);
				StyleSetter.setStyleValue(targetElement, newElement);
			}
			else {
				JOptionPane.showMessageDialog(null, "The same ID name exists. Please change the name and save again.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			StyleSetter.replaceID(targetName, result);
		}
		
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		try {
			Thread.sleep(500);
			setStyles();
			searchTree(tree, path, result);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));				
		}
	}

	private void pressDeleteButton() {
		TreePath path = tree.getSelectionPath().getParentPath();
		if(tree.getLastSelectedPathComponent() != null && tree.getSelectionPath().getParentPath() != null) {
			targetName = tree.getSelectionPath().getLastPathComponent().toString();
		}
		Element targetElement = detectTarget(targetName);
		StyleSetter.removeStyleValue(targetElement);
		StyleSetter.deleteID(targetName);
		
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		try {
			Thread.sleep(500);
			setStyles();
			tree.setSelectionPath(path);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));				
		}
	}

	private Element detectTarget(String targetName) {
		Element targetElement = null;

		if(targetName != null) {
			String nodeName = tree.getSelectionPath().getParentPath().getLastPathComponent().toString();
			if(nodeName == "Style") {
				if(LDXMLTags.Span.equalsIgnoreCase(targetName)) {
					if(StyleList.SpanStyleList.get(null) != null)
					{
						targetElement = StyleList.SpanStyleList.get(null).xmlElement;
					}
				}
				if(LDXMLTags.InMark.equalsIgnoreCase(targetName)) {
					if(StyleList.InMarkStyleList.get(null) != null)
					{
						targetElement = StyleList.InMarkStyleList.get(null).xmlElement;
					}
				}
				if(LDXMLTags.BR.equalsIgnoreCase(targetName)) {
					if(StyleList.BRStyleList.get(null) != null)
					{
						targetElement = StyleList.BRStyleList.get(null).xmlElement;
					}
				}
				if(LDXMLTags.Mark.equalsIgnoreCase(targetName)) {
					if(StyleList.MarkStyleList.get(null) != null)
					{
						targetElement = StyleList.MarkStyleList.get(null).xmlElement;
					}
				}
				if(LDXMLTags.Arrow.equalsIgnoreCase(targetName)) {
					if(StyleList.ArrowStyleList.get(null) != null)
					{
						targetElement = StyleList.ArrowStyleList.get(null).xmlElement;
					}
				}
				if(LDXMLTags.TextBox.equalsIgnoreCase(targetName)) {
					if(StyleList.TextBoxStyleList.get(null) != null)
					{
						targetElement = StyleList.TextBoxStyleList.get(null).xmlElement;
					}
				}
			}

			if(LDXMLTags.Span.equalsIgnoreCase(nodeName)) {
				if(StyleList.SpanStyleList.get(targetName) != null)
				{
					targetElement = StyleList.SpanStyleList.get(targetName).xmlElement;
				}
			}
			if(LDXMLTags.InMark.equalsIgnoreCase(nodeName)) {
				if(StyleList.InMarkStyleList.get(targetName) != null)
				{
					targetElement = StyleList.InMarkStyleList.get(targetName).xmlElement;
				}
			}
			if(LDXMLTags.Mark.equalsIgnoreCase(nodeName)) {
				if(StyleList.MarkStyleList.get(targetName) != null)
				{
					targetElement = StyleList.MarkStyleList.get(targetName).xmlElement;
				}
			}
			if(LDXMLTags.BR.equalsIgnoreCase(nodeName)) {
				if(StyleList.BRStyleList.get(targetName) != null)
				{
					targetElement = StyleList.BRStyleList.get(targetName).xmlElement;
				}
			}
			if(LDXMLTags.Arrow.equalsIgnoreCase(nodeName)) {
				if(StyleList.ArrowStyleList.get(targetName) != null)
				{
					targetElement = StyleList.ArrowStyleList.get(targetName).xmlElement;
				}
			}
			if(LDXMLTags.TextBox.equalsIgnoreCase(nodeName)) {
				if(StyleList.TextBoxStyleList.get(targetName) != null)
				{
					targetElement = StyleList.TextBoxStyleList.get(targetName).xmlElement;
				}
			}
		}

		return targetElement;
	}


	public static void setStyles(){
		DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
		String[] sortedKeys = null;
		
		if(StyleList.BRStyleList != null) {
	    	brNode.removeAllChildren();
	    	sortedKeys = sortHashMap(StyleList.BRStyleList.keySet());
	    	for(String key : sortedKeys) {
	    		if(key != null) brNode.add(new DefaultMutableTreeNode(key));
	    	}
		}

	    if(StyleList.SpanStyleList != null) {
	    	spanNode.removeAllChildren();
	    	sortedKeys = sortHashMap(StyleList.SpanStyleList.keySet());
	    	for(String key : sortedKeys) {
	    		if(key != null) spanNode.add(new DefaultMutableTreeNode(key));
	    	}
	    }

	    if(StyleList.InMarkStyleList != null) {
	    	inMarkNode.removeAllChildren();
	    	sortedKeys = sortHashMap(StyleList.InMarkStyleList.keySet());
	    	for(String key : sortedKeys) {
	    		if(key != null) inMarkNode.add(new DefaultMutableTreeNode(key));
	    	}
	    }

	    if(StyleList.MarkStyleList != null) {
	    	markNode.removeAllChildren();
	    	sortedKeys = sortHashMap(StyleList.MarkStyleList.keySet());
	    	for(String key : sortedKeys) {
	    		if(key != null) markNode.add(new DefaultMutableTreeNode(key));
	    	}
	    }
	    
	    if(StyleList.ArrowStyleList != null) {
	    	arrowNode.removeAllChildren();
	    	sortedKeys = sortHashMap(StyleList.ArrowStyleList.keySet());
	    	for(String key : sortedKeys) {
	    		if(key != null) arrowNode.add(new DefaultMutableTreeNode(key));
	    	}
	    }
	    
	    if(StyleList.TextBoxStyleList != null) {
	    	textBoxNode.removeAllChildren();
	    	sortedKeys = sortHashMap(StyleList.TextBoxStyleList.keySet());
	    	for(String key : sortedKeys) {
	    		if(key != null) textBoxNode.add(new DefaultMutableTreeNode(key));
	    	}
	    }

	    model.reload();
	    tree.setSelectionRow(0);
	    EditorWindow.expandAll(tree);
	}

	private void selectionChanged() {
		String styleName = "";
		String nodeName = "";
		if(tree.getLastSelectedPathComponent() != null) styleName = tree.getLastSelectedPathComponent().toString();
		if(styleName != null && tree.getSelectionPath() != null) nodeName = tree.getSelectionPath().getParentPath().getLastPathComponent().toString();
		if(nodeName == "Style") {
			if(styleName.equalsIgnoreCase(LDXMLTags.Body)){
				setButtonState(false);
				stylePane.setStylePane(LDXMLTags.Body, StyleList.BodyStyle);
				return;
			}
			else if(styleName.equalsIgnoreCase(LDXMLTags.BR)){
				setButtonState(false);
				stylePane.setStylePane(LDXMLTags.BR, StyleList.BRStyleList.get(null));
				return;
			}
			else if(styleName.equalsIgnoreCase(LDXMLTags.Span)) {
				setButtonState(false);
				stylePane.setStylePane(LDXMLTags.Span, StyleList.SpanStyleList.get(null));
				return;
			}
			else if(styleName.equalsIgnoreCase(LDXMLTags.InMark)) {
				setButtonState(false);
				stylePane.setStylePane(LDXMLTags.InMark, StyleList.InMarkStyleList.get(null));
				return;
			}
			else if(styleName.equalsIgnoreCase(LDXMLTags.Mark)) {
				setButtonState(false);
				stylePane.setStylePane(LDXMLTags.Mark,  StyleList.MarkStyleList.get(null));
				return;
			}
			else if(styleName.equalsIgnoreCase(LDXMLTags.Arrow)) {
				setButtonState(false);
				stylePane.setStylePane(LDXMLTags.Arrow,  StyleList.ArrowStyleList.get(null));
				return;
			}
			else if(styleName.equalsIgnoreCase(LDXMLTags.TextBox)) {
				setButtonState(false);
				stylePane.setStylePane(LDXMLTags.TextBox,  StyleList.TextBoxStyleList.get(null));
				return;
			}
		}
		else {
			if(nodeName.equalsIgnoreCase(LDXMLTags.BR)) {
				setButtonState(true);
				stylePane.setStylePane(LDXMLTags.BR, StyleList.BRStyleList.get(styleName));
				return;
			}
			else if(nodeName.equalsIgnoreCase(LDXMLTags.Span)) {
				setButtonState(true);
				stylePane.setStylePane(LDXMLTags.Span, StyleList.SpanStyleList.get(styleName));
				return;
			}
			else if(nodeName.equalsIgnoreCase(LDXMLTags.InMark)) {
				setButtonState(true);
				stylePane.setStylePane(LDXMLTags.InMark, StyleList.InMarkStyleList.get(styleName));
				return;
			}
			else if(nodeName.equalsIgnoreCase(LDXMLTags.Mark)) {
				setButtonState(true);
				stylePane.setStylePane(LDXMLTags.Mark, StyleList.MarkStyleList.get(styleName));
				return;
			}
			else if(nodeName.equalsIgnoreCase(LDXMLTags.Arrow)) {
				setButtonState(true);
				stylePane.setStylePane(LDXMLTags.Arrow,  StyleList.ArrowStyleList.get(styleName));
				return;
			}
			else if(nodeName.equalsIgnoreCase(LDXMLTags.TextBox)) {
				setButtonState(true);
				stylePane.setStylePane(LDXMLTags.TextBox, StyleList.TextBoxStyleList.get(styleName));
				return;
			}
		}

		stylePane.setStylePane(null, new BaseStyle());
	}

	public void setButtonState(boolean isOriginalTag) {
		if(tree.getLastSelectedPathComponent() != null && tree.getSelectionPath().getParentPath() != null) {
			String idName = tree.getSelectionPath().getLastPathComponent().toString();
			if(idName == LDXMLTags.Body) btnClone.setEnabled(false);
			else btnClone.setEnabled(true);
		}
		btnRename.setEnabled(isOriginalTag);
		btnDelete.setEnabled(isOriginalTag);
		if(!isOriginalTag) btnClone.setText("New Style");
		else btnClone.setText("Clone Style");
	}

	private boolean isIDExist(String tagName, String id) {
		if(tagName.equalsIgnoreCase(LDXMLTags.BR)) {
			if(StyleList.BRStyleList.get(id) != null) return true;
		}
		else if(tagName.equalsIgnoreCase(LDXMLTags.Span)) {
			if(StyleList.SpanStyleList.get(id) != null) return true;
		}
		else if(tagName.equalsIgnoreCase(LDXMLTags.InMark)) {
			if(StyleList.InMarkStyleList.get(id) != null) return true;
		}
		else if(tagName.equalsIgnoreCase(LDXMLTags.Mark)) {
			if(StyleList.MarkStyleList.get(id) != null) return true;
		}
		else if(tagName.equalsIgnoreCase(LDXMLTags.Arrow)) {
			if(StyleList.ArrowStyleList.get(id) != null) return true;
		}
		else if(tagName.equalsIgnoreCase(LDXMLTags.TextBox)) {
			if(StyleList.TextBoxStyleList.get(id) != null) return true;
		}
		return false;
	}

	public static DefaultMutableTreeNode sortTree(DefaultMutableTreeNode root) {
		if(root != null) {
			for(int i=0;i<root.getChildCount();i++) {
				  DefaultMutableTreeNode node = (DefaultMutableTreeNode) root.getChildAt(i);
				  String nt = node.getUserObject().toString();
				  for(int j=0; j<i; j++) {
					  DefaultMutableTreeNode prevNode = (DefaultMutableTreeNode) root.getChildAt(j);
					  String np = prevNode.getUserObject().toString();
					  if(nt.compareToIgnoreCase(np)<0) {
						  root.insert(node, j);
						  root.insert(prevNode, i);
					  }
				  }
				  if(node.getChildCount() > 0) node = sortTree(node);
			}
		}

		return root;
	}
	
	static private String[] sortHashMap(Set<String> unsortedList){
		String[] result = null;
		
		if(unsortedList != null){
			Object[] keys = unsortedList.toArray();
			ArrayList<String> correctKeys = new ArrayList<String>();
			for(int i = 0; i < keys.length; i++) {  
				if(keys[i] != null) correctKeys.add((String)keys[i]);
			} 
			
			
			if(correctKeys != null) {
				Collections.sort(correctKeys, new Comparator<String>() {
					  @Override
					    public int compare(String obj0, String obj1) {
					        return obj0.toLowerCase().compareTo(obj1.toLowerCase());
					    }
					});
				result = Arrays.asList(correctKeys.toArray()).toArray(new String[correctKeys.size()]);
			}
		}
		
		return result;
	}
	
	private static void searchTree(JTree tree, TreePath path, String q) {
		TreeNode node = (TreeNode)path.getLastPathComponent();
		if(node==null) return;
		if(node.toString().equals(q)) {
			tree.setSelectionPath(path);
			tree.scrollPathToVisible(path);
			node = null;
			return;
		}
		if(!node.isLeaf() && node.getChildCount()>=0) {
			Enumeration e = node.children();
			while(e.hasMoreElements())
				searchTree(tree, path.pathByAddingChild(e.nextElement()), q);
		}
	}
}
