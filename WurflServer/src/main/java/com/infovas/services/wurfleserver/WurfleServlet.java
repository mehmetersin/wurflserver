package com.infovas.services.wurfleserver;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sourceforge.wurfl.core.Device;
import net.sourceforge.wurfl.core.GeneralWURFLEngine;

public class WurfleServlet extends HttpServlet {

	private static GeneralWURFLEngine engine = null;

	final static Logger logger = LoggerFactory.getLogger(WurfleServlet.class);
	
	public Util util = new Util();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String agent = req.getParameter("user-agent");
		String capabilities = req.getParameter("capability");
		String[] caps = capabilities.split(",");
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");

		logger.debug("Request Parameters . Agent {} ,Capabilities {}", agent,
				capabilities);

		String remoteId = req.getRemoteAddr();
		String remoteHost = req.getRemoteHost();
		
		
		
		try {
			// ApplicationContext context = new ClassPathXmlApplicationContext(
			// "application-context.xml");

			if (engine == null) {

				String filePath = util.getConfig("wurflFile");
				logger.info("Wurfle file loading File Path :{}.", filePath);
				engine = new GeneralWURFLEngine(filePath, null);

			}
			Device dev = engine.getDeviceForRequest(agent);

			if (dev.getUserAgent().equals("")) {
				out.print("DEVICENOTFOUND");
				logger.error("Device not found for this user agent {}", agent);
				return;
			}

			StringBuffer xmlStr = new StringBuffer();
			for (int i = 0; i < caps.length; i++) {
				String cap = caps[i];
				String value = "NOTFOUND";
				try {
					value = dev.getCapability(cap);
				} catch (Exception e) {
					logger.error(
							"Device capability parameter not found Parameter :"
									+ cap, e);
				}
				if (i > 0) {
					xmlStr.append("|" + cap + "=" + value);
				} else {
					xmlStr.append(cap + "=" + value);
				}

			}
			logger.debug("Response Message {}", xmlStr.toString());
			out.print(xmlStr.toString());
		} catch (Exception e) {
			logger.error("Error while processing", e);
			out.print("ERROR");
		}

	}
}