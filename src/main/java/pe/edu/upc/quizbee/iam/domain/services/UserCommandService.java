package pe.edu.upc.quizbee.iam.domain.services;

import pe.edu.upc.quizbee.iam.domain.model.aggregates.User;
import pe.edu.upc.quizbee.iam.domain.model.commands.*;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;

public interface UserCommandService {
    Optional<ImmutablePair<User, String>> handle(SignInCommand command);
    Optional<User> handle(SignUpCommand command);
    Optional<User> handle(UpdateUserCommand command);
    Optional<User> handle(UpdateUserStatsCommand command);
    Optional<User> handle(UpdateUserSubscriptionCommand command);
    void handle(DeleteUserCommand command);
}