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
import java.util.List;


@Controller
@RequestMapping("/rest/" + RestConstants.VERSION_2 + "/vaccinationsmodule")
public class VaccinationsResourceController {// extends MainResourceController {
	/*@Override
	public String getNamespace() {
		return  RestConstants.VERSION_2 + "/vaccinations/";
	}*/

	@RequestMapping(value = "/vaccines/unscheduled", method = RequestMethod.GET)
	@ResponseBody
	public List<SimpleVaccine> getUnscheduledVaccinesSimple() {
		return Context.getService(VaccinesService.class).getUnscheduledVaccinesSimple(false);
	}

	@RequestMapping(value = "/vaccinations/patients", params="patientId", method = RequestMethod.GET)
	@ResponseBody
	public List<SimpleVaccination> getVaccinations(@RequestParam int patientId) {
		return Context.getService(VaccinationsService.class).listSimpleVaccinationsByPatientId(patientId);
	}

	@RequestMapping(value = "/vaccinations", method = RequestMethod.POST)
	@ResponseBody
	public SimpleVaccination saveVaccination(Vaccination vaccination) {
		return new SimpleVaccination(Context.getService(VaccinationsService.class).saveOrUpdateVaccination(vaccination));
	}

	@RequestMapping(value = "/vaccinations", method = RequestMethod.PUT)
	@ResponseBody
	public SimpleVaccination updateVaccination(Vaccination vaccination) {
		return new SimpleVaccination(Context.getService(VaccinationsService.class).saveOrUpdateVaccination(vaccination));
	}

	@RequestMapping(value = "/vaccinations/{vaccinationId}", method = RequestMethod.DELETE)
	@ResponseBody
	public HttpStatus deleteVaccination(@RequestParam int vaccinationId) {
		Vaccination vaccination1 = Context.getService(VaccinationsService.class).getVaccinationByVaccinationId(vaccinationId);
		SimpleVaccination simpleVaccination2 = new SimpleVaccination(Context.getService(VaccinationsService.class).saveOrUpdateVaccination(vaccination1));
		return HttpStatus.OK;
	}

	@RequestMapping(value = "/adverseReactions", method = RequestMethod.POST)
	@ResponseBody
	public Vaccination saveAdverseReaction(AdverseReaction adverseReaction) {
		Vaccination vaccination = new Vaccination();
		return vaccination;
	}

	@RequestMapping(value = "/adverseReactions", method = RequestMethod.PUT)
	@ResponseBody
	public Vaccination updateAdverseReaction(AdverseReaction adverseReaction) {
		Vaccination vaccination = new Vaccination();
		return vaccination;
	}

	@RequestMapping(value = "/adverseReactions/{adverseReactionId}", method = RequestMethod.DELETE)
	@ResponseBody
	public HttpStatus deleteAdverseReaction(@RequestParam int adverseReactionId) {
		Vaccination vaccination = new Vaccination();
		return HttpStatus.OK;
	}
}

