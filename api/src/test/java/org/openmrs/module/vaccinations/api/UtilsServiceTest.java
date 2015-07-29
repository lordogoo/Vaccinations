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
import org.openmrs.module.vaccinations.AuditLog;
import org.openmrs.module.vaccinations.Manufacturer;
import org.openmrs.test.BaseModuleContextSensitiveTest;


import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests {@link {VaccinationsService}}.
 */
public class UtilsServiceTest extends BaseModuleContextSensitiveTest {

    private UtilsService utilsService;

    @Before
    public void before() throws Exception {
        initializeInMemoryDatabase();
        executeDataSet("src/test/resources/VaccinationsTestData.xml");
        utilsService = Context.getService(UtilsService.class);
    }

	@Test
	public void shouldSetupContext() throws Exception{
        assertNotNull(utilsService);
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
        assertEquals(1, auditLogList.size());
        assertEquals(3, auditLogList.get(0).getAuditLogLineItemList().size());
    }
}
