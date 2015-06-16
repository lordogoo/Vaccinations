package org.openmrs.module.vaccinations.extension.html;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.vaccinations.util.PrivilegeConstants;
import org.openmrs.module.web.extension.LinkExt;

/**
 * Created by ICCHANGE on 16/Jun/2015.
 */
public class VaccinationsExt extends LinkExt {
    private static final Log LOG = LogFactory.getLog(VaccinationsExt.class);

    @Override
    public String getLabel() {
        return "vaccinations.vaccinationsmenuitem";
    }

    public String getPortletUrl() {
        return null;
    }

    @Override
    public String getUrl() {
        return "module/vaccinations/vaccinationsPortlet";
    }

    @Override
    public String getRequiredPrivilege() {
        return PrivilegeConstants.MANAGE_VACCINATIONS;
    }
}
