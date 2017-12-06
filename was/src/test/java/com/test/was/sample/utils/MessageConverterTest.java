package com.test.was.sample.utils;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.test.was.sample.model.ErrorMessage;

public class MessageConverterTest {
	
	/**
	 * Error Message JSON Object Convert Test
	 */
	@Test
	public void messageJsonToObjectTest() {
		ContextErrorMessage contextErrorMessage = new ContextErrorMessage();
		List<ErrorMessage> errorMessageList = contextErrorMessage.contextErrorMessage();
		Assert.assertNotNull(errorMessageList);
		errorMessageList.forEach(item -> System.out.println(item));
	}
	
}
