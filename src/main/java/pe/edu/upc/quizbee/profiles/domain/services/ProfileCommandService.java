package pe.edu.upc.quizbee.profiles.domain.services;

import pe.edu.upc.quizbee.profiles.domain.model.aggregates.Profile;
import pe.edu.upc.quizbee.profiles.domain.model.commands.CreateProfileCommand;

import java.util.Optional;

public interface ProfileCommandService {
    Optional<Profile> handle(CreateProfileCommand command);
}