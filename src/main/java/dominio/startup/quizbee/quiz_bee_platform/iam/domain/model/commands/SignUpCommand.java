package dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.commands;

import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.entities.Role;

import java.util.List;

public record SignUpCommand(String username, String email, String password, List<Role> roles) {
}