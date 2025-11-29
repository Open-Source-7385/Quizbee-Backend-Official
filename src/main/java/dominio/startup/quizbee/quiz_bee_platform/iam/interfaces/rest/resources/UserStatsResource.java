package dominio.startup.quizbee.quiz_bee_platform.iam.interfaces.rest.resources;

/**
 * UserStatsResource record for embedding user statistics
 */
public record UserStatsResource(
        int lives,
        int points,
        int quizzesPlayed,
        int quizzesWon,
        int quizzesLost,
        int currentStreak
) {
}
