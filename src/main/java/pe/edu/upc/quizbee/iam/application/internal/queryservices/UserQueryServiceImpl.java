package pe.edu.upc.quizbee.iam.application.internal.queryservices;

import pe.edu.upc.quizbee.iam.application.internal.outboundservices.hashing.HashingService;
import pe.edu.upc.quizbee.iam.domain.model.aggregates.User;
import pe.edu.upc.quizbee.iam.domain.model.queries.*;
import pe.edu.upc.quizbee.iam.domain.services.UserQueryService;
import pe.edu.upc.quizbee.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link UserQueryService} interface.
 */
@Service
public class UserQueryServiceImpl implements UserQueryService {
    private final UserRepository userRepository;
    private final HashingService hashingService;

    /**
     * Constructor.
     *
     * @param userRepository {@link UserRepository} instance.
     * @param hashingService {@link HashingService} instance for password validation.
     */
    public UserQueryServiceImpl(UserRepository userRepository, HashingService hashingService) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
    }

    /**
     * This method is used to handle {@link GetAllUsersQuery} query.
     * @param query {@link GetAllUsersQuery} instance.
     * @return {@link List} of {@link User} instances.
     * @see GetAllUsersQuery
     */
    @Override
    public List<User> handle(GetAllUsersQuery query) {
        return userRepository.findAll();
    }

    /**
     * This method is used to handle {@link GetUserByIdQuery} query.
     * @param query {@link GetUserByIdQuery} instance.
     * @return {@link Optional} of {@link User} instance.
     * @see GetUserByIdQuery
     */
    @Override
    @SuppressWarnings("null")
    public Optional<User> handle(GetUserByIdQuery query) {
        return userRepository.findById(query.userId());
    }

    /**
     * This method is used to handle {@link GetUserByUsernameQuery} query.
     * @param query {@link GetUserByUsernameQuery} instance.
     * @return {@link Optional} of {@link User} instance.
     * @see GetUserByUsernameQuery
     */
    @Override
    public Optional<User> handle(GetUserByUsernameQuery query) {
        return userRepository.findByUsername(query.username());
    }

    /**
     * This method is used to handle {@link GetUserByEmailQuery} query.
     * @param query {@link GetUserByEmailQuery} instance.
     * @return {@link Optional} of {@link User} instance.
     * @see GetUserByEmailQuery
     */
    @Override
    public Optional<User> handle(GetUserByEmailQuery query) {
        return userRepository.findByEmail(query.email());
    }

    /**
     * This method is used to handle {@link GetUserByEmailAndPasswordQuery} query.
     * Used for authentication via query parameters.
     * @param query {@link GetUserByEmailAndPasswordQuery} instance.
     * @return {@link Optional} of {@link User} instance if credentials are valid.
     * @see GetUserByEmailAndPasswordQuery
     */
    @Override
    public Optional<User> handle(GetUserByEmailAndPasswordQuery query) {
        var user = userRepository.findByEmail(query.email());
        if (user.isEmpty()) {
            return Optional.empty();
        }
        // Validate password
        if (!hashingService.matches(query.password(), user.get().getPassword())) {
            return Optional.empty();
        }
        return user;
    }
}