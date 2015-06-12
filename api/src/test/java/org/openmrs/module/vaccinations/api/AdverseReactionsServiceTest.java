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


import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.vaccinations.AdverseReaction;
import org.openmrs.module.vaccinations.SimpleAdverseReaction;
import org.openmrs.module.vaccinations.Vaccine;
import org.openmrs.module.vaccinations.api.AdverseReactionsService;
import org.openmrs.module.vaccinations.api.VaccinesService;
import org.openmrs.test.BaseModuleContextSensitiveTest;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Tests {@link {AdverseReactionsService}}.
 */
public class  AdverseReactionsServiceTest extends BaseModuleContextSensitiveTest {

    private AdverseReactionsService adverseReactionsService;
    private SessionFactory sessionFactory;

    @Before
    public void before() throws Exception {
        initializeInMemoryDatabase();
        executeDataSet("src/test/resources/VaccinationsTestData.xml");
        adverseReactionsService = Context.getService(AdverseReactionsService.class);
    }

    @Test
    public void shouldSetupContext() throws Exception{
        assertNotNull(adverseReactionsService);
    }

    @Test
    public void shouldReturnAdverseReactionByUuid(){
        logger.debug("Looking for AdverseReaction by Uuid");
        AdverseReaction adverseReaction = adverseReactionsService.getAdverseReactionByAdverseReactionId(1);
        assertNotNull(adverseReaction);
        assertEquals(new Integer(1), adverseReaction.getId());
    }

    //This test will not fail if more than one unscheduled adverseReaction is added to the test data file
    @Test
    public void shouldSaveOrUpdatePatientAdverseReaction(){
        logger.warn("Updating Patient AdverseReactions");


        //Acquiring an unscheduled adverseReaction
        SimpleAdverseReaction simpleAdverseReaction = new SimpleAdverseReaction(adverseReactionsService.getAdverseReactionByUuid("6304a894-7806-44ad-97c6-0d1e04c18c66"));
        assertNotNull(simpleAdverseReaction);
        assertEquals(new Integer(1), simpleAdverseReaction.getId());
        assertEquals(new String("MEH"), simpleAdverseReaction.getAdverse_event());

        simpleAdverseReaction.setAdverse_event("BLA");

        AdverseReaction adverseReaction = new AdverseReaction(simpleAdverseReaction);
        assertEquals(new String("6304a894-7806-44ad-97c6-0d1e04c18c66"), adverseReaction.getUuid());
        assertEquals(new Integer(1), adverseReaction.getId());
        assertEquals(new String("BLA"), adverseReaction.getAdverse_event());


        adverseReactionsService.saveOrUpdateAdverseReaction(adverseReaction);

        AdverseReaction adverseReaction1 = adverseReactionsService.getAdverseReactionByUuid("6304a894-7806-44ad-97c6-0d1e04c18c66");
        assertEquals(new String("BLA"), adverseReaction1.getAdverse_event());
        //assertEquals(new Integer(2), adverseReaction.getId());
    }

    //This test will not fail if more than one unscheduled adverseReaction is added to the test data file
    @Test
    public void shouldSavePatientAdverseReaction(){
        logger.warn("Saving Patient AdverseReactions");
        Vaccine vaccine = Context.getService(VaccinesService.class).getVaccineByUuid("d5f0f111-0a36-11e5-ba5b-005056be863d");

        //Acquiring an unscheduled adverseReaction
        AdverseReaction adverseReaction = new AdverseReaction();

        //TODO: Ensure that adverse reaction is automatically assigned on constructor call
        adverseReaction.setVaccination_id(2);
        adverseReaction.setGrade("BLA");
        adverseReaction.setCreator(vaccine.getCreator());
        adverseReaction.setDateCreated(new Date());

        AdverseReaction adverseReaction1 = adverseReactionsService.saveOrUpdateAdverseReaction(adverseReaction);

        AdverseReaction adverseReaction2 = adverseReactionsService.getAdverseReactionByUuid(adverseReaction1.getUuid());
        assertEquals(new String("BLA"), adverseReaction2.getGrade());
    }
}
