package org.openmrs.module.vaccinations.enums;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openmrs.module.vaccinations.util.DosingUnitsSerializer;

/**
 * Created by Serghei Luchianov on 2015-06-25.
 */
@JsonSerialize(using = DosingUnitsSerializer.class)
public enum DosingUnits {
    International (162264, "International units"),
    Ampule (162335, "Ampule(s)"),
    Drop (162356, "Drop"),
    Ounce (162358, "Fluid Ounce"),
    Gram (161554, "Gram"),
    Milligram (161553, "Milligram"),
    Milliequivalent (162364, "Milliequivalent"),
    Microgram (162366, "Microgram"),
    Milliliter (162263, "Milliliter"),
    Tablet (1513, "Tablet"),
    Unit (162381, "Unit"),
    Vial (162382, "Vial");


    private final String name;
    private final int conceptId;

    private DosingUnits(int conceptId, String name){
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
