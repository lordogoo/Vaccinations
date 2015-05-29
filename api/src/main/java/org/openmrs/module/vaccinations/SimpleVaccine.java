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

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.User;

import java.io.Serializable;
import java.util.Date;

/**
 * It is a model class. It should extend {@link BaseOpenmrsObject}.
 */
public class SimpleVaccine extends BaseOpenmrsObject implements Serializable {

	//private static final long serialVersionUID = 1L;


	public SimpleVaccine(Integer id, String name, String indication_name, Double dose, Integer dose_number, String dosing_unit, String route, boolean scheduled) {
		this.id = id;
		this.name = name;
		this.indication_name = indication_name;
		this.dose = dose;
		this.dose_number = dose_number;
		this.dosing_unit = dosing_unit;
		this.route = route;
		this.scheduled = scheduled;
	}

	private Integer id;
	private String name;
	private String indication_name;
	private Double dose;
	private Integer dose_number;
	private String dosing_unit;
	private String route;
	private boolean scheduled;


	public boolean getScheduled() {
		return scheduled;
	}

	public void setScheduled(boolean scheduled) {
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

	public Double getDose() {
		return dose;
	}

	public void setDose(Double dose) {
		this.dose = dose;
	}

	public Integer getDose_number() {
		return dose_number;
	}

	public void setDose_number(Integer dose_number) {
		this.dose_number = dose_number;
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