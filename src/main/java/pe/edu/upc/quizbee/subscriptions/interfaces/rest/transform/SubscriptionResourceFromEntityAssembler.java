package pe.edu.upc.quizbee.subscriptions.interfaces.rest.transform;

import pe.edu.upc.quizbee.subscriptions.domain.model.aggregates.Subscription;
import pe.edu.upc.quizbee.subscriptions.interfaces.rest.resources.SubscriptionResource;

public class SubscriptionResourceFromEntityAssembler {
    
    public static SubscriptionResource toResourceFromEntity(Subscription entity) {
        return new SubscriptionResource(
            entity.getId(),
            entity.getUserId(),
            entity.getPlanId(),
            entity.getPrice().amount(),
            entity.getPrice().currency(),
            entity.getStatus().name(),
            entity.getStartDate(),
            entity.getNextBillingDate(),
            entity.getPaymentMethod().type(),
            entity.getPaymentMethod().lastFourDigits(),
            entity.getPaymentMethod().expiryDate()
        );
    }
}