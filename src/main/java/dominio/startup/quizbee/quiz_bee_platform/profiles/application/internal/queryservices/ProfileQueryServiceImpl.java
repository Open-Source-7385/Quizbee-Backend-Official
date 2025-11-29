package dominio.startup.quizbee.quiz_bee_platform.profiles.application.internal.queryservices;

import org.springframework.stereotype.Service;
import dominio.startup.quizbee.quiz_bee_platform.profiles.domain.model.aggregates.Profile;
import dominio.startup.quizbee.quiz_bee_platform.profiles.domain.model.queries.GetAllProfilesQuery;
import dominio.startup.quizbee.quiz_bee_platform.profiles.domain.model.queries.GetProfileByEmailQuery;
import dominio.startup.quizbee.quiz_bee_platform.profiles.domain.model.queries.GetProfileByIdQuery;
import dominio.startup.quizbee.quiz_bee_platform.profiles.domain.services.ProfileQueryService;
import dominio.startup.quizbee.quiz_bee_platform.profiles.infrastructure.persistence.jpa.repositories.ProfileRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileQueryServiceImpl implements ProfileQueryService {
    private final ProfileRepository profileRepository;

    public ProfileQueryServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Optional<Profile> handle(GetProfileByEmailQuery query) {
        return profileRepository.findByEmail(query.emailAddress());
    }

    @Override
    @SuppressWarnings("null")
    public Optional<Profile> handle(GetProfileByIdQuery query) {
        return profileRepository.findById(query.profileId());
    }

    @Override
    public List<Profile> handle(GetAllProfilesQuery query) {
        return profileRepository.findAll();
    }
}
