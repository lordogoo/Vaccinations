/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.vaccinations;

import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.BaseOpenmrsObject;

import java.io.Serializable;
import java.util.Date;

/**
 * It is a model class. It should extend either {@link BaseOpenmrsObject} or {@link BaseOpenmrsMetadata}.
 */
public class SimpleAdverseReaction extends BaseOpenmrsObject implements Serializable {

	//private static final long serialVersionUID = 1L;

    public SimpleAdverseReaction() {
    }

    public SimpleAdverseReaction(Integer id, Date date, String adverse_event, String grade, Integer vaccination_id) {
        this.id = id;
        this.date = date;
        this.adverse_event = adverse_event;
        this.grade = grade;
        this.vaccination_id = vaccination_id;
    }

    public SimpleAdverseReaction(AdverseReaction adverseReaction){
        if (!(null == adverseReaction)) {
            this.id = adverseReaction.getId();
            this.date = adverseReaction.getDate();
            this.adverse_event = adverseReaction.getAdverse_event();
            this.grade = adverseReaction.getGrade();
            this.vaccination_id = adverseReaction.getVaccination_id();
        }
    }

    private Integer id;
    private Date date;
    private String adverse_event;
    private String grade;
    private Integer vaccination_id;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAdverse_event() {
        return adverse_event;
    }

    public void setAdverse_event(String adverse_event) {
        this.adverse_event = adverse_event;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

	@Override
	public Integer getId() {
		return id;
	}
	
	@Override
	public void setId(Integer id) {
		this.id = id;
	}

    public Integer getVaccination_id() {
        return vaccination_id;
    }

    public void setVaccination_id(Integer vaccination_id) {
        this.vaccination_id = vaccination_id;
    }
}