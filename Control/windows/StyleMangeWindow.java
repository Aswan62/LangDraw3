package windows;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import java.awt.BorderLayout;
import javax.swing.JButton;

import java.io.File;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

public class StyleMangeWindow extends JDialog {
	private JList list;
	private DefaultListModel model;
	private File targetDir;


	public StyleMangeWindow() {
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationByPlatform(true);
		setMinimumSize(new Dimension(100, 200));
		setModal(true);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setSize(new Dimension(100, 200));

		model = new DefaultListModel();

		targetDir = null;
		targetDir = new File("./Styles/");

		if (!targetDir.exists()) {
			targetDir.mkdir();
		}

		File[] fileList = targetDir.listFiles();
		String[] fileNames = new String[fileList.length];
		for (int i = 0; i < fileList.length; i++) {
			String fileName = fileList[i].getName();
			if(fileName.endsWith(".xml")) {
				fileName = fileName.substring(0, fileName.length() - 4);
				model.addElement(fileName);
			}
		}

		list = new JList(model);
		getContentPane().add(list, BorderLayout.CENTER);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteFile();
			}
		});
		getContentPane().add(btnDelete, BorderLayout.SOUTH);
	}


	private void deleteFile(){
		Object[] names = list.getSelectedValues();

		for (Object name : names) {
			File file = new File(targetDir, (String)name + ".xml");
			if(file.exists()) file.delete();
		}

		if (!list.isSelectionEmpty()) {
			int minIndex = list.getMinSelectionIndex();
			int maxIndex = list.getMaxSelectionIndex();
			model.removeRange(minIndex, maxIndex);
		}
	}
}
