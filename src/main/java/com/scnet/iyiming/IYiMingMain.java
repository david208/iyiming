package com.scnet.iyiming;

import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IYiMingMain {
	private static Logger logger = LoggerFactory.getLogger(IYiMingMain.class);

	public static void main(String[] args) throws Exception {
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(8187);
		tomcat.getConnector().setURIEncoding("UTF-8");
		String path = IYiMingMain.class.getResource("/").getPath();
		tomcat.addWebapp("/IYiMing", path.substring(0, path.indexOf("target")) + "src/main/webapp");
		tomcat.start();
		logger.info("Started tomcat");
		tomcat.getServer().await();
	}
}
