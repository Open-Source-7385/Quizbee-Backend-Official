package pe.edu.upc.quizbee.iam.domain.model.commands;

import pe.edu.upc.quizbee.iam.domain.model.valueobjects.SubscriptionStatus;

import java.util.Date;

/**
 * UpdateUserSubscriptionCommand
 * <p>
 *     Command to update a user's subscription status.
 * </p>
 */
public record UpdateUserSubscriptionCommand(
        Long userId,
        SubscriptionStatus status,
        Date expiry
) {
}
