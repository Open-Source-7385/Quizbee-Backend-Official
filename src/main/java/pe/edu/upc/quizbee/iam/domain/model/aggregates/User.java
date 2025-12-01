package pe.edu.upc.quizbee.iam.domain.model.aggregates;

import pe.edu.upc.quizbee.iam.domain.model.entities.Role;
import pe.edu.upc.quizbee.iam.domain.model.valueobjects.SubscriptionStatus;
import pe.edu.upc.quizbee.iam.domain.model.valueobjects.UserStats;
import pe.edu.upc.quizbee.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User aggregate root
 * This class represents the aggregate root for the User entity.
 *
 * @see AuditableAbstractAggregateRoot
 */
@Getter
@Setter
@Entity
public class User extends AuditableAbstractAggregateRoot<User> {

    @NotBlank
    @Size(max = 50)
    @Column(unique = true)
    private String username;

    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @Column(length = 500)
    private String avatar;

    @Column(length = 100)
    private String displayName;

    @Column(length = 500)
    private String bio;

    @Column(length = 100)
    private String country;

    @Column(length = 50)
    private String language;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "lives", column = @Column(name = "stats_lives")),
            @AttributeOverride(name = "points", column = @Column(name = "stats_points")),
            @AttributeOverride(name = "quizzesPlayed", column = @Column(name = "stats_quizzes_played")),
            @AttributeOverride(name = "quizzesWon", column = @Column(name = "stats_quizzes_won")),
            @AttributeOverride(name = "quizzesLost", column = @Column(name = "stats_quizzes_lost")),
            @AttributeOverride(name = "currentStreak", column = @Column(name = "stats_current_streak"))
    })
    private UserStats stats;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private SubscriptionStatus subscriptionStatus;

    private Date subscriptionExpiry;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {
        this.roles = new HashSet<>();
        this.stats = new UserStats();
        this.subscriptionStatus = SubscriptionStatus.FREE;
        this.language = "English";
    }

    public User(String username, String email, String password) {
        this();
        this.username = username;
        this.email = email;
        this.password = password;
        this.displayName = username; // Default displayName to username
    }

    public User(String username, String email, String password, List<Role> roles) {
        this(username, email, password);
        addRoles(roles);
    }

    /**
     * Add a role to the user
     * @param role the role to add
     * @return the user with the added role
     */
    public User addRole(Role role) {
        this.roles.add(role);
        return this;
    }

    /**
     * Add a list of roles to the user
     * @param roles the list of roles to add
     * @return the user with the added roles
     */
    public User addRoles(List<Role> roles) {
        var validatedRoleSet = Role.validateRoleSet(roles);
        this.roles.addAll(validatedRoleSet);
        return this;
    }

    /**
     * Update user stats
     * @param newStats the new stats
     * @return the user with updated stats
     */
    public User updateStats(UserStats newStats) {
        this.stats = newStats;
        return this;
    }

    /**
     * Update subscription status
     * @param status the new subscription status
     * @param expiry the subscription expiry date
     * @return the user with updated subscription
     */
    public User updateSubscription(SubscriptionStatus status, Date expiry) {
        this.subscriptionStatus = status;
        this.subscriptionExpiry = expiry;
        return this;
    }

    /**
     * Update profile information
     * @param displayName the display name
     * @param bio the biography
     * @param avatar the avatar URL
     * @param country the country
     * @param language the language
     * @return the user with updated profile
     */
    public User updateProfile(String displayName, String bio, String avatar, String country, String language) {
        if (displayName != null) this.displayName = displayName;
        if (bio != null) this.bio = bio;
        if (avatar != null) this.avatar = avatar;
        if (country != null) this.country = country;
        if (language != null) this.language = language;
        return this;
    }
}