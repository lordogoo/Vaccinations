package org.openmrs.module.vaccinations.util.Serializers;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.openmrs.module.vaccinations.enums.BodySites;

import java.io.IOException;

/**
 * Created by Serghei on 2015-06-25.
 */
public class BodySitesSerializer extends JsonSerializer<BodySites> {

    @Override
    public void serialize(BodySites value, JsonGenerator generator,
                          SerializerProvider provider) throws IOException,
            JsonProcessingException {

        generator.writeStartObject();
        generator.writeFieldName("name");
        generator.writeString(value.getName());
        generator.writeEndObject();
    }
}