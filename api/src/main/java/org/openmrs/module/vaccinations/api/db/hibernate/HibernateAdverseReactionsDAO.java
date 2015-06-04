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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.vaccinations.AdverseReaction;
import org.openmrs.module.vaccinations.api.db.AdverseReactionsDAO;

import java.util.List;

/**
 * It is a default implementation of  {@link AdverseReactionsDAO}.
 */
public class HibernateAdverseReactionsDAO implements AdverseReactionsDAO {
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
	public AdverseReaction saveOrUpdateAdverseReaction(AdverseReaction adverseReaction) {
		sessionFactory.getCurrentSession().saveOrUpdate(adverseReaction);
		return adverseReaction;
	}

	@Override
	public AdverseReaction getAdverseReactionByAdverseReactionId(int adverseReactionId) {
		return (AdverseReaction)sessionFactory.getCurrentSession().get(AdverseReaction.class, adverseReactionId);
	}

	@Override
	public AdverseReaction getAdverseReactionByUuid(String uuid) {
		if (uuid == null)
			return null;

		Criteria crit = sessionFactory.getCurrentSession().createCriteria(AdverseReaction.class);
		crit.add(Restrictions.eq("uuid", uuid));

		List<AdverseReaction> adverseReactionList = (List<AdverseReaction>)crit.list();
		if (adverseReactionList.isEmpty())
			return null;

		return adverseReactionList.get(0);
	}
}