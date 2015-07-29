package org.openmrs.module.vaccinations.api.db;

/**
 * Created by Serghei Luchianov on 25/May/2015.
 */

import org.openmrs.module.vaccinations.Manufacturer;
import org.openmrs.module.vaccinations.api.UtilsService;

import java.util.List;

/**
 *  Database methods for {@link UtilsService}.
 */
public interface UtilsDAO {

    public List<Manufacturer> getAllManufacturers(Boolean includeRetired);
}
