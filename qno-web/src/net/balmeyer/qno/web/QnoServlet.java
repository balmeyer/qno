package net.balmeyer.qno.web;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.balmeyer.qno.Qno;

@SuppressWarnings("serial")
public class QnoServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		
		Qno qno = new Qno();
		
		qno.load("patterns.txt");
		
		String text = qno.execute();
		
		
		resp.getWriter().println(text);
	}
	
	
	 
}
