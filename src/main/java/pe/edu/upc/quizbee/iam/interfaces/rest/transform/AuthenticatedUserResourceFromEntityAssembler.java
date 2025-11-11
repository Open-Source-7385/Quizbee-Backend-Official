package pe.edu.upc.quizbee.iam.interfaces.rest.transform;

import pe.edu.upc.quizbee.iam.domain.model.aggregates.User;
import pe.edu.upc.quizbee.iam.interfaces.rest.resources.AuthenticatedUserResource;

public class AuthenticatedUserResourceFromEntityAssembler {

    public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
        return new AuthenticatedUserResource(user.getId(), user.getUsername(), token);
    }
}