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
import org.openmrs.module.vaccinations.AuditLog;
import org.openmrs.module.vaccinations.api.db.UtilsDAO;
import org.openmrs.module.vaccinations.Manufacturer;
import org.openmrs.module.vaccinations.AuditLogLineItem;

import java.util.List;

/**
 * It is a default implementation of  {@link UtilsDAO}.
 */
public class HibernateUtilsDAO implements UtilsDAO {
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
    public List<Manufacturer> getAllManufacturers(Boolean includeRetired) {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(Manufacturer.class);
        /*if (!includeRetired){
            crit.add(Restrictions.eq("retired", false));
        }*/
        return (List<Manufacturer>)crit.list();
    }

    @Override
    public List<AuditLog> getAuditLogByVaccinationId(int vaccinationId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AuditLog.class);
        criteria.add(Restrictions.eq("vaccination_id", vaccinationId));
        return (List<AuditLog>)criteria.list();
    }

    @Override
    public List<AuditLogLineItem> getAuditLogLineItems(int auditLogId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AuditLogLineItem.class);
        criteria.add(Restrictions.eq("audit_log_id", auditLogId));
        return (List<AuditLogLineItem>)criteria.list();
    }

    @Override
    public AuditLog saveOrUpdateAuditLog(AuditLog auditLog) {
        AuditLog auditLog1 = (AuditLog)sessionFactory.getCurrentSession().merge(auditLog);
        sessionFactory.getCurrentSession().saveOrUpdate(auditLog1);
        sessionFactory.getCurrentSession().flush();
        return auditLog1;
    }

    @Override
    public AuditLogLineItem saveOrUpdateAuditLogLineItem(AuditLogLineItem auditLogLineItem) {
        AuditLogLineItem auditLogLineItem1 = (AuditLogLineItem)sessionFactory.getCurrentSession().merge(auditLogLineItem);
        sessionFactory.getCurrentSession().saveOrUpdate(auditLogLineItem1);
        sessionFactory.getCurrentSession().flush();
        return auditLogLineItem1;
    }
}