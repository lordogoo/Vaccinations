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
package org.openmrs.module.vaccinations.api;

import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.vaccinations.SimpleVaccine;
import org.openmrs.module.vaccinations.Vaccine;
import org.openmrs.module.vaccinations.util.PrivilegeConstants;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * This service exposes module's core functionality. It is a Spring managed bean which is configured in moduleApplicationContext.xml.
 * <p>
 * It can be accessed only via Context:<br>
 * <code>
 * Context.getService(VaccinationsService.class).someMethod();
 * </code>
 * 
 * @see org.openmrs.api.context.Context
 */
@Transactional
public interface VaccinesService extends OpenmrsService {

	public List<SimpleVaccine> simplifyVaccines(List<Vaccine> vaccines) throws APIException;

	public List<Vaccine> complicateSimpleVaccines(List<SimpleVaccine> simpleVaccines) throws Exception;

	@Transactional(readOnly =  true)
	@Authorized( {PrivilegeConstants.VIEW_VACCINES} )
	public List<SimpleVaccine> getAllVaccinesSimple(Boolean includeRetired) throws APIException;

	@Transactional(readOnly =  true)
	@Authorized( {PrivilegeConstants.VIEW_VACCINES} )
	public List<SimpleVaccine> getScheduledVaccinesSimple(Boolean includeRetired) throws APIException;

	@Transactional(readOnly =  true)
	@Authorized( {PrivilegeConstants.VIEW_VACCINES} )
	public List<SimpleVaccine> getUnscheduledVaccinesSimple(Boolean includeRetired) throws APIException;

	@Transactional(readOnly =  true)
	@Authorized( {PrivilegeConstants.VIEW_VACCINES} )
	public List<Vaccine> getAllVaccines(Boolean includeRetired) throws APIException;

	@Transactional(readOnly =  true)
	@Authorized( {PrivilegeConstants.VIEW_VACCINES} )
	public List<Vaccine> getScheduledVaccines(Boolean includeRetired) throws APIException;

	@Transactional(readOnly =  true)
	@Authorized( {PrivilegeConstants.VIEW_VACCINES} )
	public List<Vaccine> getUnscheduledVaccines(Boolean includeRetired) throws APIException;

	@Transactional(readOnly =  true)
	@Authorized( {PrivilegeConstants.VIEW_VACCINES} )
	public Vaccine getVaccineByUuid(String uuid) throws APIException;

	@Transactional(readOnly =  true)
	@Authorized( {PrivilegeConstants.MANAGE_VACCINES} )
	public Vaccine saveOrUpdateVaccine(Vaccine vaccine) throws APIException;

}