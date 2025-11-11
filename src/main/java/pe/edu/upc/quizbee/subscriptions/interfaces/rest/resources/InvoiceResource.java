package pe.edu.upc.quizbee.subscriptions.interfaces.rest.resources;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record InvoiceResource(
    Long id,
    String invoiceNumber,
    Long subscriptionId,
    String userId,
    String description,
    BigDecimal amountValue,
    String amountCurrency,
    LocalDateTime issueDate,
    String status
) {
}