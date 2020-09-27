package com.shadow.simulation.framework.http.server;

import com.shadow.simulation.framework.http.DispatcherServlet;
import com.shadow.simulation.framework.support.URL;
import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;

/**
 * @author shadow
 * @create 2020-09-24
 * @description
 */
public class HttpServer {

	public void start(URL url) {
		Tomcat tomcat = new Tomcat();

		Server server = tomcat.getServer();
		Service service = server.findService("Tomcat");

		Connector connector = new Connector();
		connector.setPort(url.getPort());

		Engine engine = new StandardEngine();
		engine.setDefaultHost(url.getHostname());

		Host host = new StandardHost();
		host.setName(url.getHostname());

		String contextPath = "";
		Context context = new StandardContext();
		context.setPath(contextPath);
		context.addLifecycleListener(new Tomcat.FixContextListener());

		host.addChild(context);
		engine.addChild(host);

		service.setContainer(engine);
		service.addConnector(connector);

		tomcat.addServlet(contextPath,"dispatcher",new DispatcherServlet());
		context.addServletMappingDecoded("/*","dispatcher");

		try {
			tomcat.start();
			tomcat.getServer().await();
		} catch (LifecycleException e) {
			e.printStackTrace();
		}
	}
}
