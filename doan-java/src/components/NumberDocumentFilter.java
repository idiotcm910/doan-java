package components;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class NumberDocumentFilter extends DocumentFilter {
	@Override 
	public void insertString(DocumentFilter.FilterBypass fb, int offset, String _text, AttributeSet attr) throws BadLocationException{
		if(_text.length() == 0) {
			return;
		}

		int valueASCII = _text.charAt(0);
		// kiem tra xem du lieu nhap vao co phai la so' khong
		if(valueASCII < 48 || valueASCII > 57) {
			return;
		}

		super.insertString(fb, offset, _text, attr);
	}

	@Override
	public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String _text, AttributeSet attrs) throws BadLocationException {
		if(_text.length() == 0) {
			fb.remove(0, fb.getDocument().getLength());
			super.insertString(fb, offset, _text, attrs);
			return;
		}

		int valueASCII = _text.charAt(0);
		// kiem tra xem du lieu nhap vao co phai la so' khong
		if(valueASCII < 48 || valueASCII > 57) {
			return;
		}

		super.replace(fb, offset, length, _text, attrs);
	}
}
