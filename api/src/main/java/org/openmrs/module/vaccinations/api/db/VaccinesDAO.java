package org.openmrs.module.vaccinations.api.db;

/**
 * Created by Serghei Luchianov on 25/May/2015.
 */

import org.openmrs.module.vaccinations.Vaccine;
import org.openmrs.module.vaccinations.api.VaccinesService;

import java.util.List;

/**
 *  Database methods for {@link VaccinesService}.
 */
public interface VaccinesDAO {

    public List<Vaccine> listAllVaccines(Boolean includeRetired);
    public List<Vaccine> listScheduledVaccines(Boolean includeRetired);
    public List<Vaccine> listUnscheduledVaccines(Boolean includeRetired);
    public Vaccine saveOrUpdateVaccine(Vaccine vaccine);
    public Vaccine getVaccineByUuid(String uuid);
}
