package pe.edu.upc.quizbee.subscriptions.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.quizbee.subscriptions.domain.model.valueobjects.InvoiceStatus;
import pe.edu.upc.quizbee.subscriptions.domain.model.valueobjects.Money;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
public class Invoice {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String invoiceNumber;

    @Column(nullable = false)
    private Long subscriptionId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String description;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amount", column = @Column(name = "amount_value")),
        @AttributeOverride(name = "currency", column = @Column(name = "amount_currency"))
    })
    private Money amount;

    @Column(nullable = false)
    private LocalDateTime issueDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvoiceStatus status;

    protected Invoice() {
        // Required by JPA
    }

    public Invoice(Long subscriptionId, String userId, String description, Money amount) {
        this.subscriptionId = subscriptionId;
        this.userId = userId;
        this.description = description;
        this.amount = amount;
        this.issueDate = LocalDateTime.now();
        this.status = InvoiceStatus.PENDING;
        this.invoiceNumber = generateInvoiceNumber();
    }

    private String generateInvoiceNumber() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String date = this.issueDate.format(formatter);
        return "INV-" + date + "-" + System.currentTimeMillis();
    }

    public Money calculateTotal() {
        // En el futuro se pueden agregar impuestos, descuentos, etc.
        return this.amount;
    }

    public void markAsPaid() {
        if (this.status == InvoiceStatus.PAID) {
            throw new IllegalStateException("Invoice is already paid");
        }
        if (this.status == InvoiceStatus.CANCELLED) {
            throw new IllegalStateException("Cannot mark cancelled invoice as paid");
        }
        this.status = InvoiceStatus.PAID;
    }

    public void cancel() {
        if (this.status == InvoiceStatus.PAID) {
            throw new IllegalStateException("Cannot cancel paid invoice");
        }
        this.status = InvoiceStatus.CANCELLED;
    }

    public boolean isPaid() {
        return this.status == InvoiceStatus.PAID;
    }

    public byte[] generatePDF() {
        // TODO: Implementar generación de PDF
        // Por ahora retorna array vacío
        return new byte[0];
    }
}