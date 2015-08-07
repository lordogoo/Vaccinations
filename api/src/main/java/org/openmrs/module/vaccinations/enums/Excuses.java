package org.openmrs.module.vaccinations.enums;


import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openmrs.module.vaccinations.util.Serializers.ExcusesSerializer;

/**
 * Created by Serghei Luchianov on 2015-06-25.
 */
@JsonSerialize(using = ExcusesSerializer.class)
public enum Excuses {
    WrongVaccine("Wrong vaccine administered"),
    OutOfStock("Vaccine is out of stock"),
    Expired("Vaccine is expired"),
    NoExcuse("Other");

    private final String name;

    private Excuses(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
