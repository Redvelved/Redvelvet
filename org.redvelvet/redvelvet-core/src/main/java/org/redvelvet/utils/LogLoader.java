package org.redvelvet.utils;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * 
* @ClassName: LogLoader 
* @Description: Base on LogBack
* @author yzh yzh yingzh@getui.com
* @date 2016年3月8日 上午11:43:58 
*
 */

public class LogLoader {
	
	public static void load() throws IOException, JoranException {
		LogLoader.load(System.getProperty("user.dir") + "/config/logback.xml");
	}
	
	public static void load(String externalConfigFileLocation) throws IOException, JoranException {
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		File externalConfigFile = new File(externalConfigFileLocation);
		if (!externalConfigFile.exists()) {
			throw new IOException("Logback External Config File Parameter does not reference a file that exists");
		} else {
			if (!externalConfigFile.isFile()) {
				throw new IOException("Logback External Config File Parameter exists, but does not reference a file");
			} else {
				if (!externalConfigFile.canRead()) {
					throw new IOException("Logback External Config File exists and is a file, but cannot be read.");
				} else {
					JoranConfigurator configurator = new JoranConfigurator();
					configurator.setContext(lc);
					lc.reset();
					configurator.doConfigure(externalConfigFileLocation);
					StatusPrinter.printInCaseOfErrorsOrWarnings(lc);
				}
			}
		}
	}
}