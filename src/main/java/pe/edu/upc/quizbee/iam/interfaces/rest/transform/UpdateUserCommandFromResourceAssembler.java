package pe.edu.upc.quizbee.iam.interfaces.rest.transform;

import pe.edu.upc.quizbee.iam.domain.model.commands.UpdateUserCommand;
import pe.edu.upc.quizbee.iam.interfaces.rest.resources.UpdateUserResource;

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
