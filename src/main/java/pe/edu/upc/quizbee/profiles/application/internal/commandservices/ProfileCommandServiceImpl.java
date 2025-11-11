package pe.edu.upc.quizbee.profiles.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.quizbee.profiles.domain.model.aggregates.Profile;
import pe.edu.upc.quizbee.profiles.domain.model.commands.CreateProfileCommand;
import pe.edu.upc.quizbee.profiles.domain.model.valueobjects.EmailAddress;
import pe.edu.upc.quizbee.profiles.domain.services.ProfileCommandService;
import pe.edu.upc.quizbee.profiles.infrastructure.persistence.jpa.repositories.ProfileRepository;

import java.util.Optional;

@Service
public class ProfileCommandServiceImpl implements ProfileCommandService {
    private final ProfileRepository profileRepository;

    public ProfileCommandServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Optional<Profile> handle(CreateProfileCommand command) {
        var emailAddress = new EmailAddress(command.email());
        profileRepository.findByEmail(emailAddress).map(profile -> {
            throw new IllegalArgumentException("Profile with email " + command.email() + " already exists");
        });
        var profile = new Profile(command);
        profileRepository.save(profile);
        return Optional.of(profile);
    }
}