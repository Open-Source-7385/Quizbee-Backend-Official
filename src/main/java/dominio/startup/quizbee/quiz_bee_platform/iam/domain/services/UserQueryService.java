package dominio.startup.quizbee.quiz_bee_platform.iam.domain.services;

import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.aggregates.User;
import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.queries.GetAllUsersQuery;
import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.queries.GetUserByIdQuery;
import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.queries.GetUserByUsernameQuery;
import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.queries.GetUserByEmailQuery;
import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.queries.GetUserByEmailAndPasswordQuery;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {
    List<User> handle(GetAllUsersQuery query);
    Optional<User> handle(GetUserByIdQuery query);
    Optional<User> handle(GetUserByUsernameQuery query);
    Optional<User> handle(GetUserByEmailQuery query);
    Optional<User> handle(GetUserByEmailAndPasswordQuery query);
}