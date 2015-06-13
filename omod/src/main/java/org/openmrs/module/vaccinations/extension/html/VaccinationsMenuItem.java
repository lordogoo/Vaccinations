package org.openmrs.module.vaccinations.extension.html;

import org.openmrs.module.web.extension.LinkExt;

public class VaccinationsMenuItem extends LinkExt {

	@Override
	public MEDIA_TYPE getMediaType() {
		return MEDIA_TYPE.html;
	}
	
	
	@Override
	public String getLabel() {
		return "vaccinations.vaccinationsmenuitem";
	}

	
	@Override
	public String getUrl() {
		return "module/vaccinations/vaccinationsPatientSelector.form";
	}

	@Override
	public String getRequiredPrivilege() {
		return "Manage Vaccinations";
	}

}
