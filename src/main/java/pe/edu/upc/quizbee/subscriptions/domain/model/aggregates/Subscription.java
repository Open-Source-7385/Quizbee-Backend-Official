package pe.edu.upc.quizbee.subscriptions.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.quizbee.subscriptions.domain.model.valueobjects.Money;
import pe.edu.upc.quizbee.subscriptions.domain.model.valueobjects.PaymentMethod;
import pe.edu.upc.quizbee.subscriptions.domain.model.valueobjects.SubscriptionStatus;

import java.time.LocalDateTime;

@Entity
@Getter
public class Subscription {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private Long planId;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amount", column = @Column(name = "price_amount")),
        @AttributeOverride(name = "currency", column = @Column(name = "price_currency"))
    })
    private Money price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionStatus status;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime nextBillingDate;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "type", column = @Column(name = "payment_type")),
        @AttributeOverride(name = "lastFourDigits", column = @Column(name = "payment_last_digits")),
        @AttributeOverride(name = "expiryDate", column = @Column(name = "payment_expiry"))
    })
    private PaymentMethod paymentMethod;

    protected Subscription() {
        // Required by JPA
    }

    public Subscription(String userId, Long planId, Money price, PaymentMethod paymentMethod) {
        this.userId = userId;
        this.planId = planId;
        this.price = price;
        this.paymentMethod = paymentMethod;
        this.status = SubscriptionStatus.ACTIVE;
        this.startDate = LocalDateTime.now();
        this.nextBillingDate = LocalDateTime.now().plusMonths(1);
    }

    public void changePlan(Long newPlanId, Money newPrice) {
        if (!isActive()) {
            throw new IllegalStateException("Cannot change plan of inactive subscription");
        }
        this.planId = newPlanId;
        this.price = newPrice;
    }

    public void cancel() {
        if (this.status == SubscriptionStatus.CANCELLED) {
            throw new IllegalStateException("Subscription is already cancelled");
        }
        this.status = SubscriptionStatus.CANCELLED;
    }

    public void updatePaymentMethod(PaymentMethod newPaymentMethod) {
        if (!isActive()) {
            throw new IllegalStateException("Cannot update payment method of inactive subscription");
        }
        this.paymentMethod = newPaymentMethod;
    }

    public void renew() {
        if (this.status != SubscriptionStatus.EXPIRED) {
            throw new IllegalStateException("Only expired subscriptions can be renewed");
        }
        this.status = SubscriptionStatus.ACTIVE;
        this.nextBillingDate = LocalDateTime.now().plusMonths(1);
    }

    public boolean isActive() {
        return this.status == SubscriptionStatus.ACTIVE;
    }

    public void expire() {
        this.status = SubscriptionStatus.EXPIRED;
    }
}