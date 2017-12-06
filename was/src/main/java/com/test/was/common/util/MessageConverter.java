package com.test.was.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class MessageConverter {
	
	/**
	  * JsonString을 Object Class로 전환
	  * 
	  * 
	  * @param jsonString
	  * @param objectClass
	  * @return
	  */
	public static <T> T convertJsonToObject(final String jsonString, final Class<T> objectClass) {
		Gson gson = new Gson();
		return gson.fromJson(jsonString, objectClass);
	}

	/**
	 * Object Class를 Json String으로 전환
	 * 
	 * 
	 * @param jsonString
	 * @param objectClass
	 * @return
	 */
	public static String convertObjectToJson(Object objectClass) {
		Gson gson = new GsonBuilder().registerTypeAdapter(String.class, new JsonStringConverter()).create();
		return gson.toJson(objectClass);
	}
	
}
