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

import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.vaccinations.SimpleVaccination;
import org.openmrs.module.vaccinations.SimpleVaccine;
import org.openmrs.module.vaccinations.Vaccination;
import org.openmrs.module.vaccinations.api.VaccinationsService;
import org.openmrs.module.vaccinations.api.VaccinesService;
import org.openmrs.module.vaccinations.api.db.VaccinationsDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * It is a default implementation of {@link VaccinationsService}.
 */
public class VaccinationsServiceImpl extends BaseOpenmrsService implements VaccinationsService {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private VaccinationsDAO dao;
	
	/**
     * @param dao the dao to set
     */
    public void setDao(VaccinationsDAO dao) {
	    this.dao = dao;
    }
    
    /**
     * @return the dao
     */
    public VaccinationsDAO getDao() {
	    return dao;
    }

    @Override
    public List<Vaccination> listVaccinationsByPatientId(Integer patientId) throws APIException {
        return dao.getVaccinationsByPatientId(patientId);
    }

    @Override
    public List<SimpleVaccination> listSimpleVaccinationsByPatientId(Integer patientId) throws APIException {
        List<Vaccination> vaccinations = dao.getVaccinationsByPatientId(patientId);
        ArrayList<SimpleVaccination> simpleVaccinations = new ArrayList<SimpleVaccination>();
        for(Vaccination vaccination : vaccinations){
            simpleVaccinations.add(vaccinationToSimpleVaccination(vaccination));
        }
        return simpleVaccinations;
    }

    @Override
    public SimpleVaccination vaccinationToSimpleVaccination(Vaccination vaccination) throws APIException {

        SimpleVaccination simpleVaccination = new SimpleVaccination(vaccination.getId(),vaccination.getScheduled_date(),vaccination.getName(),vaccination.getIndication_name(),
                vaccination.getDose(),vaccination.getDosing_unit(),vaccination.getRoute(),vaccination.getScheduled(),
                Context.getService(VaccinesService.class).vaccineToSimpleVaccine(vaccination.getVaccine()),
                vaccination.getAdministered(), vaccination.getAdministration_date(),vaccination.getBody_site_administered(),vaccination.getDose_number(),vaccination.getLot_number(),
                vaccination.getManufacturer(),vaccination.getManufacture_date(),vaccination.getExpiry_date(),vaccination.getAdverse_reaction_observed(),vaccination.getPatient_id());
        return simpleVaccination;
    }


    @Override
    public Vaccination getVaccinationByVaccinationId(int vaccination_id) throws APIException {
        return null;
    }
}