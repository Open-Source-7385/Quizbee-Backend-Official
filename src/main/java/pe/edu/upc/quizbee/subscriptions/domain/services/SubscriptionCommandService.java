package pe.edu.upc.quizbee.subscriptions.domain.services;

import pe.edu.upc.quizbee.subscriptions.domain.model.aggregates.Subscription;
import pe.edu.upc.quizbee.subscriptions.domain.model.commands.CancelSubscriptionCommand;
import pe.edu.upc.quizbee.subscriptions.domain.model.commands.ChangePlanCommand;
import pe.edu.upc.quizbee.subscriptions.domain.model.commands.CreateSubscriptionCommand;
import pe.edu.upc.quizbee.subscriptions.domain.model.commands.UpdatePaymentMethodCommand;

import java.util.Optional;

public interface SubscriptionCommandService {
    Long handle(CreateSubscriptionCommand command);
    Optional<Subscription> handle(ChangePlanCommand command);
    Optional<Subscription> handle(UpdatePaymentMethodCommand command);
    Optional<Subscription> handle(CancelSubscriptionCommand command);
}