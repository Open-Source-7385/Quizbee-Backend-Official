package dominio.startup.quizbee.quiz_bee_platform.profiles.domain.services;

import dominio.startup.quizbee.quiz_bee_platform.profiles.domain.model.aggregates.Profile;
import dominio.startup.quizbee.quiz_bee_platform.profiles.domain.model.queries.GetAllProfilesQuery;
import dominio.startup.quizbee.quiz_bee_platform.profiles.domain.model.queries.GetProfileByEmailQuery;
import dominio.startup.quizbee.quiz_bee_platform.profiles.domain.model.queries.GetProfileByIdQuery;

import java.util.List;
import java.util.Optional;

public interface ProfileQueryService {
    Optional<Profile> handle(GetProfileByEmailQuery query);
    Optional<Profile> handle(GetProfileByIdQuery query);
    List<Profile> handle(GetAllProfilesQuery query);
}