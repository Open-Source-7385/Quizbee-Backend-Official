package dominio.startup.quizbee.quiz_bee_platform.profiles.interfaces.rest.resources;

import java.util.Date;

/**
 * ProfileResource record
 * <p>
 *     This record represents a profile resource with extended fields.
 *     Can be used independently or merged with User data.
 * </p>
 */
public record ProfileResource(
        Long id,
        Long userId,
        String fullName,
        String email,
        String streetAddress,
        Date createdAt,
        Date updatedAt
) {
}
