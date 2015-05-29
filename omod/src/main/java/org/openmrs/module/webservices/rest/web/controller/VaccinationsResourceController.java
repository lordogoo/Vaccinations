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
import org.openmrs.module.vaccinations.Vaccination;
import org.openmrs.module.vaccinations.Vaccine;
import org.openmrs.module.vaccinations.api.VaccinesService;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.ModelMap;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceController; //To potentially remove
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.List;


@Controller
@RequestMapping("/rest/" + RestConstants.VERSION_2 + "/vaccinationsmodule")
public class VaccinationsResourceController {// extends MainResourceController {
	/*@Override
	public String getNamespace() {
		return  RestConstants.VERSION_2 + "/vaccinations/";
	}*/

	@RequestMapping(value = "/vaccines", method = RequestMethod.GET)
	@ResponseBody
	public Collection<Vaccine> getAllVaccines() {
		//model.put("vaccines", Context.getService(VaccinesService.class).getAllVaccines(false));
		return Context.getService(VaccinesService.class).getAllVaccines(false);
	}


	@RequestMapping(value = "/vaccinations", method = RequestMethod.POST)
	@ResponseBody
	public Vaccination saveVaccination(Vaccination vaccination) {
		return vaccination;
	}
}

