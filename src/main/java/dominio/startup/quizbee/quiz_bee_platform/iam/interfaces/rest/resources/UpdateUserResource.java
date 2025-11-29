package dominio.startup.quizbee.quiz_bee_platform.iam.interfaces.rest.resources;

/**
 * UpdateUserResource
 * <p>
 *     Resource for updating user profile information.
 * </p>
 */
public record UpdateUserResource(
        String displayName,
        String bio,
        String avatar,
        String country,
        String language
) {
}
