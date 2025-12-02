package pe.edu.upc.quizbee.subscriptions.domain.model.commands;

public record ChangePlanCommand(
    Long subscriptionId,
    Long newPlanId
) {
}