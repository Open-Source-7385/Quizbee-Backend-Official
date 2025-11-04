package pe.edu.upc.quizbee.profiles.interfaces.rest.transform;

import pe.edu.upc.quizbee.profiles.domain.model.aggregates.Profile;
import pe.edu.upc.quizbee.profiles.interfaces.rest.resources.ProfileResource;

public class ProfileResourceFromEntityAssembler {
    public static ProfileResource toResourceFromEntity(Profile entity) {
        return new ProfileResource(entity.getId(), entity.getEmailAddress(),
                entity.getFullName(), entity.getStreetAddress());
    }
}
