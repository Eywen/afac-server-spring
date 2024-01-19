package com.tfm.afac.data.model;



import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.lang.reflect.Type;

public class SearchGuideOptionSerializer extends JsonSerializer<SearchGuideOptionEnum> {
    /*public JsonElement serialize(SearchGuideOptionEnum search, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("ID", search.getId());
        object.addProperty("name", search.getName());
        return object;
    }*/

    @Override
    public void serialize(SearchGuideOptionEnum searchGuideOptionEnum, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

    }
}
