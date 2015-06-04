package org.openmrs.module.vaccinations.api.db;

/**
 * Created by ICCHANGE on 25/May/2015.
 */

import org.openmrs.module.vaccinations.Vaccine;
import org.openmrs.module.vaccinations.api.VaccinesService;

import java.util.List;

public interface VaccinesDAO {

    public List<Vaccine> getAllVaccines(Boolean includeRetired);
    public List<Vaccine> getScheduledVaccines(Boolean includeRetired);
    public List<Vaccine> getUnscheduledVaccines(Boolean includeRetired);
    public Vaccine saveOrUpdateVaccine(Vaccine vaccine);
    public Vaccine getVaccineByUuid(String uuid);
}
