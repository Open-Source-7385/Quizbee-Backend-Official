package pe.edu.upc.quizbee.subscriptions.domain.model.commands;

import pe.edu.upc.quizbee.subscriptions.domain.model.valueobjects.PaymentMethod;

public record CreateSubscriptionCommand(
    String userId,
    Long planId,
    PaymentMethod paymentMethod
) {
}