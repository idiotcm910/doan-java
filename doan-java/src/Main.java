import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.UIManager;
import javax.swing.JFrame;
import GUI.MainGUI;
import GUI.LoginFrame;
import BLL.ProductBLL;
import common.Util;
import common.FrameDisplayRegistry;
import components.ReadSettings;

import common.AppConnection;

public class Main {
	public static void main(String[] args) {
		Dimension sizeApp = Toolkit.getDefaultToolkit().getScreenSize();
		loadData();
				
		System.out.println(ReadSettings.getInstance().getConfig("databaseName"));


		FrameDisplayRegistry displayRegistry = FrameDisplayRegistry.getInstance();
		
		LoginFrame loginFrame = new LoginFrame();
		displayRegistry.mainFrameRegistry(loginFrame);
	}

	public static void loadData() {
		// load setting
		ReadSettings.getInstance().readFile();
	}
}
