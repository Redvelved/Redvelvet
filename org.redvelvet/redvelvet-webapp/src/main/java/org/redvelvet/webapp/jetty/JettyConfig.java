package org.redvelvet.webapp.jetty;

import javax.management.RuntimeErrorException;

import org.redvelvet.core.impl.BaseConfig;

/**
 * @Title: JettyConfig.java
 * @Package org.redvelvet.webapp.jetty
 * @Description: Jetty相关配置(由 BaseConfig 装饰而得到)
 * @author yzh yzh yingzh@getui.com
 * @date 2016年3月8日
 */

public class JettyConfig {
	
	private  BaseConfig baseConfig=BaseConfig.getInstance();
	
	private final static int port = 8080;
	private final static int minThreads = 10;
	private final static int maxThreads = 200;
	private final static int maxIdleTime = 10000;
	
	private final static String resourceBase = "src/main/webapp";
	private final static String descriptor = "src/main/webapp/WEB-INF/web.xml";
	private final static String context = "/"; // 其实是项目名
	
	
	/*public JettyConfig(String filepath){
		throw new RuntimeErrorException(null, " Not Compelete Yet!");
	}*/
	
	public int getPort() {
		return baseConfig.getProperty(CFG_Jetty_Key.Port, port);
	}

	public int getMinThreads() {
		return baseConfig.getProperty(CFG_Jetty_Key.MinThreads, minThreads);
	}

	public int getMaxThreads() {
		return baseConfig.getProperty(CFG_Jetty_Key.MaxThreads, maxThreads);
	}

	public int getMaxIdleTime() {
		return baseConfig.getProperty(CFG_Jetty_Key.MaxIdleTime, maxIdleTime);
	}

	public String getResourceBase() {
		return baseConfig.getProperty(CFG_Jetty_Key.ResourceBase, resourceBase);
	}

	public String getDescriptor() {
		return baseConfig.getProperty(CFG_Jetty_Key.Descriptor, descriptor);
	}

	public String getContext() {
		return baseConfig.getProperty(CFG_Jetty_Key.Context, context);
	}
	
	
	static class CFG_Jetty_Key{
		public final static String  Port="redvelvet.servejetty.port";
		public final static String  MinThreads="redvelvet.servejetty.minThreads";
		public final static String  MaxThreads="redvelvet.servejetty.maxThreads";
		public final static String  MaxIdleTime="redvelvet.servejetty.maxIdleTime";
		public final static String  ResourceBase="redvelvet.servejetty.resourceBase";
		public final static String  Descriptor="redvelvet.servejetty.descriptor";
		public final static String  Context="redvelvet.servejetty.context";
	}
}
