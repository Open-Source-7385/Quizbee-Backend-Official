package dominio.startup.quizbee.quiz_bee_platform.profiles.domain.model.aggregates;

import jakarta.persistence.*;
import dominio.startup.quizbee.quiz_bee_platform.profiles.domain.model.commands.CreateProfileCommand;
import dominio.startup.quizbee.quiz_bee_platform.profiles.domain.model.valueobjects.EmailAddress;
import dominio.startup.quizbee.quiz_bee_platform.profiles.domain.model.valueobjects.PersonName;
import dominio.startup.quizbee.quiz_bee_platform.profiles.domain.model.valueobjects.StreetAddress;
import dominio.startup.quizbee.quiz_bee_platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
public class Profile extends AuditableAbstractAggregateRoot<Profile> {

    @Column(nullable = true)
    private Long userId; // Foreign key to User in IAM context

    @Embedded
    private PersonName name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address", column = @Column(name = "email_address"))})
    EmailAddress email;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "address_street")),
            @AttributeOverride(name = "number", column = @Column(name = "address_number")),
            @AttributeOverride(name = "city", column = @Column(name = "address_city")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "address_postal_code")),
            @AttributeOverride(name = "country", column = @Column(name = "address_country"))})
    private StreetAddress address;

    public Profile(String firstName, String lastName, String email, String street, String number, String city, String postalCode, String country) {
        this.name = new PersonName(firstName, lastName);
        this.email = new EmailAddress(email);
        this.address = new StreetAddress(street, number, city, postalCode, country);
        this.userId = null;
    }

    public Profile(CreateProfileCommand command) {
        this.name = new PersonName(command.firstName(), command.lastName());
        this.email = new EmailAddress(command.email());
        this.address = new StreetAddress(command.street(), command.number(), command.city(), command.postalCode(), command.country());
        this.userId = null;
    }

    public Profile(Long userId, String firstName, String lastName, String email, String street, String number, String city, String postalCode, String country) {
        this(firstName, lastName, email, street, number, city, postalCode, country);
        this.userId = userId;
    }

    public Profile() {
        this.userId = null;
    }

    public void updateName(String firstName, String lastName) {
        this.name = new PersonName(firstName, lastName);
    }

    public void updateEmail(String email) {
        this.email = new EmailAddress(email);
    }

    public void updateAddress(String street, String number, String city, String postalCode, String country) {
        this.address = new StreetAddress(street, number, city, postalCode, country);
    }

    public String getFullName() {
        return name.getFullName();
    }

    public String getEmailAddress() {
        return email.address();
    }

    public String getStreetAddress() {
        return address.getStreetAddress();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}