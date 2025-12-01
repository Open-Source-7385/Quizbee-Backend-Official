package pe.edu.upc.quizbee.iam.domain.model.queries;

/**
 * Query to get a user by email and password
 * Used for authentication via query parameters
 */
public record GetUserByEmailAndPasswordQuery(String email, String password) {
}
