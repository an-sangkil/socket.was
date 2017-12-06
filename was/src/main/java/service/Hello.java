package service;

import com.test.was.sample.http.Request;
import com.test.was.sample.http.Response;
import com.test.was.sample.http.SimpleServlet;

public class Hello implements SimpleServlet {

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doGet(Request request, Response response) throws Exception {
		String text = "{ \"key\" : \"HelloWorld2\"}";
		response.createBody("application/json", text);
	}

	@Override
	public void distory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getServletName() {
		// TODO Auto-generated method stub
		return "service.Hello HI!";
	}
	
	
}
