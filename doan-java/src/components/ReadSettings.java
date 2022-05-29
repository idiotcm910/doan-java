package components;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.HashMap;
import java.util.Map;

public class ReadSettings {
	private static ReadSettings instance = null;

	private String pathFile;
	private Map<String, String> configs;
	private boolean wasReadSettings;

	private ReadSettings() {
		configs = new HashMap<String, String>();	
		wasReadSettings = false;
		pathFile = "./src/settings.txt";
	}

	public static ReadSettings  getInstance() {
		if(instance == null) {
			instance = new ReadSettings();
		}

		return instance;
	}

	public void readFile() {
		if(wasReadSettings) {
			return;
		}

		FileInputStream fileInputStream = null;
		BufferedReader br = null;

		try {
			fileInputStream = new FileInputStream(pathFile);
			br = new BufferedReader(new InputStreamReader(fileInputStream));
			String line = null;

			while((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, "=");

				if(st.hasMoreTokens()) {
					String configName = st.nextToken().trim();
					String configValue = st.nextToken().trim();

					configs.put(configName, configValue);
				}
			}

			wasReadSettings = true;
		}
		catch(FileNotFoundException ex) {
			ex.printStackTrace();
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		finally {
			try {
				fileInputStream.close();
				br.close();
			}
			catch(IOException ex) {
				ex.printStackTrace();
			}
		}
	}	

	/**
	 * Lấy giá trị của config có name la đối số truyền vào
	 * @return String trả về giá trị của cofig nếu tìm thấy 
	 * @return null trả vê null nếu không tìm thấy
	 * */
	
	public String getConfig(String name) {
		String value = null;
		
		if(!wasReadSettings) {
			readFile();	
		}

		if(configs.containsKey(name)) {
			value = configs.get(name);
		}

		return value;
	}
}
