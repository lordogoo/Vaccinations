package org.openmrs.module.vaccinations.util.Serializers;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.openmrs.module.vaccinations.enums.Excuses;

import java.io.IOException;

/**
 * Created by Serghei on 2015-06-25.
 */
public class ExcusesSerializer extends JsonSerializer<Excuses> {

    @Override
    public void serialize(Excuses value, JsonGenerator generator,
                          SerializerProvider provider) throws IOException,
            JsonProcessingException {

        generator.writeStartObject();
        generator.writeFieldName("name");
        generator.writeString(value.getName());
        generator.writeEndObject();
    }
}