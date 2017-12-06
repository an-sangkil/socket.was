package com.test.was.sample.http;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.test.was.sample.http.Response;
import com.test.was.sample.model.ErrorMessage;

public class ResponseTest {
	
	Response response;
	@Before
	public void test (){
		response = new Response();
	}
	
	/**
	 * 정의된 Error message JSON 읽어 오기
	 */
	@Test
	public void searchFilterTest () {
		List<ErrorMessage> errorMessageList = response.searchFilter("wastest");
		Assert.assertNotNull(errorMessageList);
		errorMessageList.forEach(p -> System.out.println(p.getRootDir()));
	}
	
	/**
	 * 정의된 Error message JSON 읽어 오기
	 */
	@Test
	public void searchTest () {
		ErrorMessage errorMessage = response.search("root");
		Assert.assertNotNull(errorMessage);
		Assert.assertEquals(errorMessage.getRootDir(), "root");
	}
	
}
