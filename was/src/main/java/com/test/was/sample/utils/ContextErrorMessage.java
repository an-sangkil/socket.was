package com.test.was.sample.utils;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.slf4j.LoggerFactory;

import com.test.was.common.util.MessageConverter;
import com.test.was.common.util.StringUtils;
import com.test.was.sample.model.ErrorMessage;

public class ContextErrorMessage {
	
	private org.slf4j.Logger logger = LoggerFactory.getLogger(ContextErrorMessage.class);
	
	/**
	 * ���ؽ����� ������ ���� JSON message ���� �о� ��ü�� ��ȯ
	 * @return
	 */
	public List<ErrorMessage> contextErrorMessage() {
		
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("errorfile.json");
		String jsonStr = StringUtils.getStringFromInputStream(is);
		logger.debug("jsonStr = {}" ,jsonStr);
		ErrorMessage[] errorMessage = MessageConverter.convertJsonToObject(jsonStr, ErrorMessage[].class);
		
		return Arrays.asList(errorMessage);
	}

}
