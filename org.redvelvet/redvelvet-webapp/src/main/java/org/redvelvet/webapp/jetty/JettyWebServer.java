package org.redvelvet.webapp.jetty;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import org.redvelvet.core.IWebServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @ClassName: JettyWebServer
 * @Description: Jetty 作为嵌入式 Web容器
 * @author yzh yzh yingzh@getui.com
 * @date 2016年3月8日 上午9:51:20
 *
 */
public class JettyWebServer extends Server implements IWebServer{

	private static Logger logger = LoggerFactory.getLogger(JettyWebServer.class);
	
	private JettyConfig jettyConfig =new JettyConfig();
	
	private int port;
	private int minThreads;
	private int maxThreads;
	private int maxIdleTime;
	
	private String resourceBase;
	private String descriptor;
	private String context; //其实是项目名
	
	private ThreadPool threadPool;

	private Connector[] connectors;

	private Handler handler;

	/**
	 * 
	 * @Title: setThreadPool0 @Description: 设置线程池 @param @param
	 * threadPool @param @return @return JettyServer 返回类型 @throws
	 */
	public JettyWebServer setThreadPool0(ThreadPool threadPool) {
		this.threadPool = threadPool;
		return this;
	}

	/**
	 * 
	 * @Title: setConnectors0 @Description: 设置连接参数 @param @param
	 * connectors @param @return @return JettyServer 返回类型 @throws
	 */
	public JettyWebServer setConnectors0(Connector[] connectors) {
		this.connectors = connectors;
		return this;
	}

	public JettyWebServer setHandler0(Handler handler) {
		this.handler = handler;
		return this;
	}

	/**
	 * 
	* @Title: server 
	* @Description: 服务启动 等待阻塞。
	* @param @return    
	* @return JettyWebServer    返回类型 
	* @throws
	 */
	public JettyWebServer server() {
		try {
			this.start();
			
			logger.debug("##############################################");
			logger.debug("###               Redvelvet                ###");
			logger.debug("##############################################");
			logger.debug("###                                        ###");
			logger.debug("###         Server Strat Successfully      ###");
			logger.debug("###                                        ###");
			logger.debug("##############################################");
			
			
			logger.debug("# Listen Port  :{}",this.port);
			logger.debug("# MinThreads   :{}",this.minThreads);
			logger.debug("# MaxThreads   :{}",this.maxThreads);
			logger.debug("# MaxIdleTime  :{}",this.maxIdleTime);
			logger.debug("# ResourceBase :{}",this.resourceBase);
			logger.debug("# Descriptor   :{}",this.descriptor);
			logger.debug("# Context      :{}",this.context);
			
			this.join();  //同步阻塞。确保完全启动 jetty
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return this;
	}
	/**
	 * 
	* @Title: setConfig 
	* @Description: 设置默认配置
	* @param @param configPath
	* @param @return    
	* @return JettyWebServer    返回类型 
	* @throws
	 */
	public JettyWebServer setConfig() {
		port=jettyConfig.getPort();
		minThreads=jettyConfig.getMinThreads();
		maxThreads=jettyConfig.getMaxThreads();
		maxIdleTime=jettyConfig.getMaxIdleTime();
		resourceBase=jettyConfig.getResourceBase();
		descriptor=jettyConfig.getDescriptor();
		context=jettyConfig.getContext();
		return this;
	}

	/**
	 * 
	* @Title: init 
	* @Description: 配置初始化参数
	* @param @return    
	* @return JettyWebServer    返回类型 
	* @throws
	 */
	public JettyWebServer init() {
		
		this.setConfig();
		
		if (threadPool == null) {
			QueuedThreadPool threadPool = new QueuedThreadPool();
			threadPool.setMinThreads(minThreads);
			threadPool.setMaxThreads(maxThreads);
			this.threadPool = threadPool;
		}
		this.setThreadPool(threadPool);

		if (connectors == null) {
			SelectChannelConnector connector = new SelectChannelConnector();
			connector.setPort(port);
			connector.setMaxIdleTime(maxIdleTime);
			connector.setAcceptors(Runtime.getRuntime().availableProcessors() + 1);
			connector.setStatsOn(false);
			connector.setLowResourcesConnections(32000);
			connector.setLowResourcesMaxIdleTime(60000 * 10);
			// 解决Windows下重复启动Jetty居然不报告端口冲突的问题.
			connector.setReuseAddress(false);
			this.connectors = new Connector[] { connector };
		}
		this.setConnectors(connectors);

		if (handler == null) {
			WebAppContext webAppContext = new WebAppContext();
			webAppContext.setClassLoader(Thread.currentThread().getContextClassLoader());
			webAppContext.setResourceBase(resourceBase);
			webAppContext.setDescriptor(descriptor);
			webAppContext.setContextPath(context);
			//webAppContext.setErrorHandler(null);
			//webAppContext.setErrorHandler(errorHandler);
			this.handler = webAppContext;
		}
		
		this.setHandler(handler);
		this.setStopAtShutdown(true); // 设置在JVM退出时关闭Jetty的钩子。
		this.setSendServerVersion(false);
		
		this.setSendDateHeader(false);
		this.setGracefulShutdown(1000);
		return this;
	}
}
