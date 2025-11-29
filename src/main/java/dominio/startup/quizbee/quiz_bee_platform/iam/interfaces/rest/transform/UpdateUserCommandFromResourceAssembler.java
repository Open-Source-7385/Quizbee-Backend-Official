package dominio.startup.quizbee.quiz_bee_platform.iam.interfaces.rest.transform;

import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.commands.UpdateUserCommand;
import dominio.startup.quizbee.quiz_bee_platform.iam.interfaces.rest.resources.UpdateUserResource;

public class UpdateUserCommandFromResourceAssembler {
    public static UpdateUserCommand toCommandFromResource(Long userId, UpdateUserResource resource) {
        return new UpdateUserCommand(
                userId,
                resource.displayName(),
                resource.bio(),
                resource.avatar(),
                resource.country(),
                resource.language()
        );
    }
}
