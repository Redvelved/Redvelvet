package org.redvelvet.webapp;

import org.redvelvet.webapp.jetty.JettyWebServer;

/**   
* @Title: JettyWebServer_Test.java 
* @Package org.redvelvet.webapp 
* @Description: TODO(用一句话描述该文件做什么) 
* @author yzh yzh yingzh@getui.com
* @date 2016年3月8日  
*/

public class JettyWebServer_Test {

	
	public static void main(String[] args) {
		JettyWebServer jettyWebServer=new JettyWebServer();
		jettyWebServer.init().server();
	}
}
