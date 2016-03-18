package org.redvelvet.webapp.support;

/**   
* @Title: R.java 
* @Package org.redvelvet.webapp.support 
* @Description: TODO(用一句话描述该文件做什么) 
* @author yzh yzh yingzh@getui.com
* @date 2016年3月9日  
*/

public interface R {

	public static class CFG_Jetty_Key{
		public final static String  Port="redvelvet.server.jetty.port";
		public final static String  MinThreads="redvelvet.server.jetty.minThreads";
		public final static String  MaxThreads="redvelvet.server.jetty.maxThreads";
		public final static String  MaxIdleTime="redvelvet.server.jetty.maxIdleTime";
		public final static String  ResourceBase="redvelvet.server.jetty.resourceBase";
		public final static String  Descriptor="redvelvet.server.jetty.descriptor";
		public final static String  Context="redvelvet.server.jetty.context";
	}
}
