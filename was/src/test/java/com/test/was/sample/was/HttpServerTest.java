package com.test.was.sample.was;

import org.junit.Before;
import org.junit.Test;

import com.test.was.sample.utils.HeaderParse;
import com.test.was.sample.webserver.HttpServer;

public class HttpServerTest {
	
	@Before
	public void startWebServerTest() {
		String args[] = null;
		HttpServer.main(args);
	}
	
	@Test
	public void headerParseTest(){
		HeaderParse headerParse = new HeaderParse();
	}
	
}
