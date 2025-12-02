package pe.edu.upc.quizbee.subscriptions.interfaces.rest.transform;

import pe.edu.upc.quizbee.subscriptions.domain.model.aggregates.Plan;
import pe.edu.upc.quizbee.subscriptions.interfaces.rest.resources.PlanResource;

public class PlanResourceFromEntityAssembler {
    
    public static PlanResource toResourceFromEntity(Plan entity) {
        return new PlanResource(
            entity.getId(),
            entity.getName(),
            entity.getSubtitle(),
            entity.getPrice().amount(),
            entity.getPrice().currency(),
            entity.getFeatures(),
            entity.getPopular()
        );
    }
}