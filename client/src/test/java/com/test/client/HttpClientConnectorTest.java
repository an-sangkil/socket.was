package com.test.client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.test.client.code.ClientCode;
import com.test.client.model.HelloModel;

public class HttpClientConnectorTest {
	
	HttpClientConnector httpClientConnector =  null;
	@Before
	public void init() {
		httpClientConnector  = new HttpClientConnector();
	}
	
	@Test(expected=Exception.class)
	public void test403() throws Exception {
		httpClientConnector.connecterSendGET(ClientCode.TEST_403ERROR, HelloModel.class, null);
	}
	
	@Test(expected=Exception.class)
	public void test404() throws Exception {
		httpClientConnector.connecterSendGET(ClientCode.TEST_404ERROR, HelloModel.class, null);
	}
	
	@Test(expected=Exception.class)
	public void test500() throws Exception {
		httpClientConnector.connecterSendGET(ClientCode.TEST_500ERROR , HelloModel.class, null);
	}
	
	@Test
	public void helloTest() throws Exception {
		HelloModel helloModel = httpClientConnector.connecterSendGET(ClientCode.TEST_HELLO, HelloModel.class, null);
		System.out.println(helloModel.toString());
		Assert.assertNotNull(helloModel);
		Assert.assertEquals(helloModel.getKey(), "HelloWorld");
	}
	
	@Test
	public void helloServiceTest() throws Exception {
		HelloModel helloModel = httpClientConnector.connecterSendGET(ClientCode.TEST_SERVICE_HELLO, HelloModel.class, null);
		System.out.println(helloModel.toString());
		Assert.assertNotNull(helloModel);
		Assert.assertEquals(helloModel.getKey(), "HelloWorld2");
	}

}
