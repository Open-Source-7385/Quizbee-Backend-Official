package dominio.startup.quizbee.quiz_bee_platform.iam.interfaces.rest.transform;

import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.aggregates.User;
import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.entities.Role;
import dominio.startup.quizbee.quiz_bee_platform.iam.interfaces.rest.resources.UserResource;
import dominio.startup.quizbee.quiz_bee_platform.iam.interfaces.rest.resources.UserStatsResource;

import java.util.List;

public class UserResourceFromEntityAssembler {

    public static UserResource toResourceFromEntity(User user) {
        var roles = user.getRoles().stream()
                .map(Role::getStringName)
                .toList();
        
        var primaryRole = roles.isEmpty() ? "user" : roles.get(0).toLowerCase().replace("role_", "");
        
        var stats = new UserStatsResource(
                user.getStats() != null ? user.getStats().lives() : 5,
                user.getStats() != null ? user.getStats().points() : 0,
                user.getStats() != null ? user.getStats().quizzesPlayed() : 0,
                user.getStats() != null ? user.getStats().quizzesWon() : 0,
                user.getStats() != null ? user.getStats().quizzesLost() : 0,
                user.getStats() != null ? user.getStats().currentStreak() : 0
        );
        
        var languages = user.getLanguage() != null ? List.of(user.getLanguage()) : List.of("English");
        
        return new UserResource(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getAvatar(),
                primaryRole,
                roles,
                user.getDisplayName() != null ? user.getDisplayName() : user.getUsername(),
                user.getBio(),
                user.getCountry(),
                user.getLanguage() != null ? user.getLanguage() : "English",
                languages,
                user.getLanguage() != null ? user.getLanguage() : "English",
                stats,
                user.getSubscriptionStatus() != null ? user.getSubscriptionStatus().name().toLowerCase() : "free",
                user.getSubscriptionExpiry(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}