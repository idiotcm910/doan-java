package common;

import java.awt.Image;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.Dimension;
import java.io.File;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

public class Util {
	
	public static ImageIcon getImage(String url, int width, int height) {
		ImageIcon img = new ImageIcon(url);
		width = (width < 1)? 50 : width;
		height = (height < 1)? 50 : height;

		Image resizeImg = img.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(resizeImg);
	}

	/**
	 * Hàm dùng đê lấy font thêm thủ công vào chương trình
	 * @param keyFont tên font cần lấy, vi dụ Constants.FONT_DEFAULT
	 * @param style nhận vào 1 chuỗi la style của font, gồm 3 chuỗi style "NORMAL", "BOLD", "ITALIC"
	 * @param size kích thước của font
	 * */
	public static Font getFont(String keyFont, String style, int size) {
		size = (size < 1)? 15 : size;

		Font fontReturned = null;

		String strFormat = "./src/assets/fonts/%s/%s-%s.ttf";
		String fileUrl = "";

		switch(style) {
			case "BOLD":
				fileUrl = String.format(strFormat, keyFont, keyFont, "Bold");	
				break;
			case "NORMAL":
				fileUrl = String.format(strFormat, keyFont, keyFont, "Regular");
				break;
		}
		
		try {
			File font_file = new File(fileUrl);
			if(font_file.exists()) {
			}
			fontReturned = Font.createFont(Font.TRUETYPE_FONT, font_file);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}

		return fontReturned.deriveFont(Font.PLAIN, size);
	}
	public static Dimension getSizeOfString(String text, Font font) {
		FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);
		
		int textWidth = (int)(font.getStringBounds(text, frc).getWidth());
		int textHeight = (int)(font.getStringBounds(text, frc).getHeight());

		return new Dimension(textWidth + 10, textHeight + 10);
	}

	public static int calculatePrecent(int precent, int value) {
		return (int)(value * ((float)precent / 100));
	}

	// luu y: can kiem tra xem tren window co the duoc height cua title bar co giong' nhu tren linux khong
	public static int getHeightTitleBarOfFrame() {
		int height = 0;

		javax.swing.JFrame frame = new javax.swing.JFrame();
		frame.pack();
		height = frame.getInsets().top;
		frame.dispose();
		
		return height;
	}

	public static String getDateNow() {
		java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = new java.util.Date();
		return dateFormat.format(date);
	}

	public static String convertMoneyToString(int number) {
		String money = "";
		String str = Integer.toString(number);
		int soDu = str.length() - ((str.length() / 3) * 3);
		
		int index = (soDu == 0)? 2 : soDu - 1;

		for(int i = 0; i < str.length(); ++i) {
			money += str.charAt(i);
			if(i == index && i != str.length() - 1) {
				money += ".";
				index += 3;
			}		
		}

		return money + "đ";
	}

	public static int convertMoneyStringToInt(String moneyStr) {
		moneyStr = moneyStr.substring(0, moneyStr.length() - 1);

		// loại bỏ dấu . 
		if(moneyStr.length() > 3) {
			moneyStr = moneyStr.replaceAll("\\.", "");
		}

		return Integer.parseInt(moneyStr);
	}

	public static Dimension getSizeApp() {
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		int widthApp = (int)size.getWidth();
		int heightApp = (int)size.getHeight();
		return new Dimension(widthApp, heightApp);
	}

	public static String getExtensionFile(File f) {
      String ext = null;
      String s = f.getName();
      int i = s.lastIndexOf('.');
   
      if (i > 0 &&  i < s.length() - 1) {
         ext = s.substring(i).toLowerCase();
      }
      return ext;
   } 
}
