package edu.org.server.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

	private static final Properties	properties	= new Properties();

	static {
		File file = new File("config.properties");

		InputStream input = null;

		try {
			input = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		if (input == null) {
			throw new RuntimeException("Arquivo de propriedades nao encontrado 'config.properties'");
		}
		try {
			Config.properties.load(input);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static String getValue(String propName) {
		return Config.properties.getProperty(propName);
	}
}
