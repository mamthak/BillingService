package com.rightminds.biller.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.omg.CORBA.portable.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class JsonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonUtil() {
    }

    static {
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String toJson(Object obj, SerializationFeature... serializationFeatures) {
        try {
            ObjectWriter writer = OBJECT_MAPPER.writer().withFeatures(serializationFeatures);
            return writer.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.error("Exception occurred during converting an object to Json", e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String attributesString, Class<T> valueType) {
        try {
            return OBJECT_MAPPER.readValue(attributesString, valueType);
        } catch (IOException e) {
            LOGGER.error("Exception occurred during creating object from json string", e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(InputStream inputStream, Class<T> valueType) {
        try {
            return OBJECT_MAPPER.readValue(inputStream, valueType);
        } catch (IOException e) {
            LOGGER.error("Exception occurred during creating object from input stream", e);
            throw new RuntimeException(e);
        }
    }
}
