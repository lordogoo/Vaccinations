package org.openmrs.module.vaccinations.enums;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openmrs.module.vaccinations.util.Serializers.RouteMapEnumSerializer;


/**
 * Created by Serghei Luchianov on 2015-06-25.
 */
@JsonSerialize(using = RouteMapEnumSerializer.class)
public enum RouteMapEnum {
    Oral ("160240", new String[] {"Oral"}),
    Intramuscular ("160243", new String[] {BodySites.Thigh.getName(), BodySites.Tricep.getName(), BodySites.Deltoid.getName(), BodySites.InnerForearm.getName(), BodySites.OuterForearm.getName()}),
    Subcutaneous ("160245", new String[] {BodySites.Buttock.getName(), BodySites.Buttock.getName(), BodySites.Deltoid.getName()}),
    Intranasal ("161253", new String[] {"Nasal"}),
    Transdermal ("162391", new String[] {BodySites.Buttock.getName(), BodySites.Buttock.getName(), BodySites.Deltoid.getName()}),
    Intradermal ("123456", new String[] {BodySites.Buttock.getName(), BodySites.Buttock.getName(), BodySites.Deltoid.getName()});

    private final String conceptId;
    private final String[] sites;

    private RouteMapEnum(String conceptId, String[] sites){
        this.conceptId = conceptId;
        this.sites = sites;
    }

    public String getConceptId() {
        return conceptId;
    }

    public String[] getSites() {
        return sites;
    }
}
