package org.openmrs.module.vaccinations;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.vaccinations.api.UtilsService;

/**
 * Created by Serghei Luchianov on 14/Jul/2015.
 */
public class AuditLog implements Serializable {

    private Integer id;
    private Integer vaccination_id;
    private String changed_by;
    private String location;
    private Date dateChanged;

    private String excuse;
    private String reason;

    private List<AuditLogLineItem> auditLogLineItemList;

    public AuditLog(){
       //auditLogLineItemList = Context.getService(UtilsService.class).getAuditLogLineItems(this.id);
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getExcuse() {
        return excuse;
    }

    public void setExcuse(String excuse) {
        this.excuse = excuse;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<AuditLogLineItem> getAuditLogLineItemList() {
        if (auditLogLineItemList != null ) {
            return auditLogLineItemList;
        }else{
            auditLogLineItemList = Context.getService(UtilsService.class).getAuditLogLineItems(this.id);
            return auditLogLineItemList;
        }
    }

    public void setAuditLogLineItemList(List<AuditLogLineItem> auditLogLineItemList) {
        this.auditLogLineItemList = auditLogLineItemList;
    }

    public Integer getVaccination_id() {
        return vaccination_id;
    }

    public void setVaccination_id(Integer vaccination_id) {
        this.vaccination_id = vaccination_id;
    }

    public String getChanged_by() {
        return changed_by;
    }

    public void setChanged_by(String changed_by) {
        this.changed_by = changed_by;
    }

    public Date getDateChanged() {
        return dateChanged;
    }

    public void setDateChanged(Date dateChanged) {
        this.dateChanged = dateChanged;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}