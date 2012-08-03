package edu.org.server.utils;

import java.net.URL;
import java.net.URLClassLoader;

import edu.org.common.PluginNotFoundException;

public final class PluginFactory {
	private static String folderLib;
	
	static {
		folderLib = Config.getValue("folderLib");
	}
	
	public static <T> T getPlugin(final String fileExt) throws PluginNotFoundException {
		String implName = MapPlugin.getValue(fileExt);

		if (implName == null) {
			implName = MapPlugin.getValue("all");
		}
		
		String[] info = implName.split("##");

		String jar = folderLib + info[0];
		String className = info[1];
		

		return PluginFactory.getPlugin(jar, className);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getPlugin(final String jarName, final String className) throws PluginNotFoundException {
		try {
			URLClassLoader newInstance = URLClassLoader.newInstance(new URL[] { PluginFactory.class.getResource(jarName) }, PluginFactory.class.getClassLoader());

			Class<?> forName = Class.forName(className, true, newInstance);
			T objResponse = (T) forName.newInstance();
			return objResponse;
		} catch (final Exception ex) {
			throw new PluginNotFoundException("Nao conseguiu criar uma nova instancia " + className, ex);
		}		
	}
	
}
