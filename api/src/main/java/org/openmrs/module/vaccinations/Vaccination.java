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
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.vaccinations.api.VaccinationsService;

import java.util.Date;

/**
 * It is a model class. It should extend either {@link BaseOpenmrsObject} or {@link BaseOpenmrsMetadata}.
 */
public class Vaccination extends BaseOpenmrsObject implements Serializable {

	//private static final long serialVersionUID = 1L;


	public Vaccination() {
	}

	public Vaccination(Date scheduled_date, String name, String indication_name, Double dose, String dosing_unit, String route, boolean scheduled, Vaccine vaccine, boolean administered, Date administration_date, String body_site_administered, Integer dose_number, boolean adverse_reaction_observed, int patient_id) {
		this.scheduled_date = scheduled_date;
		this.name = name;
		this.indication_name = indication_name;
		this.dose = dose;
		this.dosing_unit = dosing_unit;
		this.route = route;
		this.scheduled = scheduled;
		this.vaccine = vaccine;
		this.administered = administered;
		this.administration_date = administration_date;
		this.body_site_administered = body_site_administered;
		this.dose_number = dose_number;
		this.adverse_reaction_observed = adverse_reaction_observed;
		this.patient_id = patient_id;
	}

	public Vaccination(SimpleVaccination simpleVaccination){
		if (!(null == simpleVaccination)) {
			this.id = simpleVaccination.getId();
			this.scheduled_date = simpleVaccination.getScheduled_date();
			this.name = simpleVaccination.getName();
			this.indication_name = simpleVaccination.getIndication_name();
			this.dose = simpleVaccination.getDose();
			this.dosing_unit = simpleVaccination.getDosing_unit();
			this.route = simpleVaccination.getRoute();
			this.scheduled = simpleVaccination.getScheduled();
			this.scheduled_date = simpleVaccination.getScheduled_date();
			this.vaccine = new Vaccine(simpleVaccination.getSimpleVaccine());
			this.adverse_reaction = new AdverseReaction(simpleVaccination.getSimpleAdverse_reaction());
			this.administered = simpleVaccination.getAdministered();
			this.administration_date = simpleVaccination.getAdministration_date();
			this.body_site_administered = simpleVaccination.getBody_site_administered();
			this.dose_number = simpleVaccination.getDose_number();
			this.lot_number = simpleVaccination.getLot_number();
			this.manufacturer = simpleVaccination.getManufacturer();
			this.manufacture_date = simpleVaccination.getManufacture_date();
			this.expiry_date = simpleVaccination.getExpiry_date();
			this.adverse_reaction_observed = simpleVaccination.getAdverse_reaction_observed();
			this.patient_id = simpleVaccination.getPatient_id();
		}

		//Database look up
		Vaccination oldVersion = Context.getService(VaccinationsService.class).getVaccinationByUuid(simpleVaccination.getUuid()); //lookup by UUID
		//
		if (oldVersion == null) {
			this.creator = Context.getAuthenticatedUser();
			this.dateCreated = new Date();

		}else{
			this.setCreator(oldVersion.getCreator());
			this.setDateCreated(oldVersion.dateCreated);
			this.changedBy = Context.getAuthenticatedUser();
			this.dateChanged = new Date();
			this.setUuid(oldVersion.getUuid());
		}
	}

	private Integer id;
    private Date scheduled_date;
	private String name;
	private String indication_name;
	private Double dose;
	private String dosing_unit;
	private String route;
    private boolean scheduled;

    private Vaccine vaccine;
    private AdverseReaction adverse_reaction;

	private boolean administered;
	private Date administration_date;
	private String body_site_administered;
    private Integer dose_number;
	private String lot_number;
	private String manufacturer;
	private Date manufacture_date;
	private Date expiry_date;
	private boolean adverse_reaction_observed;


	private User creator;
	private Date dateCreated;
	private User changedBy;
	private Date dateChanged;
	private boolean retired;
	private Date dateRetired;
	private User retiredBy;
	private String retireReason;

	private int patient_id;

	public int getPatient_id() {
		return patient_id;
	}

	public void setPatient_id(int patient_id) {
		this.patient_id = patient_id;
	}

	public boolean getScheduled() {
		return scheduled;
	}

	public void setScheduled(boolean scheduled) {
		this.scheduled = scheduled;
	}

	public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public User getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(User changedBy) {
        this.changedBy = changedBy;
    }

    public Date getDateChanged() {
        return dateChanged;
    }

    public void setDateChanged(Date dateChanged) {
        this.dateChanged = dateChanged;
    }

    public boolean getRetired() {
        return retired;
    }

    public void setRetired(boolean retired) {
        this.retired = retired;
    }

    public Date getDateRetired() {
        return dateRetired;
    }

    public void setDateRetired(Date dateRetired) {
        this.dateRetired = dateRetired;
    }

    public User getRetiredBy() {
        return retiredBy;
    }

    public void setRetiredBy(User retiredBy) {
        this.retiredBy = retiredBy;
    }

    public String getRetireReason() {
        return retireReason;
    }

    public void setRetireReason(String retireReason) {
        this.retireReason = retireReason;
    }

	public Vaccine getVaccine() {
		return vaccine;
	}

	public void setVaccine(Vaccine vaccine) {
		this.vaccine = vaccine;
	}

	public AdverseReaction getAdverse_reaction() {
		return adverse_reaction;
	}

	public void setAdverse_reaction(AdverseReaction adverse_reaction) {
		this.adverse_reaction = adverse_reaction;
	}


	public Date getScheduled_date() {
		return scheduled_date;
	}

	public void setScheduled_date(Date scheduled_date) {
		this.scheduled_date = scheduled_date;
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

	public boolean getAdministered() {
		return administered;
	}

	public void setAdministered(boolean administered) {
		this.administered = administered;
	}

	public Date getAdministration_date() {
		return administration_date;
	}

	public void setAdministration_date(Date administration_date) {
		this.administration_date = administration_date;
	}

	public String getBody_site_administered() {
		return body_site_administered;
	}

	public void setBody_site_administered(String body_site_administered) {
		this.body_site_administered = body_site_administered;
	}

	public Integer getDose_number() {
		return dose_number;
	}

	public void setDose_number(Integer dose_number) {
		this.dose_number = dose_number;
	}

	public String getLot_number() {
		return lot_number;
	}

	public void setLot_number(String lot_number) {
		this.lot_number = lot_number;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Date getManufacture_date() {
		return manufacture_date;
	}

	public void setManufacture_date(Date manufacture_date) {
		this.manufacture_date = manufacture_date;
	}

	public Date getExpiry_date() {
		return expiry_date;
	}

	public void setExpiry_date(Date expiry_date) {
		this.expiry_date = expiry_date;
	}

	public boolean getAdverse_reaction_observed() {
		return adverse_reaction_observed;
	}

	public void setAdverse_reaction_observed(boolean adverse_reaction_observed) {
		this.adverse_reaction_observed = adverse_reaction_observed;
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