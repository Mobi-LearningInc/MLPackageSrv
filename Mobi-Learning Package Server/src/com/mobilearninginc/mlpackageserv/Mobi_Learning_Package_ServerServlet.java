package com.mobilearninginc.mlpackageserv;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class Mobi_Learning_Package_ServerServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
}
