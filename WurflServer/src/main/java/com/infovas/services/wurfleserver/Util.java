package com.infovas.services.wurfleserver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

public class Util {

	//mebitirgen
	
	static Properties configProp = null;

	public String getConfig(String param) {

		if (configProp == null) {
 
			configProp = new Properties();

			InputStream in = this.getClass().getClassLoader()
					.getResourceAsStream("config.properties");
			try {
				configProp.load(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return configProp.getProperty(param);
	}

}
