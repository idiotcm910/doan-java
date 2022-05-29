package components;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

public class TMoneyField extends JTextField {
	public TMoneyField(int limit) {
		AbstractDocument document = (AbstractDocument)getDocument();	
		document.setDocumentFilter(new MoneyDocumentFilter(limit));
	}

	public Long getMoney() {
		String moneyStr = getText();

		if(moneyStr.length() == 0) {
			return Long.parseLong("0");	
		}

		//loại bỏ ký tự "đ"
		moneyStr = moneyStr.substring(0, moneyStr.length() - 1);

		// loại bỏ dấu . 
		if(moneyStr.length() > 3) {
			moneyStr = moneyStr.replaceAll("\\.", "");
		}

		return Long.parseLong(moneyStr);
	}

	public void setText(int money) {
		super.setText(Integer.toString(money));
	}
}

