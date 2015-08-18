package org.openmrs.module.vaccinations;

import org.openmrs.module.vaccinations.enums.BodySites;
import org.openmrs.module.vaccinations.enums.Routes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serghei Luchianov on 17/Aug/2015.
 */
public class RouteMapping  implements Serializable {

    private String name;
    private Routes route;
    private ArrayList<BodySites> bodySitesList;

    private void RouteMapping(){}

    private void RouteMapping(Routes route, ArrayList<BodySites> bodySitesList){
        this.route = route;
        this.name = route.getName();
        this.bodySitesList = bodySitesList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Routes getRoute() {
        return route;
    }

    public void setRoute(Routes route) {
        this.route = route;
        this.name = route.getName();
    }

    public ArrayList<BodySites> getBodySitesList() {
        return bodySitesList;
    }

    public void setBodySitesList(ArrayList<BodySites> bodySitesList) {
        this.bodySitesList = bodySitesList;
    }
}
