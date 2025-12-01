package pe.edu.upc.quizbee.iam.interfaces.rest.resources;

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
