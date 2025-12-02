package pe.edu.upc.quizbee.iam.domain.model.commands;

/**
 * UpdateUserCommand
 * <p>
 *     Command to update a user's profile information.
 * </p>
 */
public record UpdateUserCommand(
        Long userId,
        String displayName,
        String bio,
        String avatar,
        String country,
        String language
) {
}
