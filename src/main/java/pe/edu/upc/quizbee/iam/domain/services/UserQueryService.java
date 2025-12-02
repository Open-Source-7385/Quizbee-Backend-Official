package pe.edu.upc.quizbee.iam.domain.services;

import pe.edu.upc.quizbee.iam.domain.model.aggregates.User;
import pe.edu.upc.quizbee.iam.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {
    List<User> handle(GetAllUsersQuery query);
    Optional<User> handle(GetUserByIdQuery query);
    Optional<User> handle(GetUserByUsernameQuery query);
    Optional<User> handle(GetUserByEmailQuery query);
    Optional<User> handle(GetUserByEmailAndPasswordQuery query);
}