package com.test.was.sample.http;

public interface SimpleServlet {
	
	public void init();
	
	public void doGet(Request request, Response response) throws Exception;
	
	public void distory();
	
	public String getServletName();
}
