package org.openmrs.module.vaccinations.enums;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openmrs.module.vaccinations.util.Serializers.BodySitesSerializer;

/**
 * Created by Serghei Luchianov on 2015-06-25.
 */
@JsonSerialize(using = BodySitesSerializer.class)
public enum BodySites {
    Thigh("Upper outer thigh"),
    Buttock("Buttock"),
    UpperArmDeltoid("Upper arm (deltoid)"),
    UpperArmTricep("Upper arm (tricep)"),
    PosteriorForearm("Forearm (Outer/Posterior)"),
    AnteriorForearm("Forearm (Inner/Anterior)"),
    Mouth("Mouth"),
    Nostril("Nostril"),
    LowerLeg("Lower leg"),
    Abdomen("Abdomen"),
    Chest("Chest"),
    UpperBack("Upper back"),
    LowerBack("Lower back");

    private final String name;

    private BodySites(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
