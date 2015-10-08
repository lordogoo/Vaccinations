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
import java.util.Date;

import net.sf.saxon.pattern.CombinedNodeTest;
import org.openmrs.BaseOpenmrsObject;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.vaccinations.api.VaccinationsService;
import org.openmrs.module.vaccinations.api.VaccinesService;

/**
 * It is a model class. It should extend {@link BaseOpenmrsObject}.
 */
public class Vaccine extends BaseOpenmrsObject implements Serializable {

	//private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String indication_name;
	private Double dose;
	private Integer dose_number;
	private String dosing_unit;
	private String route;
	private boolean scheduled;
	private Integer numeric_indication;

    private Integer min_age;
    private Integer max_age;

    private String body_site_administered;
    private Boolean side_administered_left;

	private User creator;
	private Date dateCreated;
	private User changedBy;
	private Date dateChanged;
	private boolean retired;
	private Date dateRetired;
	private User retiredBy;
	private String retireReason;

    public Vaccine() {
    }


    public Vaccine (SimpleVaccine simpleVaccine){
        //Since Vaccine should never be altered, we only do lookup by Uuid and then assign all the data to the new Vaccine
        if (simpleVaccine != null) {
            if (simpleVaccine.getUuid() != null) {
                //Database look up
                Vaccine oldVersion = Context.getService(VaccinesService.class).getVaccineByUuid(simpleVaccine.getUuid()); //lookup by UUID
                //
                this.id = oldVersion.getId();
				/*this.name = oldVersion.getName();
				this.indication_name = oldVersion.getIndication_name();
				this.dose = oldVersion.getDose();
				this.dose_number = oldVersion.getDose_number();
				this.dosing_unit = oldVersion.getDosing_unit();
				this.route = oldVersion.getRoute();
				this.scheduled = oldVersion.getScheduled();
				this.numeric_indication = oldVersion.getNumeric_indication();*/
                this.setCreator(oldVersion.getCreator());
                this.setDateCreated(oldVersion.dateCreated);
				/*this.changedBy = oldVersion.getChangedBy();
				this.dateChanged = oldVersion.getDateChanged();
				this.retired = oldVersion.getRetired();
				this.dateRetired = oldVersion.getDateRetired();
				this.retiredBy = oldVersion.getRetiredBy();
				this.retireReason = oldVersion.getRetireReason();*/
                this.setUuid(oldVersion.getUuid());
            }else{
                //Do nothing, vaccination is lost
            }
        }
    }

    public Integer getMin_age() {
        return min_age;
    }

    public void setMin_age(Integer min_age) {
        this.min_age = min_age;
    }

    public Integer getMax_age() {
        return max_age;
    }

    public void setMax_age(Integer max_age) {
        this.max_age = max_age;
    }

    public String getBody_site_administered() {
        return body_site_administered;
    }

    public void setBody_site_administered(String body_site_administered) {
        this.body_site_administered = body_site_administered;
    }

    public Boolean getSide_administered_left() {
        return side_administered_left;
    }

    public void setSide_administered_left(Boolean side_administered_left) {
        this.side_administered_left = side_administered_left;
    }

	public Integer getNumeric_indication() {
		return numeric_indication;
	}

	public void setNumeric_indication(Integer numeric_indication) {
		this.numeric_indication = numeric_indication;
	}

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


}