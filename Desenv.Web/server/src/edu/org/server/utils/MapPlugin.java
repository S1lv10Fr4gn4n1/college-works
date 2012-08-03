package edu.org.server.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class MapPlugin {

	private static final Properties	properties	= new Properties();

	static {
		File file = new File("mapPlugin.properties");

		InputStream input = null;

		try {
			input = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		if (input == null) {
			throw new RuntimeException("Arquivo de propriedades nao encontrado 'mapPlugin.properties'");
		}
		try {
			MapPlugin.properties.load(input);
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
		return MapPlugin.properties.getProperty(propName);
	}
}
