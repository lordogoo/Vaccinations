package org.openmrs.module.vaccinations.api.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.Location;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.vaccinations.*;
import org.openmrs.module.vaccinations.api.db.UtilsDAO;
import org.openmrs.module.vaccinations.api.UtilsService;
import org.openmrs.module.vaccinations.enums.Excuses;
import org.openmrs.module.vaccinations.util.Constants;

import java.util.List;

/**
 * Created by Serghei Luchianov on 14/Jul/2015.
 */
public class UtilsServiceImpl extends BaseOpenmrsService implements UtilsService {

    protected final Log log = LogFactory.getLog(this.getClass());
    private SessionFactory sessionFactory;
    private UtilsDAO dao;

    /**
     * @param dao the dao to set
     */
    public void setDao(UtilsDAO dao) {
        this.dao = dao;
    }

    /**
     * @return the dao
     */
    public UtilsDAO getDao() {
        return dao;
    }

    @Override
    public List<Manufacturer> getAllManufacturers(Boolean includeRetired) throws APIException {
        return dao.getAllManufacturers(includeRetired);
    }

    @Override
    public List<AuditLog> getAuditLogByVaccinationId(int vaccinationId) throws APIException {
        return dao.getAuditLogByVaccinationId(vaccinationId);
    }

    @Override
    public List<AuditLogLineItem> getAuditLogLineItems(int auditLogId) throws APIException {
        return dao.getAuditLogLineItems(auditLogId);
    }

    @Override
    public AuditLog saveOrUpdateAuditLog(AuditLog auditLog) throws APIException {
        return dao.saveOrUpdateAuditLog(auditLog);
    }

    @Override
    public AuditLogLineItem saveOrUpdateAuditLogLineItem(AuditLogLineItem auditLogLineItem) throws APIException {
        return dao.saveOrUpdateAuditLogLineItem(auditLogLineItem);
    }

    /*
        This method should always be run after the changes have been saved. That way there will always be a proper id for the newly created Vaccination
     */
    @Override
    public AuditLog createAuditLogRecord(Vaccination oldVac, Vaccination newVac, Excuses excuse, String reason) throws APIException {
        AuditLog auditLog = new AuditLog();
        Location userLocus = Context.getLocationService().getLocation(Integer.parseInt(Context.getAuthenticatedUser().getUserProperty(Constants.LOCATIONPROPERTY)));
        String userName = Context.getAuthenticatedUser().getName();

        auditLog.setVaccination_id(newVac.getId());
        auditLog.setLocation(userLocus.toString());
        auditLog.setChanged_by(userName);
        auditLog.setExcuse(excuse.getName());
        auditLog.setReason(reason);

        auditLog = saveOrUpdateAuditLog(auditLog);

        if (oldVac.getScheduled_date() != newVac.getScheduled_date()) {
            AuditLogLineItem auditLogLineItem = new AuditLogLineItem();
            auditLogLineItem.setField("Scheduled Date");
            auditLogLineItem.setOriginal_value(oldVac.getScheduled_date().toString());
            auditLogLineItem.setNew_value(newVac.getScheduled_date().toString());

            auditLogLineItem.setAudit_log_id(auditLog.getId());
            Context.getService(UtilsService.class).saveOrUpdateAuditLogLineItem(auditLogLineItem);
        }

        if (oldVac.getName() != newVac.getName()) {
            AuditLogLineItem auditLogLineItem = new AuditLogLineItem();
            auditLogLineItem.setField("Name");
            auditLogLineItem.setOriginal_value(oldVac.getName().toString());
            auditLogLineItem.setNew_value(newVac.getName().toString());

            auditLogLineItem.setAudit_log_id(auditLog.getId());
            Context.getService(UtilsService.class).saveOrUpdateAuditLogLineItem(auditLogLineItem);
        }

        if (oldVac.getIndication_name() != newVac.getIndication_name()) {
            AuditLogLineItem auditLogLineItem = new AuditLogLineItem();
            auditLogLineItem.setField("Indication Name");
            auditLogLineItem.setOriginal_value(oldVac.getIndication_name().toString());
            auditLogLineItem.setNew_value(newVac.getIndication_name().toString());

            auditLogLineItem.setAudit_log_id(auditLog.getId());
            Context.getService(UtilsService.class).saveOrUpdateAuditLogLineItem(auditLogLineItem);
        }

        if (oldVac.getDose() != newVac.getDose()) {
            AuditLogLineItem auditLogLineItem = new AuditLogLineItem();
            auditLogLineItem.setField("Dose");
            auditLogLineItem.setOriginal_value(oldVac.getDose().toString());
            auditLogLineItem.setNew_value(newVac.getDose().toString());

            auditLogLineItem.setAudit_log_id(auditLog.getId());
            Context.getService(UtilsService.class).saveOrUpdateAuditLogLineItem(auditLogLineItem);
        }

        if (oldVac.getDosing_unit() != newVac.getDosing_unit()) {
            AuditLogLineItem auditLogLineItem = new AuditLogLineItem();
            auditLogLineItem.setField("Dosing Unit");
            auditLogLineItem.setOriginal_value(oldVac.getDosing_unit().toString());
            auditLogLineItem.setNew_value(newVac.getDosing_unit().toString());

            auditLogLineItem.setAudit_log_id(auditLog.getId());
            Context.getService(UtilsService.class).saveOrUpdateAuditLogLineItem(auditLogLineItem);
        }

        if (oldVac.getRoute() != newVac.getRoute()) {
            AuditLogLineItem auditLogLineItem = new AuditLogLineItem();
            auditLogLineItem.setField("Route");
            auditLogLineItem.setOriginal_value(oldVac.getRoute().toString());
            auditLogLineItem.setNew_value(newVac.getRoute().toString());

            auditLogLineItem.setAudit_log_id(auditLog.getId());
            Context.getService(UtilsService.class).saveOrUpdateAuditLogLineItem(auditLogLineItem);
        }

        if (oldVac.getScheduled() != newVac.getScheduled()) {
            AuditLogLineItem auditLogLineItem = new AuditLogLineItem();
            auditLogLineItem.setField("Scheduled Flag");
            auditLogLineItem.setOriginal_value(((Boolean)oldVac.getScheduled()).toString());
            auditLogLineItem.setNew_value(((Boolean)newVac.getScheduled()).toString());

            auditLogLineItem.setAudit_log_id(auditLog.getId());
            Context.getService(UtilsService.class).saveOrUpdateAuditLogLineItem(auditLogLineItem);
        }

        if (oldVac.getAdverse_reaction() != newVac.getAdverse_reaction()) {
            AuditLogLineItem auditLogLineItem = new AuditLogLineItem();
            auditLogLineItem.setField("Adverse Reaction");
            auditLogLineItem.setOriginal_value(oldVac.getAdverse_reaction().toString());
            auditLogLineItem.setNew_value(newVac.getAdverse_reaction().toString());

            auditLogLineItem.setAudit_log_id(auditLog.getId());
            Context.getService(UtilsService.class).saveOrUpdateAuditLogLineItem(auditLogLineItem);
        }

        if (oldVac.getScheduled() != newVac.getScheduled()) {
            AuditLogLineItem auditLogLineItem = new AuditLogLineItem();
            auditLogLineItem.setField("Scheduled Flag");
            auditLogLineItem.setOriginal_value(((Boolean)oldVac.getScheduled()).toString());
            auditLogLineItem.setNew_value(((Boolean)newVac.getScheduled()).toString());

            auditLogLineItem.setAudit_log_id(auditLog.getId());
            Context.getService(UtilsService.class).saveOrUpdateAuditLogLineItem(auditLogLineItem);
        }

        return auditLog;
    }
}
