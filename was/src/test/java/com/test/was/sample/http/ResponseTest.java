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
	 * ���ǵ� Error message JSON �о� ����
	 */
	@Test
	public void searchFilterTest () {
		List<ErrorMessage> errorMessageList = response.searchFilter("wastest");
		Assert.assertNotNull(errorMessageList);
		errorMessageList.forEach(p -> System.out.println(p.getRootDir()));
	}
	
	/**
	 * ���ǵ� Error message JSON �о� ����
	 */
	@Test
	public void searchTest () {
		ErrorMessage errorMessage = response.search("root");
		Assert.assertNotNull(errorMessage);
		Assert.assertEquals(errorMessage.getRootDir(), "root");
	}
	
}
