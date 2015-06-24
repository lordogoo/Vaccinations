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
        return simplifyVaccines(getAllVaccines(includeRetired));
    }

    @Override
    public List<SimpleVaccine> getScheduledVaccinesSimple(Boolean includeRetired) throws APIException {
        return simplifyVaccines(getScheduledVaccines(includeRetired));
    }

    @Override
    public List<SimpleVaccine> getUnscheduledVaccinesSimple(Boolean includeRetired) throws APIException {
        return simplifyVaccines(getUnscheduledVaccines(includeRetired));
    }

    @Override
    public List<Vaccine> getAllVaccines(Boolean includeRetired) throws APIException {
        return dao.listAllVaccines(includeRetired);
    }

    @Override
    public List<Vaccine> getScheduledVaccines(Boolean includeRetired) throws APIException {
        return dao.listScheduledVaccines(includeRetired);
    }

    @Override
    public List<Vaccine> getUnscheduledVaccines(Boolean includeRetired) throws APIException {
        return dao.listUnscheduledVaccines(includeRetired);
    }

    @Override
    public Vaccine saveOrUpdateVaccine(Vaccine vaccine) throws APIException {
        return dao.saveOrUpdateVaccine(vaccine);
    }

    @Override
    public Vaccine getVaccineByUuid(String uuid) throws APIException {
        return dao.getVaccineByUuid(uuid);
    }

    @Override
    public List<SimpleVaccine> simplifyVaccines(List<Vaccine> vaccines) throws APIException{
        ArrayList<SimpleVaccine> simpleVaccines = new ArrayList<SimpleVaccine>();
        for(Vaccine vaccine : vaccines){
            simpleVaccines.add(new SimpleVaccine(vaccine));
        }
        return simpleVaccines;
    }

    @Override
    public List<Vaccine> complicateSimpleVaccines(List<SimpleVaccine> simpleVaccines) throws Exception {
        ArrayList<Vaccine> vaccines = new ArrayList<Vaccine>();
        for(SimpleVaccine simpleVaccine : simpleVaccines){
            try {
                vaccines.add(new Vaccine(simpleVaccine));
            }catch (Exception ex){
                throw ex;
            }
        }
        return vaccines;
    }
}