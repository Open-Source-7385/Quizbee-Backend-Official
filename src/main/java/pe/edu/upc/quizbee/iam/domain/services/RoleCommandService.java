package pe.edu.upc.quizbee.iam.domain.services;

import pe.edu.upc.quizbee.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
    void handle(SeedRolesCommand command);
}