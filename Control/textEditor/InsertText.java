package textEditor;

import java.awt.Toolkit;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

public class InsertText {
	public static void InsertBR(String id, RSyntaxTextArea area)
	{
		String result = "<BR/>";

		if(id != null && !id.isEmpty()) {
			result = "<BR".concat(" ID=\"").concat(id).concat("\"/>");
		}

		int start = area.getSelectionStart();
		int end = area.getSelectionEnd();

		if(start == end)
		{
			area.replaceSelection(result);
			area.select(start + result.length(), start + result.length());
		} else {
			Toolkit.getDefaultToolkit().beep();
		}

		area.requestFocus();
	}

	public static void InsertSpan(String id, RSyntaxTextArea area)
	{
		String result = "<Span>";

		if(id != null && !id.isEmpty()) {
			result = "<Span".concat(" ID=\"").concat(id).concat("\">");
		}

		int start = area.getSelectionStart();
		int end = area.getSelectionEnd();
		String selectedText = area.getSelectedText();
		if(start != end)
		{
			if(start > end)
			{
				int temp = start;
				start = end;
				end = temp;
			}
			result = result.concat(selectedText).concat("</Span>");
			area.replaceSelection(result);
			area.select(start + result.length(), start + result.length());
		} else {
			Toolkit.getDefaultToolkit().beep();
		}

		area.requestFocus();
	}

	public static void InsertInMark(String id, RSyntaxTextArea area)
	{
		String result = "<InMark Show=\"\"/>";

		if(id != null && !id.isEmpty()) {
			result = "<InMark".concat(" ID=\"").concat(id).concat("\" Show=\"\"/>");
		}

		int start = area.getSelectionStart();
		int end = area.getSelectionEnd();

		if(start == end)
		{
			area.replaceSelection(result);
			area.select(start + result.length() - 3, start + result.length() - 3);
		} else {
			Toolkit.getDefaultToolkit().beep();
		}

		area.requestFocus();
	}

	public static void InsertMark(String id, RSyntaxTextArea area)
	{
		String result = "<Mark Show=\"\">";

		if(id != null && !id.isEmpty()) {
			result = "<Mark".concat(" ID=\"").concat(id).concat("\" Show=\"\">");
		}

		int start = area.getSelectionStart();
		int end = area.getSelectionEnd();
		String selectedText = area.getSelectedText();
		if(start != end)
		{
			if(start > end)
			{
				int temp = start;
				start = end;
				end = temp;
			}
			area.replaceSelection(result.concat(selectedText).concat("</Mark>"));
			area.select(start + result.length() - 2, start + result.length() - 2);
		} else {
			Toolkit.getDefaultToolkit().beep();
		}

		area.requestFocus();
	}
}
