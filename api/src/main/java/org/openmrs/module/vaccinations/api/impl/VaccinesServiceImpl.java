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
package org.openmrs.module.vaccinations.api.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.vaccinations.Vaccination;
import org.openmrs.module.vaccinations.Vaccine;
import org.openmrs.module.vaccinations.SimpleVaccine;
import org.openmrs.module.vaccinations.api.VaccinesService;
import org.openmrs.module.vaccinations.api.db.VaccinesDAO;
import sun.java2d.pipe.SpanShapeRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * It is a default implementation of {@link VaccinesService}.
 */
public class VaccinesServiceImpl extends BaseOpenmrsService implements VaccinesService {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private VaccinesDAO dao;
	
	/**
     * @param dao the dao to set
     */
    public void setDao(VaccinesDAO dao) {
	    this.dao = dao;
    }
    
    /**
     * @return the dao
     */
    public VaccinesDAO getDao() {
	    return dao;
    }

    @Override
    public List<SimpleVaccine> getAllVaccinesSimple(Boolean includeRetired) throws APIException {
        List<Vaccine> vaccines = dao.getAllVaccines(includeRetired);
        ArrayList<SimpleVaccine> simpleVaccines = new ArrayList<SimpleVaccine>();
        for(Vaccine vaccine : vaccines){
            simpleVaccines.add(vaccineToSimpleVaccine(vaccine));
        }
        return simpleVaccines;
    }

    @Override
    public List<Vaccine> getAllVaccines(Boolean includeRetired) throws APIException {
        return dao.getAllVaccines(includeRetired);
    }

    @Override
    public Vaccine getVaccineByUuid(String Uuid) throws APIException {
        return new Vaccine();
    }

    @Override
    public SimpleVaccine vaccineToSimpleVaccine(Vaccine vaccine) throws APIException {
        SimpleVaccine simpleVaccine = new SimpleVaccine(vaccine.getId(), vaccine.getName(), vaccine.getIndication_name(), vaccine.getDose(), vaccine.getDose_number(), vaccine.getDosing_unit(), vaccine.getRoute(), vaccine.getScheduled());
        return simpleVaccine;
    }

}