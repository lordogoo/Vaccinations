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

import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.vaccinations.*;
import org.openmrs.module.vaccinations.enums.Excuses;
import org.openmrs.test.BaseModuleContextSensitiveTest;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests {@link {VaccinationsService}}.
 */
public class UtilsServiceTest extends BaseModuleContextSensitiveTest {

    private UtilsService utilsService;
    private VaccinationsService vaccinationsService;

    @Before
    public void before() throws Exception {
        initializeInMemoryDatabase();
        executeDataSet("src/test/resources/VaccinationsTestData.xml");
        utilsService = Context.getService(UtilsService.class);
        vaccinationsService = Context.getService(VaccinationsService.class);
        Context.getUserService().setUserProperty(Context.getAuthenticatedUser(), "defaultLocation", "1");
        Context.getUserService().getUser(1).setName("Jonny");
    }

	@Test
	public void shouldSetupContext() throws Exception{
        assertNotNull(utilsService);
        assertNotNull(vaccinationsService);
	}

    @Test
    public void shouldReturnManufacturers(){
        logger.debug("Getting all manufacturers");
        List<Manufacturer> manufacturers = utilsService.getAllManufacturers(true);
        assertEquals(2, manufacturers.size());
    }

    @Test
    public void shouldReturnAuditLog(){
        logger.debug("Getting all audit logs");
        List<AuditLog> auditLogList = utilsService.getAuditLogByVaccinationId(1);
        assertEquals(2, auditLogList.size());
        assertEquals(3, auditLogList.get(0).getAuditLogLineItemList().size());
        assertEquals(Excuses.NoExcuse.toString(), auditLogList.get(0).getExcuse());
    }

    @Test
    public void shouldReturnEmptyAuditLog(){
        logger.debug("Getting all audit logs");
        List<AuditLog> auditLogList = utilsService.getAuditLogByVaccinationId(2);
        assertEquals(0, auditLogList.size());
        //assertEquals(3, auditLogList.get(0).getAuditLogLineItemList().size());
        //assertEquals(Excuses.NoExcuse.toString(), auditLogList.get(0).getExcuse());
    }

    @Test
    public void shouldCreateNewAuditLogRecordAndLineItems(){
        //Acquiring an unscheduled vaccination
        Vaccine vaccine = Context.getService(VaccinesService.class).getVaccineByUuid("d5f0f111-0a36-11e5-ba5b-005056be863d");
        Vaccination vaccination = vaccinationsService.vaccineToVaccination(vaccine, new Date());

        vaccination.setPatient_id(3);
        vaccination.setDose(500.00);
        vaccination.setAdministered(true);
        vaccination.setScheduled_date(new Date());
        vaccination.setScheduled(true);
        vaccination.setAdministration_date(new Date());
        vaccination.setBody_site_administered("Nah");
        vaccination.setSide_administered_left(true);
        vaccination.setAdverse_reaction_observed(false);
        vaccination.setDosing_unit("bla");
        vaccination.setExpiry_date(new Date());
        vaccination.setLot_number("meh");
        vaccination.setManufacture_date(new Date());
        vaccination.setManufacturer("Vaccines Inc");
        vaccination.setDose_number(1);
        vaccination.setCreator(vaccine.getCreator());
        vaccination.setDateCreated(new Date());

        SimpleVaccination newVac = new SimpleVaccination(vaccination);
        vaccinationsService.saveOrUpdateVaccination(vaccination);
        assertFalse(vaccination.getAuditLogList().size() > 0);

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, 1);
        newVac.setScheduled_date(c.getTime());
        vaccination.setDose(600.00);
        vaccination.setAdministered(false);
        vaccination.setScheduled(false);
        vaccination.setAdministration_date(new Date());
        vaccination.setBody_site_administered("Blah");
        vaccination.setSide_administered_left(false);
        vaccination.setAdverse_reaction_observed(true);
        vaccination.setDosing_unit("mg");
        vaccination.setExpiry_date(c.getTime());
        vaccination.setLot_number("another");
        vaccination.setManufacture_date(c.getTime());
        vaccination.setManufacturer("Vaccinations Inc");
        vaccination.setDose_number(2);
        vaccination.setCreator(vaccine.getCreator());
        vaccination.setDateCreated(c.getTime());
        Vaccination vaccination1 = vaccinationsService.saveOrUpdateVaccination(new Vaccination(newVac));

        utilsService.createAuditLogRecord(vaccination, vaccination1, Excuses.NoExcuse.getName(), "No reason provided");

        assertTrue(vaccination1.getAuditLogList().size() > 0);
        assertEquals(Excuses.NoExcuse.getName(), vaccination1.getAuditLogList().get(0).getExcuse());
        assertTrue(vaccination1.getAuditLogList().get(0).getAuditLogLineItemList().size() > 2);
    }
}
