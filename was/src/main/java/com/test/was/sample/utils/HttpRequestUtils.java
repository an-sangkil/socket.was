package com.test.was.sample.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class HttpRequestUtils {

	/**
	 * @param queryString은 URL에서 ? 이후에 전달되는 name=value 
	 * @return
	 */
	public static Map<String, String> parseQueryString(String queryString) {
		
		Map<String, String> paramMap = new HashMap<>();
		if (StringUtils.isNotEmpty(queryString)) {
			String[] tokens = queryString.split("&");
			for (String token: tokens) {
				String ar[] = token.split("=");
				for (int i = 0; i < ar.length; i++) {
					paramMap.put(ar[0], ar[1]);
				}
				
			}
		}
		return paramMap;
	}
	
	public static void main(String[] args) throws IOException {
		String url = "kkk=1&aaaa=2";
		Map<String, String> paramMap = HttpRequestUtils.parseQueryString(url);
		System.out.println(paramMap);
	}
		

}
