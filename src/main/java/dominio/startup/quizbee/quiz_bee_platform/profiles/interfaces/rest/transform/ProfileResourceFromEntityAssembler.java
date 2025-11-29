package dominio.startup.quizbee.quiz_bee_platform.profiles.interfaces.rest.transform;

import dominio.startup.quizbee.quiz_bee_platform.profiles.domain.model.aggregates.Profile;
import dominio.startup.quizbee.quiz_bee_platform.profiles.interfaces.rest.resources.ProfileResource;

public class ProfileResourceFromEntityAssembler {
    public static ProfileResource toResourceFromEntity(Profile entity) {
        return new ProfileResource(
                entity.getId(),
                entity.getUserId(),
                entity.getFullName(),
                entity.getEmailAddress(),
                entity.getStreetAddress(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
