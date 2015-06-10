/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 2.0 (the "License"); you may not use this file except in
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
package org.openmrs.module.webservices.rest.web.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.vaccinations.*;
import org.openmrs.module.vaccinations.api.AdverseReactionsService;
import org.openmrs.module.vaccinations.api.VaccinationsService;
import org.openmrs.module.vaccinations.api.VaccinesService;
import org.openmrs.module.webservices.rest.web.RestConstants;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.ModelMap;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceController; //To potentially remove
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Date;
import java.util.List;

//For debugging
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


@Controller
@RequestMapping("/rest/" + RestConstants.VERSION_2 + "/vaccinationsmodule")
public class VaccinationsResourceController {// extends MainResourceController {

    //private AdverseReactionsService adverseReactionsService = Context.getService(AdverseReactionsService.class);
    //private VaccinationsService vaccinationsService = Context.getService(VaccinationsService.class);
    //private VaccinesService vaccinesService = Context.getService(VaccinesService.class);
    protected final Log log = LogFactory.getLog(this.getClass());
	/*@Override
	public String getNamespace() {
		return  RestConstants.VERSION_2 + "/vaccinations/";
	}*/

	@RequestMapping(value = "/vaccines/unscheduled", method = RequestMethod.GET)
	@ResponseBody
	public List<SimpleVaccine> getUnscheduledVaccinesSimple() {
		return Context.getService(VaccinesService.class).getUnscheduledVaccinesSimple(false);
	}

	@RequestMapping(value = "/vaccines/template", method = RequestMethod.POST)
	@ResponseBody
	public SimpleVaccination generateSimpleVaccinationTemplate(@RequestBody SimpleVaccine simpleVaccine) {
		SimpleVaccination simpleVaccination = new SimpleVaccination();
		simpleVaccination.setSimpleVaccine(simpleVaccine);
		return simpleVaccination;
	}

	@RequestMapping(value = "/vaccinations/patient/{patientId}", method = RequestMethod.GET)
	@ResponseBody
	public List<SimpleVaccination> getVaccinations(@PathVariable int patientId) {
		return Context.getService(VaccinationsService.class).combineVaccinesAndVaccinationsByPatientIdSimple(patientId);
	}

	@RequestMapping(value = "/vaccinations/patient/{patientId}", method = RequestMethod.POST)
	@ResponseBody
	public SimpleVaccination saveVaccination(@RequestBody SimpleVaccination simpleVaccination, @PathVariable int patientId) throws Exception {
		try {
			simpleVaccination.setPatient_id(patientId);
            return new SimpleVaccination(Context.getService(VaccinationsService.class).saveOrUpdateVaccination(new Vaccination(simpleVaccination)));
		}catch (Exception ex){
			throw ex;
		}
	}

	@RequestMapping(value = "/vaccinations/{vaccinationId}/patient/{patientId}", method = RequestMethod.PUT)
	@ResponseBody
	public SimpleVaccination updateVaccination(
			@RequestBody SimpleVaccination simpleVaccination, @PathVariable int vaccinationId, @PathVariable int patientId) throws Exception {
		try
		{
			simpleVaccination.setPatient_id(patientId);

			//If an object already exists in the session, save using that object
			Vaccination oldVaccination = Context.getService(VaccinationsService.class).getVaccinationByVaccinationId(vaccinationId);
			if (oldVaccination != null) {
				oldVaccination = new Vaccination(simpleVaccination);
				return new SimpleVaccination(Context.getService(VaccinationsService.class).saveOrUpdateVaccination(oldVaccination));
            } else {
                return new SimpleVaccination(Context.getService(VaccinationsService.class).saveOrUpdateVaccination(new Vaccination(simpleVaccination)));
			}
		}catch (Exception ex){
			throw ex;
		}
	}

	@RequestMapping(value = "/vaccinations/{vaccinationId}/patient/{patientId}", method = RequestMethod.DELETE)
	@ResponseBody
	public SimpleVaccination deleteVaccination(@PathVariable int vaccinationId, @PathVariable int patientId) {

		Vaccination vaccination1 = Context.getService(VaccinationsService.class).getVaccinationByVaccinationId(vaccinationId);
		vaccination1.setRetired(true);
		vaccination1.setRetiredBy(Context.getAuthenticatedUser());
		vaccination1.setDateRetired(new Date());
		vaccination1.setRetireReason("Deleted");
		SimpleVaccination simpleVaccination2 = new SimpleVaccination(Context.getService(VaccinationsService.class).saveOrUpdateVaccination(vaccination1));

		//check if vaccination is based on a scheduled vaccine
		if (vaccination1.getScheduled()){
			//returns a new vaccination template
			simpleVaccination2 = new SimpleVaccination(Context.getService(VaccinationsService.class).vaccineToVaccination(vaccination1.getVaccine(), Context.getService(VaccinationsService.class).calculateScheduledDate(patientId,vaccination1.getVaccine())));
			//simpleVaccination2.setId(vaccinationId); //id has to be null since
			//simpleVaccination2.setPatient_id(patientId);
			//return simpleVaccination2;
		}else{
			//returns a retired vaccination body
		}
		return simpleVaccination2;
	}

	@RequestMapping(value = "/adversereactions/patient/{patientId}/vaccinations/{vaccinationId}", method = RequestMethod.POST)
	@ResponseBody
	public SimpleVaccination saveAdverseReaction(
			@RequestBody SimpleAdverseReaction simpleAdverseReaction, @PathVariable int patientId, @PathVariable int vaccinationId) {
		simpleAdverseReaction.setVaccination_id(vaccinationId);
		simpleAdverseReaction = new SimpleAdverseReaction(Context.getService(AdverseReactionsService.class).saveOrUpdateAdverseReaction(new AdverseReaction(simpleAdverseReaction)));
		return new SimpleVaccination(Context.getService(VaccinationsService.class).getVaccinationByVaccinationId(simpleAdverseReaction.getVaccination_id()));
    }

    @RequestMapping(value = "/adversereactions/{adverseReactionId}/patient/{patientId}", method = RequestMethod.PUT)
	@ResponseBody
	public SimpleVaccination updateAdverseReaction(
			@RequestBody SimpleAdverseReaction simpleAdverseReaction, @PathVariable int adverseReactionId, @PathVariable int patientId) {
		simpleAdverseReaction = new SimpleAdverseReaction(Context.getService(AdverseReactionsService.class).saveOrUpdateAdverseReaction(new AdverseReaction(simpleAdverseReaction)));
        return new SimpleVaccination(Context.getService(VaccinationsService.class).getVaccinationByVaccinationId(simpleAdverseReaction.getVaccination_id()));
	}

	@RequestMapping(value = "/adversereactions/{adverseReactionId}/patient/{patientId}", method = RequestMethod.DELETE)
	@ResponseBody
	public HttpStatus deleteAdverseReaction(@PathVariable int adverseReactionId, @PathVariable int patientId) {
		//Lookup adverse reaction
		AdverseReaction adverseReaction = Context.getService(AdverseReactionsService.class).getAdverseReactionByAdverseReactionId(adverseReactionId);

		//Lookup vaccination by id from adverse reaction
		Vaccination vaccination1 = Context.getService(VaccinationsService.class).getVaccinationByVaccinationId(adverseReaction.getVaccination_id());

		//set adverse reaction retired to true and retirer to authenticated user
		adverseReaction.setRetired(true);
		adverseReaction.setRetiredBy(Context.getAuthenticatedUser());
		adverseReaction.setDateRetired(new Date());
		adverseReaction.setRetireReason("Deleted");
		//set vaccination's adverse reaction to the adjusted adverse reaction.
		vaccination1.setAdverse_reaction(adverseReaction);
		//save or update vaccination
        Context.getService(VaccinationsService.class).saveOrUpdateVaccination(vaccination1);
		return HttpStatus.OK;
	}
}

