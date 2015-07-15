package org.openmrs.module.vaccinations.enums;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openmrs.module.vaccinations.util.Serializers.RoutesSerializer;

/**
 * Created by Serghei Luchianov on 2015-06-25.
 */
@JsonSerialize(using = RoutesSerializer.class)
public enum Routes {
    Oral ("160240", "Oral"),
    Intramuscular ("160243", "Intramuscular"),
    Subcutaneous ("160245", "Subcutaneous"),
    Intranasal ("161253", "Intranasal"),
    Transdermal ("162391", "Transdermal"),
    Intradermal ("123456", "Intradermal");

    private final String name;
    private final String conceptId;

    private Routes(String conceptId, String name){
        this.conceptId = conceptId;
        this.name = name;
    }

    public String getConceptId() {
        return conceptId;
    }


    public String getName() {
        return name;
    }
}
