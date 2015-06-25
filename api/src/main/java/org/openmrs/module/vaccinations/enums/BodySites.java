package org.openmrs.module.vaccinations.enums;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openmrs.module.vaccinations.util.BodySitesSerializer;

/**
 * Created by Serghei Luchianov on 2015-06-25.
 */
@JsonSerialize(using = BodySitesSerializer.class)
public enum BodySites {
    Thigh("Upper outer thigh"),
    Buttock("Buttock"),
    Deltoid("Upper arm (deltoid)"),
    Tricep("Upper arm (tricep)"),
    OuterForearm("Forearm (Outer/Posterior)"),
    InnerForearm("Forearm (Inner/Anterior)"),
    NA("N/A (Oral, Nasal)");

    private final String name;

    private BodySites(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
