package pe.edu.upc.quizbee.profiles.domain.model.queries;

import pe.edu.upc.quizbee.profiles.domain.model.valueobjects.EmailAddress;

public record GetProfileByEmailQuery(EmailAddress emailAddress) {
}