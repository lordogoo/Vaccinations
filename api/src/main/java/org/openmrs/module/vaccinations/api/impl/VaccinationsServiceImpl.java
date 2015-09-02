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

import org.hibernate.SessionFactory;
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


import java.util.*;

/**
 * It is a default implementation of {@link VaccinationsService}.
 */
public class VaccinationsServiceImpl extends BaseOpenmrsService implements VaccinationsService {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	private SessionFactory sessionFactory;
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
        return dao.listVaccinationsByPatientId(patientId);
    }

    @Override
    public List<SimpleVaccination> listSimpleVaccinationsByPatientId(Integer patientId) throws APIException {
        return simplifyVaccinations(listVaccinationsByPatientId(patientId));
    }

    //WILL ONLY RETURN UNADMINISTERED VACCINATIONS
    @Override
    public List<Vaccination> combineVaccinesAndVaccinationsByPatientId(Integer patientId) throws APIException {
        //Retrieving all scheduled vaccines
        VaccinesService vaccinesService = Context.getService(VaccinesService.class);
        List<Vaccine> vaccines = vaccinesService.getScheduledVaccines(false);
        if (vaccines != null && !vaccines.isEmpty()) {
            //Retrieving all vaccinations by patientId
            List<Vaccination> vaccinations = listVaccinationsByPatientId(patientId);
            Date calculatedScheduledDate = new Date();
            List<Vaccination> completeVaccinations = new ArrayList<Vaccination>();
            if (vaccinations != null && !vaccinations.isEmpty()) {
                //Combining scheduled vaccines and performed vaccinations
                for (Vaccine vaccine : vaccines) {
                    //Check if a scheduled vaccine has already been administered
                    Boolean found = false;
                    for (Vaccination vaccination : vaccinations) {
                        if (vaccine.getUuid() == vaccination.getVaccine().getUuid()) {
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        calculatedScheduledDate = calculateScheduledDate(patientId, vaccine);
                        //Vaccine to Vaccination
                        completeVaccinations.add(vaccineToVaccination(vaccine, calculatedScheduledDate));
                    }
                }
                completeVaccinations.addAll(vaccinations);

            } else {
                try {
                    for (Vaccine vaccine : vaccines) {
                        calculatedScheduledDate = calculateScheduledDate(patientId, vaccine);
                        //Vaccine to Vaccination
                        Vaccination newVaccination = vaccineToVaccination(vaccine, calculatedScheduledDate);
                        completeVaccinations.add(newVaccination);
                    }
                }catch (Exception ex){
                    log.error(ex.getMessage() + " STACKTRACE: " + ex.getStackTrace() + " BDAY: " + calculatedScheduledDate.toString() + " VaccinationName: " + completeVaccinations.get(completeVaccinations.size()-1).getName());
                    return null;
                }
            }
            return completeVaccinations;
        }else{
            throw new APIException("The vaccines list is empty!");
        }
    }

    @Override
    public Date calculateScheduledDate(Integer patientId, Vaccine vaccine) throws APIException{
        Calendar cal = Calendar.getInstance();
        Date patientBDay = new Date();
        //Get patient birthday using patient service
        if (Context.getPatientService().getPatient(patientId).getBirthdate() != null) {
            patientBDay = Context.getPatientService().getPatient(patientId).getBirthdate();
        } else {
            //Once a decision is made regarding whether to show vaccines as overdue or not
            //This code will contain an algorithm to handle that behavior
        }

        //Calculate scheduled date from birthday and numeric indication.
        int numericIndication = 0;
        if (vaccine.getNumeric_indication() != null)
        {
            numericIndication = vaccine.getNumeric_indication();
        }
        cal.setTime(patientBDay);
        cal.add(Calendar.DATE, numericIndication);
        Date calculatedScheduledDate = cal.getTime();
        return calculatedScheduledDate;
    }

    @Override
    public Vaccination vaccineToVaccination(Vaccine vaccine, Date calculatedScheduledDate) throws APIException {
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

        if (vaccine.getSide_administered_left() != null){
            newVaccination.setSide_administered_left(vaccine.getSide_administered_left());
        }
        newVaccination.setBody_site_administered(vaccine.getBody_site_administered());

        //Assign calculated scheduled date
        newVaccination.setScheduled_date(calculatedScheduledDate);

        newVaccination.setAdministered(false);
        newVaccination.setAdverse_reaction_observed(false);
        newVaccination.setSide_administered_left(false);
        return newVaccination;
    }



    @Override
    public List<SimpleVaccination> combineVaccinesAndVaccinationsByPatientIdSimple(Integer patientId) throws APIException {
        return simplifyVaccinations(combineVaccinesAndVaccinationsByPatientId(patientId));
    }

    @Override
    public Vaccination getVaccinationByUuid(String uuid) throws APIException {
        return dao.getVaccinationByUuId(uuid);
    }

    @Override
    public Vaccination saveOrUpdateVaccination(Vaccination vaccination) throws APIException {
        return dao.saveOrUpdateVaccination(vaccination);
    }

    @Override
    public Vaccination getVaccinationByVaccinationId(int vaccination_id) throws APIException {
        return dao.getVaccinationByVaccinationId(vaccination_id);
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