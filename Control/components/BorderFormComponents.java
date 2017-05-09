package components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.ListCellRenderer;
import style.BaseStyleSource;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;


public class BorderFormComponents extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 895148753649745728L;

	private BaseStyleSource baseStyleSource;

	private JComboBox comboBoxLeft;

	private JComboBox comboBoxRight;

	public BorderFormComponents(BaseStyleSource source) {
		setBackground(SystemColor.control);
		baseStyleSource = source;
		String[] contents = {"Linear", "Curve1", "Curve2", "Turned", "None"};
//		DefaultComboBoxModel modelLeft = new DefaultComboBoxModel();
//	    modelLeft.addElement(new ComboLabel("Linear",  new ImageIcon("./Images/leftLinear.png")));
//	    modelLeft.addElement(new ComboLabel("Curve1",  new ImageIcon("./Images/leftCurve1.png")));
//	    modelLeft.addElement(new ComboLabel("Curve2",  new ImageIcon("./Images/leftCurve2.png")));
//	    modelLeft.addElement(new ComboLabel("Turned",  new ImageIcon("./Images/leftTurn.png")));
//	    MyCellRenderer renderer = new MyCellRenderer(SwingConstants.LEFT, SwingConstants.RIGHT);

		comboBoxLeft = new JComboBox(contents);
		comboBoxLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				baseStyleSource.setBorderLeftForm(comboBoxLeft.getSelectedIndex());
			}
		});
		add(comboBoxLeft);
//		comboBoxLeft.setRenderer(renderer);

//	    MyCellRenderer renderer2 = new MyCellRenderer(SwingConstants.RIGHT, SwingConstants.LEFT);
//		DefaultComboBoxModel modeRight = new DefaultComboBoxModel();
//		modeRight.addElement(new ComboLabel("Linear",  new ImageIcon("./Images/rightLinear.png")));
//		modeRight.addElement(new ComboLabel("Curve1",  new ImageIcon("./Images/rightCurve1.png")));
//		modeRight.addElement(new ComboLabel("Curve2",  new ImageIcon("./Images/rightCurve2.png")));
//		modeRight.addElement(new ComboLabel("Turned",  new ImageIcon("./Images/rightTurn.png")));

		comboBoxRight = new JComboBox(contents);
		comboBoxRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				baseStyleSource.setBorderRightForm(comboBoxRight.getSelectedIndex());
			}
		});
		add(comboBoxRight);
//		comboBoxRight.setRenderer(renderer2);

		this.setPreferredSize(new Dimension(200, 60));
	}


	class ComboLabel{
		  String text;
		  ImageIcon image;

		  ComboLabel(String text, ImageIcon imageIcon){
			  this.text = text;
			  this.image = imageIcon;
		  }

		  public String getText(){
			  return text;
		  }

		  public ImageIcon getIcon(){
			  return image;
		  }
	}

	 class MyCellRenderer extends JLabel implements ListCellRenderer{
		    /**
		 *
		 */
		private static final long serialVersionUID = 6967582101086905441L;

			MyCellRenderer(int textPosition, int alignment){
		      setOpaque(true);
		      this.setHorizontalAlignment(alignment);
		      this.setHorizontalTextPosition(textPosition);
		    }

		    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
		      ComboLabel data = (ComboLabel)value;
		      setText(data.getText());
		      setIcon(data.getIcon());

		      if (isSelected){
		        setForeground(Color.white);
		        setBackground(Color.gray);
		      }else{
		        setForeground(Color.gray);
		        setBackground(Color.white);
		      }

		      return this;
		    }
	 }

	 public void initializeValue(BaseStyleSource spanStyle) {
			this.baseStyleSource = spanStyle;
			comboBoxLeft.setSelectedIndex(baseStyleSource.getBorderLeftFormIndex());
			comboBoxRight.setSelectedIndex(baseStyleSource.getBorderRightFormIndex());
	 }

	 public int getLeftFrom() {
		 return comboBoxLeft.getSelectedIndex();
	 }

	 public int getRightFrom() {
		 return comboBoxRight.getSelectedIndex();
	 }

}
