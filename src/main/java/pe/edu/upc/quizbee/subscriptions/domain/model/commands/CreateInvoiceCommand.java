package pe.edu.upc.quizbee.subscriptions.domain.model.commands;

public record CreateInvoiceCommand(
    Long subscriptionId,
    String description
) {
}