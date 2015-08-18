package org.openmrs.module.vaccinations.util.Serializers;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.openmrs.module.vaccinations.enums.RouteMapEnum;

import java.io.IOException;

/**
 * Created by Serghei on 2015-06-25.
 */
public class RouteMapEnumSerializer extends JsonSerializer<RouteMapEnum> {

    @Override
    public void serialize(RouteMapEnum value, JsonGenerator generator,
                          SerializerProvider provider) throws IOException,
            JsonProcessingException {

        generator.writeStartObject();
        generator.writeFieldName(value.getConceptId());
        generator.writeStartArray();
        for (String i : value.getSites()){
            generator.writeString(i);
        }
        generator.writeEndArray();
        generator.writeEndObject();
    }
}