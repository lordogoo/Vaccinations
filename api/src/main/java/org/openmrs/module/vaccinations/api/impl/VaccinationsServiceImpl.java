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
import org.openmrs.module.vaccinations.Vaccine;
import org.openmrs.module.vaccinations.api.VaccinationsService;
import org.openmrs.module.vaccinations.api.VaccinesService;
import org.openmrs.module.vaccinations.api.db.VaccinationsDAO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        return simplifyVaccinations(listVaccinationsByPatientId(patientId));
    }

    @Override
    public List<Vaccination> combineVaccinesAndVaccinationsByPatientId(Integer patientId) throws APIException {
        //Retrieving all scheduled vaccines
        VaccinesService vaccinesService = Context.getService(VaccinesService.class);
        List<Vaccine> vaccines = vaccinesService.getScheduledVaccines(false);

        //Retrieving all vaccinations by patientId
        List<Vaccination> vaccinations = listVaccinationsByPatientId(patientId);

        //Combining scheduled vaccines and performed vaccinations
        for (Vaccine vaccine : vaccines){
            //Check if a scheduled vaccine has already been administered
            Boolean found = false;
            for (Vaccination vaccination : vaccinations){
                if (vaccine.getUuid() == vaccination.getVaccine().getUuid()){
                    found = true;
                    break;
                }
            }

            if (!found){
                //Get patient birthday using patient service
                Date patientBDay = Context.getPatientService().getPatient(patientId).getBirthdate();

                //Calculate scheduled date from birthday and numeric indication.
                Calendar cal = Calendar.getInstance();
                cal.setTime(patientBDay);
                cal.add(Calendar.DATE, vaccine.getNumeric_indication());
                Date calculatedScheduledDate = cal.getTime();

                //create a new vaccination using vaccine as a template
                Vaccination newVaccination = new Vaccination();
                newVaccination.setVaccine(vaccine);

                newVaccination.setName(vaccine.getName());
                newVaccination.setIndication_name(vaccine.getIndication_name());
                newVaccination.setDose(vaccine.getDose());
                newVaccination.setDosing_unit(vaccine.getDosing_unit());
                newVaccination.setRoute(vaccine.getRoute());
                newVaccination.setScheduled(vaccine.getScheduled());
                newVaccination.setDose_number(vaccine.getDose_number());
                //Assign calculated scheduled date
                newVaccination.setScheduled_date(calculatedScheduledDate);

                newVaccination.setAdministered(false);
                newVaccination.setAdverse_reaction_observed(false);

                vaccinations.add(newVaccination);
            }
        }
        return vaccinations;
    }

    @Override
    public List<SimpleVaccination> combineVaccinesAndVaccinationsByPatientIdSimple(Integer patientId) throws APIException {
        return null;
    }

    @Override
    public Vaccination saveOrUpdateVaccination(Vaccination vaccination) throws APIException {
        return dao.saveOrUpdateVaccination(vaccination);
    }

    @Override
    public Vaccination getVaccinationByVaccinationId(int vaccination_id) throws APIException {
        return null;
    }

    @Override
    public List<SimpleVaccination> simplifyVaccinations(List<Vaccination> vaccinations) throws APIException {
        ArrayList<SimpleVaccination> simpleVaccinations = new ArrayList<SimpleVaccination>();
        for(Vaccination vaccination : vaccinations){
            simpleVaccinations.add(new SimpleVaccination(vaccination));
        }
        return simpleVaccinations;
    }
}