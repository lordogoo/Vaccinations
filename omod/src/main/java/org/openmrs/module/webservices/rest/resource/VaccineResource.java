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
package org.openmrs.module.webservices.rest.resource;

import org.openmrs.module.vaccinations.Vaccine;
import org.openmrs.module.vaccinations.api.VaccinesService;
import org.openmrs.api.context.Context;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.*;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

import java.util.List;

/**
 * {@link Resource} for {@link Vaccine}, supporting standard CRUD operations
 */
@Resource(name = "/v2/vaccinations/vaccine", supportedClass = Vaccine.class, supportedOpenmrsVersions = {"1.8.*", "1.9.*", "1.10.*", "1.11.*"})
public class VaccineResource extends DataDelegatingCrudResource<Vaccine> {

    /**
     * @see org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResource#getByUniqueId(java.lang.String)
     */
    @Override
    public Vaccine getByUniqueId(String uniqueId) {
        return new Vaccine(); //Context.getConceptService().getVaccineByUuid(uniqueId);
    }

    @Override
    protected void delete(Vaccine vaccine, String s, RequestContext requestContext) throws ResponseException {

    }

    /**
     * @see org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResource#newDelegate()
     */
    @Override
    public Vaccine newDelegate() {
        return new Vaccine();
    }

    /**
     * @see org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceHandler#save(java.lang.Object)
     */
    @Override
    public Vaccine save(Vaccine delegate) {
        return new Vaccine(); //Context.getConceptService().saveVaccine(delegate);
    }

    /**
     * @see org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResource#purge(java.lang.Object,
     *      org.openmrs.module.webservices.rest.web.RequestContext)
     */
    @Override
    public void purge(Vaccine delegate, RequestContext context) throws ResponseException {
        if (delegate == null)
            return;
        //Context.getConceptService().purgeVaccine(delegate);
    }

    /**
     * @see org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResource#getRepresentationDescription(org.openmrs.module.webservices.rest.web.representation.Representation)
     */
    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation rep) {

        DelegatingResourceDescription description = new DelegatingResourceDescription();
        description.addProperty("id");
        description.addProperty("name");
        description.addProperty("indication");
        description.addProperty("dose");
        description.addProperty("dose_number");
        description.addProperty("dosing_unit");
        description.addProperty("route");
        description.addProperty("scheduled");

        return description;
    }

    /**
     * @see org.openmrs.module.webservices.rest.web.resource.impl.MetadataDelegatingCrudResource#getCreatableProperties()
     */
    @Override
    public DelegatingResourceDescription getCreatableProperties() {
        DelegatingResourceDescription description = super.getCreatableProperties();
        description.addProperty("id");
        description.addProperty("name");
        description.addProperty("indication_name");
        description.addProperty("dose");
        description.addProperty("dose_number");
        description.addProperty("dosing_unit");
        description.addProperty("route");
        description.addProperty("scheduled");

        return description;
    }

    /**
     * @see org.openmrs.module.webservices.rest.web.resource.impl.DelegatingCrudResource#doGetAll(org.openmrs.module.webservices.rest.web.RequestContext)
     */
    @Override
    protected NeedsPaging<Vaccine> doGetAll(RequestContext context) throws ResponseException {
        Boolean showRetiredVaccines = false;
        return new NeedsPaging<Vaccine>(Context.getService(VaccinesService.class).getAllVaccines(showRetiredVaccines), context);
    }
}
