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

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.vaccinations.Vaccine;
import org.openmrs.test.BaseModuleContextSensitiveTest;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests {@link {VaccinationsService}}.
 */
public class  VaccinesServiceTest extends BaseModuleContextSensitiveTest {

    private VaccinesService vaccinesService;

    @Before
    public void before() throws Exception {
        initializeInMemoryDatabase();
        executeDataSet("src/test/resources/VaccinationsTestData.xml");
        vaccinesService = Context.getService(VaccinesService.class);
    }

	@Test
	public void shouldSetupContext() throws Exception{
        assertNotNull(vaccinesService);
	}

    @Test
    public void shouldReturnVaccineByUuid(){
        logger.debug("Looking for Vaccine by Uuid");
        Vaccine vaccine = vaccinesService.getVaccineByUuid("d5f0b023-0a36-11e5-ba5b-005056be863d");
        assertNotNull(vaccine);
        assertEquals(new Integer(34), vaccine.getId());
    }

    @Test
    public void shouldEnsureVaccineBodySitePresent(){
        logger.debug("Looking for Vaccine by Uuid");
        Vaccine vaccine = vaccinesService.getVaccineByUuid("d5f0b023-0a36-11e5-ba5b-005056be863d");
        assertNotNull(vaccine);
        assertEquals(new String("b"), vaccine.getBody_site_administered());
        assertEquals(new Boolean(true), vaccine.getSide_administered_left());
        assertEquals(new Integer(34), vaccine.getId());

        Vaccine vaccine1 = vaccinesService.getVaccineByUuid("d5f0f111-0a36-11e5-ba5b-005056be863d");
        assertNotNull(vaccine1);
        assertEquals(new String("a"), vaccine1.getBody_site_administered());
        assertNull(vaccine1.getSide_administered_left());
        assertEquals(new Integer(36), vaccine1.getId());
    }

    //This test will not fail if more than one unscheduled vaccine is added to the test data file
    @Test
    public void shouldReturnListOfUnscheduledVaccines(){
        logger.debug("Looking for Unscheduled Vaccines");

        //Acquiring an unscheduled vaccine
        Vaccine vaccine = vaccinesService.getVaccineByUuid("d5f0f111-0a36-11e5-ba5b-005056be863d");
        assertNotNull(vaccine);
        assertEquals(new Integer(36), vaccine.getId());

        List<Vaccine> vaccineList = vaccinesService.getUnscheduledVaccines(false);
        assertTrue(vaccineList.contains(vaccine));
    }
}
