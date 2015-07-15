package org.openmrs.module.vaccinations.api.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.vaccinations.api.db.UtilsDAO;
import org.openmrs.module.vaccinations.Manufacturer;
import org.openmrs.module.vaccinations.api.UtilsService;

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
}
