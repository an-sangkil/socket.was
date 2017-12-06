package com.test.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


public class MessageConverter {
	
	/**
	 * RestTemplate JSON 
	 * 
	 * @return
	 */
	public RestTemplate getRestTemplate() {
		
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(30000);
		clientHttpRequestFactory.setReadTimeout(30000);
		
		RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
		//restTemplate.setErrorHandler(new ClientResponseErrorHandler());
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		
		MappingJackson2HttpMessageConverter map = new MappingJackson2HttpMessageConverter();
		messageConverters.add(map);
		restTemplate.setMessageConverters(messageConverters);
		
		return restTemplate;
	}
	
	
}
