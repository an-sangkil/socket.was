package com.test.was.sample.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ErrorMessage {
	private String rootDir;
	private DetailInformation[] detail;
	public String getRootDir() {
		return rootDir;
	}
	public void setRootDir(String rootDir) {
		this.rootDir = rootDir;
	}
	public DetailInformation[] getDetail() {
		return detail;
	}
	public void setDetail(DetailInformation[] detail) {
		this.detail = detail;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public String toJsonString() {
		return com.test.was.common.util.MessageConverter.convertObjectToJson(this);
	}
	
}
