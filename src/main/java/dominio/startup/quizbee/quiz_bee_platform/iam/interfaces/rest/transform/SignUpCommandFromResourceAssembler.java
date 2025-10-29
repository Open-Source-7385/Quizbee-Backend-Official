package dominio.startup.quizbee.quiz_bee_platform.iam.interfaces.rest.transform;

import java.util.ArrayList;
import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.commands.SignUpCommand;
import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.entities.Role;
import dominio.startup.quizbee.quiz_bee_platform.iam.interfaces.rest.resources.SignUpResource;

public class SignUpCommandFromResourceAssembler {

    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        var roles = resource.roles() != null
                ? resource.roles().stream().map(name -> Role.toRoleFromName(name)).toList()
                : new ArrayList<Role>();
        return new SignUpCommand(resource.username(), resource.password(), roles);
    }
}