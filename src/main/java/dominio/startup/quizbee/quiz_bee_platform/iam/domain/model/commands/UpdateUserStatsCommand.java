package dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.commands;

import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.valueobjects.UserStats;

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
