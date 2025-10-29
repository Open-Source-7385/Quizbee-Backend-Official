package dominio.startup.quizbee.quiz_bee_platform.iam.domain.services;

import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.aggregates.User;
import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.queries.GetAllUsersQuery;
import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.queries.GetUserByIdQuery;
import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.queries.GetUserByUsernameQuery;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {
    List<User> handle(GetAllUsersQuery query);
    Optional<User> handle(GetUserByIdQuery query);
    Optional<User> handle(GetUserByUsernameQuery query);
}