package org.openmrs.module.vaccinations;

import org.openmrs.BaseOpenmrsObject;

import java.io.Serializable;

/**
 * Created by Serghei Luchianov on 14/Jul/2015.
 */
public class Manufacturer implements Serializable {
    private Integer id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}