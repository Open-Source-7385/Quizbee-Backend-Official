package dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.queries;

import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.valueobjects.Roles;

public record GetRoleByNameQuery(Roles name) {
}