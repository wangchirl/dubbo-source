package com.shadow.simulation.framework.http;

import com.shadow.simulation.framework.http.server.HttpServerHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author shadow
 * @create 2020-09-24
 * @description
 */
public class DispatcherServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		new HttpServerHandler().handler(req,resp);
	}
}
