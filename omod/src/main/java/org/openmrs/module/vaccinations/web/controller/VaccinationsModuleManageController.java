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
package org.openmrs.module.vaccinations.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.vaccinations.Vaccine;
import org.openmrs.module.vaccinations.api.VaccinationsService;
import org.openmrs.module.vaccinations.api.VaccinesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The main controller.
 */
@Controller
public class  VaccinationsModuleManageController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@RequestMapping(value = "/module/vaccinations/manage", method = RequestMethod.GET)
	public void manage(ModelMap model) {
		model.addAttribute("user", Context.getAuthenticatedUser());

		//model.put("vaccines", Context.getService(VaccinesService.class).getAllVaccines(false));
		//model.put("vaccinations", Context.getService(VaccinationsService.class).combineVaccinesAndVaccinationsByPatientId(3));
	}

    @RequestMapping(value = "/module/vaccinations/vaccinationsPatientSelector", method = RequestMethod.GET)
    public void vitalsPatientSelector(ModelMap model) {

    }
}
