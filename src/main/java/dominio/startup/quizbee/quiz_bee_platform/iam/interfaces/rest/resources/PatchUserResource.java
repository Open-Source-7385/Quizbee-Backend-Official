package dominio.startup.quizbee.quiz_bee_platform.iam.interfaces.rest.resources;

import java.util.Date;

/**
 * PatchUserResource
 * <p>
 *     Resource for partial updates to user (stats, subscription, etc.).
 * </p>
 */
public record PatchUserResource(
        UserStatsResource stats,
        String subscriptionStatus,
        Date subscriptionExpiry
) {
}
