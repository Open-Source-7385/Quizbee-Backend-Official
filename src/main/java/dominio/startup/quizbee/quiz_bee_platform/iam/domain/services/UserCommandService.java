package dominio.startup.quizbee.quiz_bee_platform.iam.domain.services;

import org.apache.commons.lang3.tuple.ImmutablePair;
import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.aggregates.User;
import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.commands.SignInCommand;
import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.commands.SignUpCommand;
import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.commands.UpdateUserCommand;
import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.commands.UpdateUserStatsCommand;
import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.commands.UpdateUserSubscriptionCommand;
import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.commands.DeleteUserCommand;

import java.util.Optional;

public interface UserCommandService {
    Optional<ImmutablePair<User, String>> handle(SignInCommand command);
    Optional<User> handle(SignUpCommand command);
    Optional<User> handle(UpdateUserCommand command);
    Optional<User> handle(UpdateUserStatsCommand command);
    Optional<User> handle(UpdateUserSubscriptionCommand command);
    void handle(DeleteUserCommand command);
}