package com.dayside.vacation.common.util;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class GsonUtil {

	/**
	 * Json 문자열을 Object로 변환
	 * @param <T>
	 * @param jsonData
	 * @param classOfT
	 * @return
	 */
	public static  <T> T deserialization(String jsonData, Class<T> classOfT) {
		Gson gson = new GsonBuilder().registerTypeAdapter(Map.class, new MapDeserializer())
									.create();
		
		return gson.fromJson(jsonData, classOfT);
	}
	
	
	/**
	 * Json 문자열을 Object로 변환
	 * @param <T>
	 * @param jsonData
	 * @param typeOf
	 * @return
	 */
	public static  <T> T deserialization(String jsonData, Type typeOf) {
		Gson gson = new GsonBuilder().registerTypeAdapter(Map.class, new MapDeserializer())
				.create();
		
		return gson.fromJson(jsonData, typeOf);
	}

	/**
	 * Object를 json문자열로 변환
	 * @param object
	 * @return
	 */
	public static String serialization(Object object) {
		Gson gson = new GsonBuilder()
				.create();

		return gson.toJson(object);
	}
	
	/**
	 * Map<String, Object> 타입으로 변환시 정수가 소수형으로 바뀌는 문제 해결
	 * @author April
	 *
	 */
	private static class MapDeserializer implements JsonDeserializer<Object> {
		public Object deserialize(JsonElement json, Type typeOfT,
				JsonDeserializationContext context) {
			
			if (json.isJsonNull())
				return null;
			else if (json.isJsonPrimitive())
				return handlePrimitive(json.getAsJsonPrimitive());
			else if (json.isJsonArray())
				return handleArray(json.getAsJsonArray(), context);
			else
				return handleObject(json.getAsJsonObject(), context);
		}

		private Object handlePrimitive(JsonPrimitive json) {
			
			if (json.isBoolean())
				return json.getAsBoolean();
			else if (json.isString())
				return json.getAsString();
			else {
				BigDecimal bigDec = json.getAsBigDecimal();
				// Find out if it is an int type
				try {
					bigDec.toBigIntegerExact();
					try {
						return bigDec.intValueExact();
					} catch (ArithmeticException e) {
					}
					return bigDec.longValue();
				} catch (ArithmeticException e) {
				}
				// Just return it as a double
				return bigDec.doubleValue();
			}
		}

		private Object handleArray(JsonArray json,
				JsonDeserializationContext context) {
			Object[] array = new Object[json.size()];
			for (int i = 0; i < array.length; i++)
				array[i] = context.deserialize(json.get(i), Object.class);
			return array;
		}

		private Object handleObject(JsonObject json,
				JsonDeserializationContext context) {
			
			Map<String, Object> map = new HashMap<String, Object>();
			for (Map.Entry<String, JsonElement> entry : json.entrySet())
				map.put(entry.getKey(),
						deserialize(entry.getValue(), Object.class, context));
			return map;
		}
	}
}