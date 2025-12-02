package pe.edu.upc.quizbee.subscriptions.interfaces.rest.transform;

import pe.edu.upc.quizbee.subscriptions.domain.model.commands.CreateSubscriptionCommand;
import pe.edu.upc.quizbee.subscriptions.domain.model.valueobjects.PaymentMethod;
import pe.edu.upc.quizbee.subscriptions.interfaces.rest.resources.CreateSubscriptionResource;

public class CreateSubscriptionCommandFromResourceAssembler {
    
    public static CreateSubscriptionCommand toCommandFromResource(CreateSubscriptionResource resource) {
        PaymentMethod paymentMethod = new PaymentMethod(
            resource.paymentType(),
            resource.cardLastFourDigits(),
            resource.cardExpiryDate()
        );
        
        return new CreateSubscriptionCommand(
            resource.userId(),
            resource.planId(),
            paymentMethod
        );
    }
}