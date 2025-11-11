package pe.edu.upc.quizbee.subscriptions.domain.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.quizbee.subscriptions.domain.model.valueobjects.Money;

import java.time.LocalDateTime;

@Entity
@Getter
public class Payment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long subscriptionId;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amount", column = @Column(name = "amount_value")),
        @AttributeOverride(name = "currency", column = @Column(name = "amount_currency"))
    })
    private Money amount;

    @Column(nullable = false)
    private String status; // PENDING, COMPLETED, FAILED, REFUNDED

    @Column(nullable = false)
    private LocalDateTime paymentDate;

    @Column(nullable = false)
    private String paymentMethodType;

    protected Payment() {
        // Required by JPA
    }

    public Payment(Long subscriptionId, Money amount, String paymentMethodType) {
        this.subscriptionId = subscriptionId;
        this.amount = amount;
        this.paymentMethodType = paymentMethodType;
        this.status = "PENDING";
        this.paymentDate = LocalDateTime.now();
    }

    public void process() {
        if (!"PENDING".equals(this.status)) {
            throw new IllegalStateException("Only pending payments can be processed");
        }
        // Simular procesamiento de pago
        this.status = "COMPLETED";
    }

    public void fail() {
        if (!"PENDING".equals(this.status)) {
            throw new IllegalStateException("Only pending payments can be marked as failed");
        }
        this.status = "FAILED";
    }

    public void refund() {
        if (!"COMPLETED".equals(this.status)) {
            throw new IllegalStateException("Only completed payments can be refunded");
        }
        this.status = "REFUNDED";
    }

    public boolean isSuccessful() {
        return "COMPLETED".equals(this.status);
    }

    public boolean isPending() {
        return "PENDING".equals(this.status);
    }

    public boolean isFailed() {
        return "FAILED".equals(this.status);
    }
}