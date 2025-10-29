package dominio.startup.quizbee.quiz_bee_platform.iam.domain.services;

import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.entities.Role;
import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.queries.GetAllRolesQuery;
import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.queries.GetRoleByNameQuery;

import java.util.List;
import java.util.Optional;

public interface RoleQueryService {
    List<Role> handle(GetAllRolesQuery query);
    Optional<Role> handle(GetRoleByNameQuery query);
}