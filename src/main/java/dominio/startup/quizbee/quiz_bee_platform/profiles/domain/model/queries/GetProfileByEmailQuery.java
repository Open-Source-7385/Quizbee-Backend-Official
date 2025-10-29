package dominio.startup.quizbee.quiz_bee_platform.profiles.domain.model.queries;

import dominio.startup.quizbee.quiz_bee_platform.profiles.domain.model.valueobjects.EmailAddress;

public record GetProfileByEmailQuery(EmailAddress emailAddress) {
}