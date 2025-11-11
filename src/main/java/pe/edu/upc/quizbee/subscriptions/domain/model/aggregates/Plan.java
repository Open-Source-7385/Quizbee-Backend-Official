package pe.edu.upc.quizbee.subscriptions.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.quizbee.subscriptions.domain.model.valueobjects.Money;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Plan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String subtitle;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amount", column = @Column(name = "price_amount")),
        @AttributeOverride(name = "currency", column = @Column(name = "price_currency"))
    })
    private Money price;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "plan_features", joinColumns = @JoinColumn(name = "plan_id"))
    @Column(name = "feature")
    private List<String> features = new ArrayList<>();

    @Column(nullable = false)
    private Boolean popular = false;

    protected Plan() {
        // Required by JPA
    }

    public Plan(String name, String subtitle, Money price, List<String> features, Boolean popular) {
        this.name = name;
        this.subtitle = subtitle;
        this.price = price;
        this.features = features != null ? new ArrayList<>(features) : new ArrayList<>();
        this.popular = popular != null ? popular : false;
    }

    public Money calculatePrice() {
        return this.price;
    }

    public boolean isPopular() {
        return this.popular;
    }

    public boolean hasFeature(String feature) {
        return this.features.stream()
                .anyMatch(f -> f.equalsIgnoreCase(feature));
    }

    public void updatePrice(Money newPrice) {
        if (newPrice.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
        this.price = newPrice;
    }

    public void markAsPopular() {
        this.popular = true;
    }

    public void unmarkAsPopular() {
        this.popular = false;
    }
}