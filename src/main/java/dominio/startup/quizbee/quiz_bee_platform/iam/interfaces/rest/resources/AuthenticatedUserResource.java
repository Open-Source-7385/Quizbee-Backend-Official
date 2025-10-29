package dominio.startup.quizbee.quiz_bee_platform.iam.interfaces.rest.resources;

public record AuthenticatedUserResource(Long id, String username, String token) {
}