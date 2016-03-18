package org.redvelvet.core;

import java.io.IOException;

import org.redvelvet.utils.LogLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.core.joran.spi.JoranException;

/**
 * @Title: LogLoader_Test.java
 * @Package org.redvelvet.core
 * @Description: TODO(用一句话描述该文件做什么)
 * @author yzh yzh yingzh@getui.com
 * @date 2016年3月8日
 */

public class LogLoader_Test {
	

	public static void main(String[] args) throws IOException, JoranException {
           LogLoader.load();
           
           Logger logger = LoggerFactory.getLogger(LogLoader_Test.class);
           
           logger.debug("sadasd");
           logger.error("sadasdas");
	}
}
