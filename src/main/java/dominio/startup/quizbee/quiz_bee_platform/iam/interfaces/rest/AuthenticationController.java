package dominio.startup.quizbee.quiz_bee_platform.iam.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import dominio.startup.quizbee.quiz_bee_platform.iam.domain.services.UserCommandService;
import dominio.startup.quizbee.quiz_bee_platform.iam.interfaces.rest.resources.AuthenticatedUserResource;
import dominio.startup.quizbee.quiz_bee_platform.iam.interfaces.rest.resources.SignInResource;
import dominio.startup.quizbee.quiz_bee_platform.iam.interfaces.rest.resources.SignUpResource;
import dominio.startup.quizbee.quiz_bee_platform.iam.interfaces.rest.resources.UserResource;
import dominio.startup.quizbee.quiz_bee_platform.iam.interfaces.rest.transform.AuthenticatedUserResourceFromEntityAssembler;
import dominio.startup.quizbee.quiz_bee_platform.iam.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import dominio.startup.quizbee.quiz_bee_platform.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import dominio.startup.quizbee.quiz_bee_platform.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;

/**
 * AuthenticationController
 * <p>
 *     This controller is responsible for handling authentication requests.
 *     It exposes two endpoints:
 *     <ul>
 *         <li>POST /api/v1/auth/sign-in</li>
 *         <li>POST /api/v1/auth/sign-up</li>
 *     </ul>
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Authentication Endpoints")
public class AuthenticationController {

    private final UserCommandService userCommandService;

    public AuthenticationController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    /**
     * Handles the sign-in request.
     * @param signInResource the sign-in request body.
     * @return the authenticated user resource.
     */
    @Operation(summary = "Sign in", description = "Authenticates a user and returns a JWT token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Authentication successful",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticatedUserResource.class))),
        @ApiResponse(responseCode = "404", description = "Invalid credentials")
    })
    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticatedUserResource> signIn(
            @RequestBody SignInResource signInResource) {

        var signInCommand = SignInCommandFromResourceAssembler
                .toCommandFromResource(signInResource);
        var authenticatedUser = userCommandService.handle(signInCommand);
        if (authenticatedUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var authenticatedUserResource = AuthenticatedUserResourceFromEntityAssembler
                .toResourceFromEntity(
                        authenticatedUser.get().getLeft(), authenticatedUser.get().getRight());
        return ResponseEntity.ok(authenticatedUserResource);
    }

    /**
     * Handles the sign-up request.
     * @param signUpResource the sign-up request body.
     * @return the created user resource.
     */
    @Operation(summary = "Sign up", description = "Registers a new user account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResource.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input or user already exists")
    })
    @PostMapping("/sign-up")
    public ResponseEntity<UserResource> signUp(@RequestBody SignUpResource signUpResource) {
        var signUpCommand = SignUpCommandFromResourceAssembler
                .toCommandFromResource(signUpResource);
        var user = userCommandService.handle(signUpCommand);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return new ResponseEntity<>(userResource, HttpStatus.CREATED);
    }
}