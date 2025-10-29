package dominio.startup.quizbee.quiz_bee_platform.iam.domain.services;

import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
    void handle(SeedRolesCommand command);
}