package dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * UserStats value object
 * <p>
 *     This class represents the statistics of a user in the system.
 *     It includes lives, points, quizzes played, won, lost, and current streak.
 * </p>
 */
@Embeddable
public record UserStats(
        int lives,
        int points,
        int quizzesPlayed,
        int quizzesWon,
        int quizzesLost,
        int currentStreak
) {
    /**
     * Default constructor for UserStats
     * Initializes with default values for a new user
     */
    public UserStats() {
        this(5, 0, 0, 0, 0, 0);
    }

    /**
     * Validates the UserStats
     * @throws IllegalArgumentException if any value is negative
     */
    public UserStats {
        if (lives < 0 || points < 0 || quizzesPlayed < 0 || quizzesWon < 0 || quizzesLost < 0 || currentStreak < 0) {
            throw new IllegalArgumentException("UserStats values cannot be negative");
        }
    }

    /**
     * Creates a new UserStats with updated lives
     * @param newLives the new number of lives
     * @return a new UserStats instance
     */
    public UserStats withLives(int newLives) {
        return new UserStats(newLives, points, quizzesPlayed, quizzesWon, quizzesLost, currentStreak);
    }

    /**
     * Creates a new UserStats with updated points
     * @param additionalPoints the points to add
     * @return a new UserStats instance
     */
    public UserStats addPoints(int additionalPoints) {
        return new UserStats(lives, points + additionalPoints, quizzesPlayed, quizzesWon, quizzesLost, currentStreak);
    }

    /**
     * Records a quiz completion
     * @param won whether the quiz was won
     * @param earnedPoints points earned
     * @return a new UserStats instance
     */
    public UserStats recordQuizCompletion(boolean won, int earnedPoints) {
        return new UserStats(
                lives,
                points + earnedPoints,
                quizzesPlayed + 1,
                won ? quizzesWon + 1 : quizzesWon,
                won ? quizzesLost : quizzesLost + 1,
                won ? currentStreak + 1 : 0
        );
    }
}
