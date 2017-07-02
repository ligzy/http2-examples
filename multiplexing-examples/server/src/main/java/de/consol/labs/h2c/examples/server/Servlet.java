package de.consol.labs.h2c.examples.server;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Servlet extends HttpServlet {

	/**
	 * 
	 */
	private static final Logger logger = LogManager.getLogger(Servlet.class.getName());

	private static final long serialVersionUID = -6243985929484239357L;
	private static final int DELAY_IN_SECONDS = 6;
	private final ConnectionTracker connectionTracker = ConnectionTracker.getInstance();
	private final AtomicInteger numberOfRequests = new AtomicInteger(0);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("req:"+req.getCookies());
		int numberOfCurrentRequest = numberOfRequests.incrementAndGet();
		int numberOfCurrentConnection = connectionTracker.getNumberOfConnections();
		try {
			Thread.sleep(TimeUnit.SECONDS.toMillis(DELAY_IN_SECONDS));
		} catch (InterruptedException e) {
			throw new ServletException(e);
		}
		resp.setContentType("text/plain");
		resp.getWriter()
				.write("This is the response for request number " + numberOfCurrentRequest + " using connection number "
						+ numberOfCurrentConnection + " after " + DELAY_IN_SECONDS + " seconds delay.");
	}
}
