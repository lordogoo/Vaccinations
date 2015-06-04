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
package org.openmrs.module.vaccinations.api.db.hibernate;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.vaccinations.Vaccine;
import org.openmrs.module.vaccinations.api.db.VaccinesDAO;

import java.util.List;

/**
 * It is a default implementation of  {@link VaccinesDAO}.
 */
public class HibernateVaccinesDAO implements VaccinesDAO {
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private SessionFactory sessionFactory;
	
	/**
     * @param sessionFactory the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
	    this.sessionFactory = sessionFactory;
    }
    
	/**
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
	    return sessionFactory;
    }

	@Override
	public List<Vaccine> getAllVaccines(Boolean includeRetired) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(Vaccine.class);
		if (!includeRetired){
			crit.add(Restrictions.eq("retired", false));
		}
		return (List<Vaccine>)crit.list();
	}

	@Override
	public List<Vaccine> getUnscheduledVaccines(Boolean includeRetired) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(Vaccine.class);
		crit.add(Restrictions.eq("scheduled", false));
		if (!includeRetired){
			crit.add(Restrictions.eq("retired", false));
		}
		return (List<Vaccine>)crit.list();
	}

	@Override
	public List<Vaccine> getScheduledVaccines(Boolean includeRetired) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(Vaccine.class);
		crit.add(Restrictions.eq("scheduled", true));
		if (!includeRetired){
			crit.add(Restrictions.eq("retired", false));
		}
		return (List<Vaccine>)crit.list();
	}

	@Override
	public Vaccine saveOrUpdateVaccine(Vaccine vaccine) {
		sessionFactory.getCurrentSession().saveOrUpdate(vaccine);
		return vaccine;
	}

	@Override
	public Vaccine getVaccineByUuid(String uuid) {
		if (uuid == null)
			return null;

		Criteria crit = sessionFactory.getCurrentSession().createCriteria(Vaccine.class);
		crit.add(Restrictions.eq("uuid", uuid));

		List<Vaccine> vaccineList = (List<Vaccine>)crit.list();
		if (vaccineList.isEmpty())
			return null;

		return vaccineList.get(0);
	}
}