package dominio.startup.quizbee.quiz_bee_platform.profiles.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dominio.startup.quizbee.quiz_bee_platform.profiles.domain.model.queries.GetAllProfilesQuery;
import dominio.startup.quizbee.quiz_bee_platform.profiles.domain.model.queries.GetProfileByIdQuery;
import dominio.startup.quizbee.quiz_bee_platform.profiles.domain.services.ProfileCommandService;
import dominio.startup.quizbee.quiz_bee_platform.profiles.domain.services.ProfileQueryService;
import dominio.startup.quizbee.quiz_bee_platform.profiles.interfaces.rest.resources.CreateProfileResource;
import dominio.startup.quizbee.quiz_bee_platform.profiles.interfaces.rest.resources.ProfileResource;
import dominio.startup.quizbee.quiz_bee_platform.profiles.interfaces.rest.transform.CreateProfileCommandFromResourceAssembler;
import dominio.startup.quizbee.quiz_bee_platform.profiles.interfaces.rest.transform.ProfileResourceFromEntityAssembler;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ProfilesController
 * <p>
 *     This class is the entry point for all the REST endpoints related to the Profile entity.
 * </p>
 */

@RestController
@RequestMapping(value = "/api/v1/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Profiles", description = "Profile Management Endpoints")
public class ProfilesController {
    private final ProfileQueryService profileQueryService;
    private final ProfileCommandService profileCommandService;

    public ProfilesController(ProfileQueryService profileQueryService,
                              ProfileCommandService profileCommandService) {

        this.profileQueryService = profileQueryService;
        this.profileCommandService = profileCommandService;
    }

    /**
     * Creates a new Profile
     * @param resource the resource containing the data to create the Profile
     * @return the created Profile
     */
    @Operation(summary = "Create new profile", description = "Creates a new user profile with personal information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Profile created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileResource.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<ProfileResource> createProfile(
            @RequestBody CreateProfileResource resource) {

        var createProfileCommand = CreateProfileCommandFromResourceAssembler
                .toCommandFromResource(resource);
        var profile = profileCommandService.handle(createProfileCommand);
        if (profile.isEmpty())
            return ResponseEntity.badRequest().build();
        var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profile.get());
        return new ResponseEntity<>(profileResource, HttpStatus.CREATED);
    }

    /**
     * Gets a Profile by its id
     * @param profileId the id of the Profile to get
     * @return the Profile resource associated to given Profile id
     */
    @Operation(summary = "Get profile by ID", description = "Returns a profile by its unique identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Profile found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileResource.class))),
        @ApiResponse(responseCode = "400", description = "Profile not found")
    })
    @GetMapping("/{profileId}")
    public ResponseEntity<ProfileResource> getProfileById(@PathVariable Long profileId) {
        var getProfileByIdQuery = new GetProfileByIdQuery(profileId);
        var profile = profileQueryService.handle(getProfileByIdQuery);
        if (profile.isEmpty())
            return ResponseEntity.badRequest().build();
        var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profile.get());
        return ResponseEntity.ok(profileResource);
    }

    /**
     * Gets all the Profiles
     * @return a list of all the Profile resources currently stored
     */
    @Operation(summary = "Get all profiles", description = "Returns a list of all profiles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileResource.class)))
    })
    @GetMapping
    public ResponseEntity<List<ProfileResource>> getAllProfiles() {
        var getAllProfilesQuery = new GetAllProfilesQuery();
        var profiles = profileQueryService.handle(getAllProfilesQuery);
        var profileResources = profiles.stream()
                .map(ProfileResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(profileResources);
    }
}