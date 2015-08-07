package org.openmrs.module.vaccinations;

import java.io.Serializable;

/**
 * Created by Serghei Luchianov on 21/Jul/2015.
 */
public class AuditLogLineItem implements Serializable {

    private Integer id;
    private Integer audit_log_id;
    private String field;
    private String original_value;
    private String new_value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAudit_log_id() {
        return audit_log_id;
    }

    public void setAudit_log_id(Integer audit_log_id) {
        this.audit_log_id = audit_log_id;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOriginal_value() {
        return original_value;
    }

    public void setOriginal_value(String original_value) {
        this.original_value = original_value;
    }

    public String getNew_value() {
        return new_value;
    }

    public void setNew_value(String new_value) {
        this.new_value = new_value;
    }
}
