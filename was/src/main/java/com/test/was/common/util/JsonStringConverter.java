package com.test.was.common.util;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Object => JSON Str 변환시 NULL "" 치환
 * @author skan
 *
 */
public class JsonStringConverter implements JsonSerializer<String>, JsonDeserializer<String> {

	@Override
	public JsonElement serialize(String src, Type typeOfSrc, JsonSerializationContext context) {
		if(src == null) {
			return new JsonPrimitive("");
		} else {
			return new JsonPrimitive(src.toString());
		}
	}

	@Override
	public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		return json.getAsJsonPrimitive().getAsString();
	}
}
