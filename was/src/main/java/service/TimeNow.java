package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.was.sample.http.Request;
import com.test.was.sample.http.Response;
import com.test.was.sample.http.SimpleServlet;

import common.TimeUtils;

public class TimeNow implements SimpleServlet {
	
	private Logger logger = LoggerFactory.getLogger(TimeNow.class);
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doGet(Request request, Response response) throws Exception {
		String city = (String) request.getParameter("city");
		if(city == null) {
			city = "Asia/Seoul";
		}
		StringBuilder sb = new StringBuilder();
		logger.trace("city = {} " , city);
		TimeUtils time = new TimeUtils();
		String timeNowStr = time.getWorldTime(city);
		
		logger.trace("timeNowStr = {} " , timeNowStr);
		
		sb.append("<html>")
		.append("<body>")
		.append(timeNowStr)
		.append("<a href=\"service.TimeNow?city=Japan \" >새로고침</a>")
		.append("</body>")
		.append("</html>");
		response.createBody("text/html", sb.toString());
	}

	@Override
	public void distory() {
		// TODO Auto-generated method stub
	}

	@Override
	public String getServletName() {
		// TODO Auto-generated method stub
		return "TimeNowServlet...";
	}

}
