package components;

import javax.swing.text.DocumentFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.AttributeSet;

public class MoneyDocumentFilter extends DocumentFilter {
	private int limit;

	public MoneyDocumentFilter(int limit) {
		this.limit = (limit < 1)? 1 : limit;
	}

	@Override
	public void replace(FilterBypass fb, int offset, int length, String _text, AttributeSet set) throws BadLocationException {
		if(_text.length() == 0) {
			fb.remove(0, fb.getDocument().getLength());
			fb.insertString(0, "0đ", set);
			return;
		}
		int valueASCII = _text.charAt(0);
		// kiem tra xem du lieu nhap vao co phai la so' khong
		if(valueASCII < 48 || valueASCII > 57) {
			return;
		}

		// chu so dau tien khong phai la so 0
		if(fb.getDocument().getLength() == 0 && _text.equals("0")) {
			return;
		}

		String text = fb.getDocument().getText(0, fb.getDocument().getLength());

		// loai bỏ dấu . với ký tự "đ"
		if(text.length() != 0 && text.charAt(text.length() - 1) == 'đ') {
			text = text.substring(0, text.length() - 1);
			text = text.replaceAll("\\.", "");
		}	

		if(_text.length() > 1) {
			text = _text;
		}
		else {
			text += _text;
		}

		// kiem tra dieu kien gioi han chu so
		if(text.length() > limit) {
			return;
		}

		if(text.length() < 4) {
			fb.remove(0, fb.getDocument().getLength());
			fb.insertString(0, text + "đ", set);
			return;
		}
		
		String moneyString = "";

		int soDu = text.length() % 3;
		int soChia = text.length() / 3;
		int indexStart = (soDu == 0)? 3 : soDu;
		int loop = (soDu == 0)? soChia - 1 : (soChia - 1) + 1;

		moneyString += text.substring(0, indexStart) + "." + text.substring(indexStart, indexStart + 3);
		for(int i = 1; i < loop; ++i) {
			indexStart += 3;
			moneyString += "." + text.substring(indexStart, indexStart + 3);
		}

		moneyString += "đ";
		fb.remove(0, fb.getDocument().getLength());
		fb.insertString(0, moneyString, set);
	}
	
	public void insertString(FilterBypass fb, int offset, String str, AttributeSet set) throws BadLocationException {
		fb.insertString(0, str, set);
	}
}
