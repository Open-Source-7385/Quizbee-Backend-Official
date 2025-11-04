package pe.edu.upc.quizbee.iam.interfaces.rest.resources;

public record AuthenticatedUserResource(Long id, String username, String token) {
}