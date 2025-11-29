package dominio.startup.quizbee.quiz_bee_platform.iam.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.commands.DeleteUserCommand;
import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.commands.UpdateUserStatsCommand;
import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.commands.UpdateUserSubscriptionCommand;
import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.queries.GetAllUsersQuery;
import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.queries.GetUserByIdQuery;
import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.queries.GetUserByEmailQuery;
import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.queries.GetUserByEmailAndPasswordQuery;
import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.valueobjects.SubscriptionStatus;
import dominio.startup.quizbee.quiz_bee_platform.iam.domain.model.valueobjects.UserStats;
import dominio.startup.quizbee.quiz_bee_platform.iam.domain.services.UserCommandService;
import dominio.startup.quizbee.quiz_bee_platform.iam.domain.services.UserQueryService;
import dominio.startup.quizbee.quiz_bee_platform.iam.interfaces.rest.resources.UserResource;
import dominio.startup.quizbee.quiz_bee_platform.iam.interfaces.rest.resources.UpdateUserResource;
import dominio.startup.quizbee.quiz_bee_platform.iam.interfaces.rest.resources.PatchUserResource;
import dominio.startup.quizbee.quiz_bee_platform.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import dominio.startup.quizbee.quiz_bee_platform.iam.interfaces.rest.transform.UpdateUserCommandFromResourceAssembler;

/**
 * This class is a REST controller that exposes the users resource.
 * It includes the following operations:
 * - GET /api/v1/users: returns all the users
 * - GET /api/v1/users?email=&password=&rol=: login with query params
 * - GET /api/v1/users/{userId}: returns the user with the given id
 * - GET /api/v1/users/email/{email}: returns the user with the given email
 * - PUT /api/v1/users/{userId}: updates user profile
 * - PATCH /api/v1/users/{userId}: partially updates user (stats, subscription)
 * - DELETE /api/v1/users/{userId}: deletes a user
 **/
@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "User Management Endpoints")
public class UsersController {

    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;

    public UsersController(UserQueryService userQueryService, UserCommandService userCommandService) {
        this.userQueryService = userQueryService;
        this.userCommandService = userCommandService;
    }

    /**
     * This method returns all the users or performs login if email and password are provided.
     * 
     * @param email Optional email for login
     * @param password Optional password for login
     * @param rol Optional rol filter
     * @return a list of user resources or a single user if login credentials match
     * @see UserResource
     */
    @Operation(summary = "Get all users or login", 
               description = "Returns all users if no parameters provided. If email and password are provided, validates credentials and returns matching user.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResource.class)))
    })
    @GetMapping
    public ResponseEntity<List<UserResource>> getAllUsersOrLogin(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String rol) {
        
        // If email and password are provided, perform login
        if (email != null && password != null) {
            var query = new GetUserByEmailAndPasswordQuery(email, password);
            var user = userQueryService.handle(query);
            
            if (user.isEmpty()) {
                // Return empty list if credentials are invalid (matching frontend expectation)
                return ResponseEntity.ok(List.of());
            }
            
            // Filter by rol if provided
            if (rol != null && !rol.isEmpty()) {
                var hasRole = user.get().getRoles().stream()
                        .anyMatch(role -> role.getStringName().toLowerCase().contains(rol.toLowerCase()));
                if (!hasRole) {
                    return ResponseEntity.ok(List.of());
                }
            }
            
            var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
            return ResponseEntity.ok(List.of(userResource));
        }
        
        // Otherwise, return all users
        var getAllUsersQuery = new GetAllUsersQuery();
        var users = userQueryService.handle(getAllUsersQuery);
        var userResources = users.stream()
                .map(UserResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(userResources);
    }

    /**
     * This method returns the user with the given id.
     *
     * @param userId the user id.
     * @return the user resource with the given id
     * @throws RuntimeException if the user is not found
     * @see UserResource
     */
    @Operation(summary = "Get user by ID", description = "Returns a user by their unique identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResource.class))),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserResource> getUserById(@PathVariable Long userId) {
        var getUserByIdQuery = new GetUserByIdQuery(userId);
        var user = userQueryService.handle(getUserByIdQuery);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return ResponseEntity.ok(userResource);
    }

    /**
     * This method returns the user with the given email.
     *
     * @param email the user email.
     * @return the user resource with the given email
     * @see UserResource
     */
    @Operation(summary = "Get user by email", description = "Returns a user by their email address")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResource.class))),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping(value = "/email/{email}")
    public ResponseEntity<UserResource> getUserByEmail(@PathVariable String email) {
        var getUserByEmailQuery = new GetUserByEmailQuery(email);
        var user = userQueryService.handle(getUserByEmailQuery);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return ResponseEntity.ok(userResource);
    }

    /**
     * Updates user profile information
     *
     * @param userId the user id
     * @param resource the update user resource
     * @return the updated user resource
     */
    @Operation(summary = "Update user profile", description = "Updates complete user profile information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResource.class))),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping(value = "/{userId}")
    public ResponseEntity<UserResource> updateUser(
            @PathVariable Long userId,
            @RequestBody UpdateUserResource resource) {
        
        var updateUserCommand = UpdateUserCommandFromResourceAssembler.toCommandFromResource(userId, resource);
        var user = userCommandService.handle(updateUserCommand);
        
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return ResponseEntity.ok(userResource);
    }

    /**
     * Partially updates user (stats, subscription status)
     *
     * @param userId the user id
     * @param resource the patch user resource
     * @return the updated user resource
     */
    @Operation(summary = "Partially update user", description = "Updates user stats and/or subscription status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResource.class))),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PatchMapping(value = "/{userId}")
    public ResponseEntity<UserResource> patchUser(
            @PathVariable Long userId,
            @RequestBody PatchUserResource resource) {
        
        var user = userQueryService.handle(new GetUserByIdQuery(userId));
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        // Update stats if provided
        if (resource.stats() != null) {
            var stats = new UserStats(
                    resource.stats().lives(),
                    resource.stats().points(),
                    resource.stats().quizzesPlayed(),
                    resource.stats().quizzesWon(),
                    resource.stats().quizzesLost(),
                    resource.stats().currentStreak()
            );
            var updateStatsCommand = new UpdateUserStatsCommand(userId, stats);
            user = userCommandService.handle(updateStatsCommand);
        }
        
        // Update subscription if provided
        if (resource.subscriptionStatus() != null) {
            var status = SubscriptionStatus.valueOf(resource.subscriptionStatus().toUpperCase());
            var updateSubscriptionCommand = new UpdateUserSubscriptionCommand(
                    userId,
                    status,
                    resource.subscriptionExpiry()
            );
            user = userCommandService.handle(updateSubscriptionCommand);
        }
        
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return ResponseEntity.ok(userResource);
    }

    /**
     * Deletes a user
     *
     * @param userId the user id
     * @return no content
     */
    @Operation(summary = "Delete user", description = "Permanently deletes a user account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        var deleteUserCommand = new DeleteUserCommand(userId);
        userCommandService.handle(deleteUserCommand);
        return ResponseEntity.noContent().build();
    }
}
