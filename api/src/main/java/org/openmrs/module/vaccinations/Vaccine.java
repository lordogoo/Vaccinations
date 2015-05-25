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

import java.io.Serializable;
import org.openmrs.BaseOpenmrsObject;
import org.openmrs.BaseOpenmrsMetadata;

/**
 * It is a model class. It should extend either {@link BaseOpenmrsObject} or {@link BaseOpenmrsMetadata}.
 */
public class Vaccine extends BaseOpenmrsObject implements Serializable {

	//private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String indication_name;
	private double dose;
	private String dosing_unit;
	private String route;
	private Boolean scheduled;


	public Boolean getScheduled() {
		return scheduled;
	}

	public void setScheduled(Boolean scheduled) {
		this.scheduled = scheduled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIndication_name() {
		return indication_name;
	}

	public void setIndication_name(String indication_name) {
		this.indication_name = indication_name;
	}

	public double getDose() {
		return dose;
	}

	public void setDose(double dose) {
		this.dose = dose;
	}

	public String getDosing_unit() {
		return dosing_unit;
	}

	public void setDosing_unit(String dosing_unit) {
		this.dosing_unit = dosing_unit;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}
	
	@Override
	public Integer getId() {
		return id;
	}
	
	@Override
	public void setId(Integer id) {
		this.id = id;
	}	
	
}