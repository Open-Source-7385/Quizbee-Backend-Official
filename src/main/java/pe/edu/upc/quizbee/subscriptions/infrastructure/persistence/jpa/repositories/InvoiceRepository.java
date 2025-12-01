package pe.edu.upc.quizbee.subscriptions.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.quizbee.subscriptions.domain.model.aggregates.Invoice;
import pe.edu.upc.quizbee.subscriptions.domain.model.valueobjects.InvoiceStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findBySubscriptionId(Long subscriptionId);
    List<Invoice> findByUserId(String userId);
    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);
    List<Invoice> findByStatus(InvoiceStatus status);
    List<Invoice> findByUserIdAndStatus(String userId, InvoiceStatus status);
}