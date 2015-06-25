package org.openmrs.module.vaccinations.enums;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openmrs.module.vaccinations.util.RoutesSerializer;

/**
 * Created by Serghei Luchianov on 2015-06-25.
 */
@JsonSerialize(using = RoutesSerializer.class)
public enum Routes {
    Oral (160240, "Oral"),
    Intramuscular (160243, "Intramuscular"),
    Subcutaneous (160245, "Subcutaneous"),
    Intranasal (161253, "Intranasal"),
    Transdermal (162391, "Transdermal");

    private final String name;
    private final int conceptId;

    private Routes(int conceptId, String name){
        this.conceptId = conceptId;
        this.name = name;
    }

    public int getConceptId() {
        return conceptId;
    }


    public String getName() {
        return name;
    }
}
