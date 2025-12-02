package pe.edu.upc.quizbee.subscriptions.interfaces.rest.resources;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SubscriptionResource(
    Long id,
    String userId,
    Long planId,
    BigDecimal priceAmount,
    String priceCurrency,
    String status,
    LocalDateTime startDate,
    LocalDateTime nextBillingDate,
    String paymentType,
    String paymentLastDigits,
    String paymentExpiry
) {
}