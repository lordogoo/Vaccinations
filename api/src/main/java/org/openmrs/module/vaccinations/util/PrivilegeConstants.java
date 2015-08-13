/*
 *
 */
package org.openmrs.module.vaccinations.util;

import org.openmrs.Privilege;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PrivilegeConstants {
	public static final String MANAGE_VACCINES = "Manage Vaccines";
	public static final String VIEW_VACCINES = "View Vaccines";
	public static final String PURGE_VACCINES = "Purge Vaccines";

	public static final String MANAGE_VACCINATIONS = "Manage Vaccinations";
	public static final String VIEW_VACCINATIONS = "View Vaccinations";
	public static final String PURGE_VACCINATIONS = "Purge Vaccinations";

	public static final String MANAGE_ADVERSE_REACTIONS = "Manage AdverseReactions";
	public static final String VIEW_ADVERSE_REACTIONS = "View AdverseReactions";
	public static final String PURGE_ADVERSE_REACTIONS = "Purge AdverseReactions";

    public static final String VIEW_AUDIT_LOG = "View Audit Log";


	public static final String[] PRIVILEGE_NAMES = new String[] {
			MANAGE_VACCINES, VIEW_VACCINES, PURGE_VACCINES,
			MANAGE_VACCINATIONS, VIEW_VACCINATIONS, PURGE_VACCINATIONS,
			MANAGE_ADVERSE_REACTIONS, VIEW_ADVERSE_REACTIONS,
			PURGE_ADVERSE_REACTIONS, VIEW_AUDIT_LOG
	};

	protected PrivilegeConstants() { }

	/**
	 * Gets all the privileges defined by the module.
	 * @return The module privileges.
	 */
	public static Set<Privilege> getModulePrivileges() {
		Set<Privilege> privileges = new HashSet<Privilege>(PRIVILEGE_NAMES.length);

		UserService service = Context.getUserService();
		if (service == null) {
			throw new IllegalStateException("The OpenMRS user service cannot be loaded.");
		}

		for (String name : PRIVILEGE_NAMES) {
			privileges.add(service.getPrivilege(name));
		}

		return privileges;
	}

	/**
	 * Gets the default privileges needed to fully use the module.
	 * @return A set containing the default set of privileges.
	 *//*
	public static Set<Privilege> getDefaultPrivileges() {
		Set<Privilege> privileges = getModulePrivileges();

		UserService service = Context.getUserService();
		if (service == null) {
			throw new IllegalStateException("The OpenMRS user service cannot be loaded.");
		}

		List<String> names = new ArrayList<String>();
		names.add(org.openmrs.util.PrivilegeConstants.EDIT_PATIENT_IDENTIFIERS);
		names.add(org.openmrs.util.PrivilegeConstants.VIEW_ADMIN_FUNCTIONS);
		names.add(org.openmrs.util.PrivilegeConstants.VIEW_CONCEPTS);
		names.add(org.openmrs.util.PrivilegeConstants.VIEW_LOCATIONS);
		names.add(org.openmrs.util.PrivilegeConstants.VIEW_NAVIGATION_MENU);
		names.add(org.openmrs.util.PrivilegeConstants.VIEW_USERS);
		names.add(org.openmrs.util.PrivilegeConstants.VIEW_ROLES);

		for (String name : names) {
			privileges.add(service.getPrivilege(name));
		}

		return privileges;
	}*/
}
