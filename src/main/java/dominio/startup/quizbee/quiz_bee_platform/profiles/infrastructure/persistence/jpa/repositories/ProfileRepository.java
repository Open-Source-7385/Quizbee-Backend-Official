package dominio.startup.quizbee.quiz_bee_platform.profiles.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dominio.startup.quizbee.quiz_bee_platform.profiles.domain.model.aggregates.Profile;
import dominio.startup.quizbee.quiz_bee_platform.profiles.domain.model.valueobjects.EmailAddress;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByEmail(EmailAddress emailAddress);
}