package org.openmrs.module.vaccinations.extension.html;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.vaccinations.util.PrivilegeConstants;
import org.openmrs.module.web.extension.PatientDashboardTabExt;

/**
 * Created by ICCHANGE on 16/Jun/2015.
 */
public class VaccinationsExt extends PatientDashboardTabExt {
    private static final Log LOG = LogFactory.getLog(VaccinationsExt.class);

    @Override
    public String getPortletUrl() {
        return "vaccinationsPortlet";
    }

    @Override
    public String getTabName() {
        return "vaccinations.vaccinationsmenuitem";
    }

    @Override
    public String getTabId() {
        return "vaccinations.vaccinations";
    }

    @Override
    public String getRequiredPrivilege() {
        return PrivilegeConstants.MANAGE_VACCINATIONS;
    }
}
