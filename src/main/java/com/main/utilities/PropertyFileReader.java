package com.main.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileReader {

	String propertyFilename = null;
	Properties commonProperties = null;
	FileInputStream fileStream = null;

	public PropertyFileReader(String propertyFilename) throws IOException {
		this.propertyFilename = propertyFilename;
		fileStream = new FileInputStream(propertyFilename);
		commonProperties = new Properties();
		commonProperties.load(fileStream);
	}
	

	public String getProperty(String key) {
		return commonProperties.getProperty(key);
	}

	public void tearDown() throws IOException {
		fileStream.close();
	}
}
