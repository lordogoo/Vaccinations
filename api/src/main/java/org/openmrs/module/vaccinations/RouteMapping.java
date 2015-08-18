package org.openmrs.module.vaccinations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serghei Luchianov on 17/Aug/2015.
 */
public class RouteMapping  implements Serializable {

    private String conceptId;
    private ArrayList<String> bodySitesList;

    private void RouteMapping(){}

    public String getConceptId() {
        return conceptId;
    }

    public void setConceptId(String conceptId) {
        this.conceptId = conceptId;
    }

    public ArrayList<String> getBodySitesList() {
        return bodySitesList;
    }

    public void setBodySitesList(ArrayList<String> bodySitesList) {
        this.bodySitesList = bodySitesList;
    }
}
