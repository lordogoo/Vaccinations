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

import java.util.Date;

/**
 * It is a model class. It should extend either {@link BaseOpenmrsObject} or {@link BaseOpenmrsMetadata}.
 */
public class Vaccination extends BaseOpenmrsObject implements Serializable {

	//private static final long serialVersionUID = 1L;

    public Vaccination() {
    }

    public Vaccination(Integer id, Date scheduled_date, String name, String indication_name, double dose, String dosing_unit, String route, Boolean scheduled, Vaccine vaccine, AdverseReaction adverse_reaction, Boolean administered, Date administered_date, String body_site_administered, int dose_number, String lot_number, String manufacturer, Date manufacture_date, Date expiry_date, Boolean adverse_reaction_observed, User creator, Date dateCreated, User changedBy, Date dateChanged, Boolean retired, Date dateRetired, User retiredBy, String retireReason) {
        this.id = id;
        this.scheduled_date = scheduled_date;
        this.name = name;
        this.indication_name = indication_name;
        this.dose = dose;
        this.dosing_unit = dosing_unit;
        this.route = route;
        this.scheduled = scheduled;
        this.vaccine = vaccine;
        this.adverse_reaction = adverse_reaction;
        this.administered = administered;
        this.administered_date = administered_date;
        this.body_site_administered = body_site_administered;
        this.dose_number = dose_number;
        this.lot_number = lot_number;
        this.manufacturer = manufacturer;
        this.manufacture_date = manufacture_date;
        this.expiry_date = expiry_date;
        this.adverse_reaction_observed = adverse_reaction_observed;
        this.creator = creator;
        this.dateCreated = dateCreated;
        this.changedBy = changedBy;
        this.dateChanged = dateChanged;
        this.retired = retired;
        this.dateRetired = dateRetired;
        this.retiredBy = retiredBy;
        this.retireReason = retireReason;
    }

    private Integer id;
    private Date scheduled_date;
	private String name;
	private String indication_name;
	private double dose;
	private String dosing_unit;
	private String route;
    private Boolean scheduled;

    private Vaccine vaccine;
    private AdverseReaction adverse_reaction;

	private Boolean administered;
	private Date administered_date;
	private String body_site_administered;
    private int dose_number;
	private String lot_number;
	private String manufacturer;
	private Date manufacture_date;
	private Date expiry_date;
	private Boolean adverse_reaction_observed;


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

    public Boolean getRetired() {
        return retired;
    }

    public void setRetired(Boolean retired) {
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

    private User creator;
    private Date dateCreated;
    private User changedBy;
    private Date dateChanged;
    private Boolean retired;
    private Date dateRetired;
    private User retiredBy;
    private String retireReason;

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


	public Boolean getScheduled() {
		return scheduled;
	}

	public void setScheduled(Boolean scheduled) {
		this.scheduled = scheduled;
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

	public Boolean getAdministered() {
		return administered;
	}

	public void setAdministered(Boolean administered) {
		this.administered = administered;
	}

	public Date getAdministered_date() {
		return administered_date;
	}

	public void setAdministered_date(Date administered_date) {
		this.administered_date = administered_date;
	}

	public String getBody_site_administered() {
		return body_site_administered;
	}

	public void setBody_site_administered(String body_site_administered) {
		this.body_site_administered = body_site_administered;
	}

	public int getDose_number() {
		return dose_number;
	}

	public void setDose_number(int dose_number) {
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

	public Boolean getAdverse_reaction_observed() {
		return adverse_reaction_observed;
	}

	public void setAdverse_reaction_observed(Boolean adverse_reaction_observed) {
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