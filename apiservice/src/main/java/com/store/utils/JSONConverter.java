package com.store.utils;

import com.google.gson.*;

public class JSONConverter {

	public static <E> String getJson(E entity) {
		Gson gon = new Gson();
		String jsonString = gon.toJson(entity);
		return jsonString;
	}

	public static <E> String getJson(E entity, boolean prtNull) {
		if (prtNull == true) {
			Gson gon = new GsonBuilder().serializeNulls().create();
			return gon.toJson(entity);
		} else {
			return getJson(entity);
		}
	}

	public static <T> T getObject(String json, Class<T> clazz) {
		Gson gon = new Gson();
		T result = (T) gon.fromJson(json, clazz);
		return result;
	}
}
