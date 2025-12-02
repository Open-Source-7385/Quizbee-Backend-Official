package pe.edu.upc.quizbee.iam.domain.model.commands;

import pe.edu.upc.quizbee.iam.domain.model.valueobjects.UserStats;

/**
 * UpdateUserStatsCommand
 * <p>
 *     Command to update a user's statistics.
 * </p>
 */
public record UpdateUserStatsCommand(
        Long userId,
        UserStats stats
) {
}
