package org.redvelvet.example;


import org.redvelvet.webapp.jetty.JettyWebServer;

/**   
* @Title: Boot.java 
* @Package redvelvet.example 
* @Description: TODO(用一句话描述该文件做什么) 
* @author yzh yzh yingzh@getui.com
* @date 2016年3月8日  
*/

public class Launcher {

	public static void main(String[] args) {
		//!!!
		JettyWebServer jettyWebServer=new JettyWebServer();
		jettyWebServer.init().server();  
	}
	
}
