package pe.edu.upc.quizbee.iam.interfaces.rest.resources;

import java.util.Date;
import java.util.List;

/**
 * UserResource record
 * <p>
 *     This record represents a user resource with all fields expected by the frontend.
 *     Compatible with both IAM and Profile contexts.
 * </p>
 */
public record UserResource(
        Long id,
        String name,              // Maps to username
        String email,
        String avatar,
        String role,              // Primary role as string
        List<String> roles,       // All roles
        String displayName,
        String bio,
        String country,
        String language,
        List<String> languages,   // Available languages
        String currentLanguage,   // Current selected language
        UserStatsResource stats,
        String subscriptionStatus,
        Date subscriptionExpiry,
        Date createdAt,
        Date updatedAt
) {
    /**
     * Simplified constructor for basic user data
     */
    public UserResource(Long id, String name, String email, List<String> roles) {
        this(id, name, email, null, roles.isEmpty() ? "user" : roles.get(0), roles, 
             name, null, null, "English", List.of("English"), "English",
             new UserStatsResource(5, 0, 0, 0, 0, 0), "free", null, null, null);
    }
}
