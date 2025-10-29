package dominio.startup.quizbee.quiz_bee_platform.profiles.interfaces.rest.transform;

import dominio.startup.quizbee.quiz_bee_platform.profiles.domain.model.commands.CreateProfileCommand;
import dominio.startup.quizbee.quiz_bee_platform.profiles.interfaces.rest.resources.CreateProfileResource;

public class CreateProfileCommandFromResourceAssembler {
    public static CreateProfileCommand toCommandFromResource(CreateProfileResource resource) {
        return new CreateProfileCommand(resource.firstName(), resource.lastName(),
                resource.email(), resource.street(), resource.number(), resource.city(),
                resource.postalCode(), resource.country());
    }
}