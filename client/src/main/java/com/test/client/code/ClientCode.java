package com.test.client.code;

import com.test.client.HttpClientConnector;


public enum ClientCode implements CommonCode  {
	
	
	/////////////////////////////////////////////////////////////////////
	// API URL 
	/////////////////////////////////////////////////////////////////////
	
	TEST_403ERROR(HttpClientConnector.ERROR_403, "403 Error Test"),
	TEST_404ERROR(HttpClientConnector.ERROR_404, "404 Error Test"),
	TEST_500ERROR(HttpClientConnector.ERROR_500, "500 Error Test"),
	
	TEST_HELLO(HttpClientConnector.HELLO, "�׽�Ʈ ��ο�"),				
	TEST_SERVICE_HELLO(HttpClientConnector.SERVICE_HELLO, "�׽�Ʈ ���� ��ο�");
	
	 
	// ... 
	private String apiURL;
	private String apiName;
	
	
	private ClientCode(String apiURL, String apiName) {
		this.apiURL  = apiURL;
		this.apiName = apiName;
	}
	
	/**
	 * API URL
	 */
	public String getApiURL() {
		return apiURL;
	}
	
	/**
	 * ���� API �̸�
	 * @return
	 */
	public String getApiName() {
		return apiName;
	}

	@Override
	public String getText() {
		return this.name();
	}
	
}