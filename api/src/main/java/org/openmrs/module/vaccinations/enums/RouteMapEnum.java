package org.openmrs.module.vaccinations.enums;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.openmrs.module.vaccinations.util.Serializers.RouteMapEnumSerializer;


/**
 * Created by Serghei Luchianov on 2015-06-25.
 */
@JsonSerialize(using = RouteMapEnumSerializer.class)
public enum RouteMapEnum {
    Intramuscular ("160243", new String[] {BodySites.InnerForearm.getName(), BodySites.OuterForearm.getName(), BodySites.UpperArmTricep.getName(), BodySites.UpperArmDeltoid.getName(), BodySites.Thigh.getName(), BodySites.LowerLeg.getName(), BodySites.Buttock.getName()}),
    Intradermal ("123456", new String[] {BodySites.InnerForearm.getName(), BodySites.OuterForearm.getName(), BodySites.UpperArmTricep.getName(), BodySites.UpperArmDeltoid.getName(), BodySites.Thigh.getName(), BodySites.LowerLeg.getName(), BodySites.Buttock.getName()}),
    Subcutaneous ("160245", new String[] {BodySites.InnerForearm.getName(), BodySites.OuterForearm.getName(), BodySites.UpperArmTricep.getName(), BodySites.UpperArmDeltoid.getName(), BodySites.Thigh.getName(), BodySites.LowerLeg.getName(), BodySites.Buttock.getName(), BodySites.Abdomen.getName()}),
    Transdermal ("162391", new String[] {BodySites.InnerForearm.getName(), BodySites.OuterForearm.getName(), BodySites.UpperArmTricep.getName(), BodySites.UpperArmDeltoid.getName(), BodySites.Thigh.getName(), BodySites.LowerLeg.getName(), BodySites.Buttock.getName(), BodySites.Abdomen.getName(), BodySites.Chest.getName(), BodySites.Back.getName()}),
    Oral ("160240", new String[] {BodySites.Mouth.getName()}),
    Intranasal ("161253", new String[] {BodySites.Nostril.getName()});

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
