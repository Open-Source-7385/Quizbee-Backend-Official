package pe.edu.upc.quizbee.subscriptions.interfaces.rest.resources;

public record UpdatePaymentMethodResource(
    String paymentType,
    String cardLastFourDigits,
    String cardExpiryDate
) {
}