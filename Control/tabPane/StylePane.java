package tabPane;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;

import org.jdesktop.swingx.JXPanel;

import enums.LDXMLTags;

import style.ArrowStyle;
import style.ArrowStyleSource;
import style.BRStyle;
import style.BRStyleSource;
import style.BaseStyle;
import style.BaseStyleSource;
import style.MarkStyle;
import style.MarkStyleSource;
import style.TextBoxStyle;
import style.TextBoxStyleSource;

import java.awt.Dimension;

public class StylePane extends JXPanel{

	/**
	 *
	 */
	private static final long serialVersionUID = 3758469034536496508L;
	private JScrollPane styleScrollPane;
	public BodyPane bodyPanel;
	public SpanInMarkPane spanPanel;
	public SpanInMarkPane inMarkPanel;
	public MarkPane markPanel;
	public ArrowPane arrowPanel;
	public TextBoxPane textBoxPanel;
	public BRPane brPanel;

	public StylePane() {
		this.setLayout(new BorderLayout());

		styleScrollPane = new JScrollPane();
		this.add(styleScrollPane,BorderLayout.CENTER);

		BaseStyleSource bodySource = new BaseStyleSource(LDXMLTags.Body);
		bodyPanel = new BodyPane(bodySource);

		BaseStyleSource spanSource = new BaseStyleSource(LDXMLTags.Span);
		spanPanel = new SpanInMarkPane(spanSource);

		BaseStyleSource inMarkSource = new BaseStyleSource(LDXMLTags.InMark);
		inMarkPanel = new SpanInMarkPane(inMarkSource);

		BRStyleSource brSource = new BRStyleSource();
		brPanel = new BRPane(brSource);

		MarkStyleSource markSource = new MarkStyleSource();
		markPanel = new MarkPane(markSource);

		ArrowStyleSource arrowSource = new ArrowStyleSource();
		arrowPanel = new ArrowPane(arrowSource);

		TextBoxStyleSource textboxSource = new TextBoxStyleSource();
		textBoxPanel = new TextBoxPane(textboxSource);

		this.setMinimumSize(new Dimension(270, 100));
	}

	public void setStylePane(String styleName, BaseStyle baseStyle) {
		styleScrollPane.getViewport().removeAll();
		if(styleName  == LDXMLTags.Body) {
			bodyPanel.initializeValue(baseStyle);
			styleScrollPane.setViewportView(bodyPanel);
		}
		else if(styleName  == LDXMLTags.Span) {
			spanPanel.initializeValue(baseStyle);
			styleScrollPane.setViewportView(spanPanel);
		}
		else if(styleName  == LDXMLTags.BR) {
			brPanel.initializeValue((BRStyle) baseStyle);
			styleScrollPane.setViewportView(brPanel);
		}
		else if(styleName  == LDXMLTags.InMark) {
			inMarkPanel.initializeValue(baseStyle);
			styleScrollPane.setViewportView(inMarkPanel);
		}
		else if(styleName  == LDXMLTags.Mark) {
			markPanel.initializeValue((MarkStyle) baseStyle);
			styleScrollPane.setViewportView(markPanel);
		}
		else if(styleName  == LDXMLTags.TextBox) {
			textBoxPanel.initializeValue((TextBoxStyle) baseStyle);
			styleScrollPane.setViewportView(textBoxPanel);
		}

		styleScrollPane.repaint();
	}


	public void setStylePane(String arrow, ArrowStyle arrowStyle) {
		styleScrollPane.getViewport().removeAll();
		arrowPanel.initializeValue(arrowStyle);
		styleScrollPane.setViewportView(arrowPanel);
		styleScrollPane.repaint();
	}


}
