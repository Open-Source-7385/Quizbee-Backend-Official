package pe.edu.upc.quizbee.iam.domain.model.queries;

import pe.edu.upc.quizbee.iam.domain.model.valueobjects.Roles;

public record GetRoleByNameQuery(Roles name) {
}