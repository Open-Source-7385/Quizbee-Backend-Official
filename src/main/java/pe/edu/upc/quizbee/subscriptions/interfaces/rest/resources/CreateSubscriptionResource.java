package pe.edu.upc.quizbee.subscriptions.interfaces.rest.resources;

public record CreateSubscriptionResource(
    String userId,
    Long planId,
    String paymentType,
    String cardLastFourDigits,
    String cardExpiryDate
) {
}