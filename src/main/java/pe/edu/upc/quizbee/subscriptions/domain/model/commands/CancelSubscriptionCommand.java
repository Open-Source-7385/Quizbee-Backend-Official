package pe.edu.upc.quizbee.subscriptions.domain.model.commands;

public record CancelSubscriptionCommand(
    Long subscriptionId
) {
}