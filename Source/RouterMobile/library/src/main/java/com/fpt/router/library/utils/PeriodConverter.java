package com.fpt.router.library.utils;

/**
 * Created by Huynh Quang Thao on 10/13/15.
 */

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import org.joda.time.Period;

import java.lang.reflect.Type;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 10/13/15.
 */
public class PeriodConverter implements JsonSerializer<Period>, JsonDeserializer<Period> {

    public static final Type PERIOD_TYPE = new TypeToken<Period>(){}.getType();

    @Override
    public JsonElement serialize(Period src, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(src.toString());
    }

    @Override
    public Period deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        // Do not try to deserialize null or empty values
        if (json.getAsString() == null || json.getAsString().isEmpty()) {
            return null;
        }

        return Period.parse(json.getAsString());
    }


}
