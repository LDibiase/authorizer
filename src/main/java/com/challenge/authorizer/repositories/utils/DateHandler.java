package com.challenge.authorizer.repositories.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class DateHandler extends StdDeserializer<LocalDateTime> {

	public DateHandler() {
		this((JavaType) null);
	}

	protected DateHandler(Class<?> vc) {
		super(vc);
	}

	protected DateHandler(JavaType valueType) {
		super(valueType);
	}

	@Override
	public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException {

		String date = jp.getText();

		try {
			Instant instant = Instant.parse(date);
			LocalDateTime formatDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);

			return formatDateTime;

		} catch (Exception e) {
			return null;
		}
	}
}
