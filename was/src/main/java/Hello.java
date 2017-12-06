import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.was.sample.http.Request;
import com.test.was.sample.http.Response;
import com.test.was.sample.http.SimpleServlet;

public class Hello implements SimpleServlet {
	private Logger logger = LoggerFactory.getLogger(Hello.class);
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doGet(Request request, Response response) throws Exception{
		String text = "{ \"key\" : \"HelloWorld\"}";
		logger.trace("jsonStr = {}" , text);
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
