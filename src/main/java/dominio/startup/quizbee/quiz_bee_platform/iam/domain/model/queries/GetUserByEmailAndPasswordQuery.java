package dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.queries;

/**
 * Query to get a user by email and password
 * Used for authentication via query parameters
 */
public record GetUserByEmailAndPasswordQuery(String email, String password) {
}
