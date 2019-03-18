package com.web2.qoq.rest.messaging.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class JsonTrimmerDeserializer extends JsonDeserializer<String> {
	@Override
	public String deserialize(JsonParser parser, DeserializationContext context) throws IOException {
		return parser.getValueAsString().trim();
	}
}