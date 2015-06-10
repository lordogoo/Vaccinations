package org.openmrs.module.vaccinations.api.db;

/**
 * Created by Serghei Luchianov on 25/May/2015.
 */
import org.openmrs.module.vaccinations.AdverseReaction;
import org.openmrs.module.vaccinations.api.AdverseReactionsService;
public interface AdverseReactionsDAO {
    public AdverseReaction saveOrUpdateAdverseReaction(AdverseReaction adverseReaction);
    public AdverseReaction getAdverseReactionByUuid(String uuid);
    public AdverseReaction getAdverseReactionByAdverseReactionId(int adverseReactionId);
}
