package com.test.was.sample.http;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.was.sample.utils.HttpRequestUtils;

/**
 * 
 * @author skan
 *
 */
public class Request {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);
	
	String path ="";
	String fileName ="";
	String query = "";
	Map<String, String> params = new HashMap<String, String>();
	
	public String getPath() {
		return path;
	}

	public String getFileName() {
		return fileName;
	}

	public Object getParameter(String key) {
		return params.get(key);
	}

	/**
	 * URL 파싱
	 * @param urlPath (/ext/index.html?parameter=1)
	 * @throws IOException
	 */
	public void parseUrl(String urlPath) throws IOException{

		Pattern pattern = Pattern.compile("(.*\\/)([^\\/?]*)\\??(.*$)");
		Matcher matcher = pattern.matcher(urlPath);
		
		if(matcher.matches()){
			path = matcher.group(1);
			fileName = matcher.group(2);
			query = matcher.group(3);
			
			logger.trace(" = {}" ,path);
			logger.trace(" = {}" ,fileName);
			logger.trace(" = {}" ,query);
			params = HttpRequestUtils.parseQueryString(query);
		}
	}
	
	public void requestMappling(String mappingURL) throws Exception {
		Request request = new Request();
		Response response = new Response();
		this.requestMappling(mappingURL, request, response);
	}
	
	/**
	 * Mapping 되는 URL method 실행
	 * @param mappingURL
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public void requestMappling(String mappingURL, Request request, Response response) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		// XXX XML JSON에서 매핑 되도록 변경
		
		// view 파일 검사  "";
			
			Class<?> cls = Class.forName(mappingURL);
			Object objInstance = cls.newInstance();
			Method method = cls.getDeclaredMethod("doGet", Request.class, Response.class);
			method.invoke(objInstance, new Object[]{request,response});
			
			// mapping 파일이 없음 오류 404
	}

	public static void main(String[] args) throws Exception {
		Request request = new Request();
		request.requestMappling("service.Hello");
		
		String url = "http://127.0.0.1/ext/index.html?kkk=1&aaaa=2";
		request.parseUrl(url);
	}
}
