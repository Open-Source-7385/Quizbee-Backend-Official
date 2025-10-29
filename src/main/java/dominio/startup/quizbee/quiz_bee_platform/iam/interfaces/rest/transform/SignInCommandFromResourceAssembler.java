package dominio.startup.quizbee.quiz_bee_platform.iam.interfaces.rest.transform;

import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.commands.SignInCommand;
import dominio.startup.quizbee.quiz_bee_platform.iam.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {

    public static SignInCommand toCommandFromResource(SignInResource signInResource) {
        return new SignInCommand(signInResource.username(), signInResource.password());
    }
}