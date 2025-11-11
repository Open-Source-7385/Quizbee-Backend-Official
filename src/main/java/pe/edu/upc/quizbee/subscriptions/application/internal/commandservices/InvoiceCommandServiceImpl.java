package pe.edu.upc.quizbee.subscriptions.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.quizbee.subscriptions.domain.model.aggregates.Invoice;
import pe.edu.upc.quizbee.subscriptions.domain.model.aggregates.Subscription;
import pe.edu.upc.quizbee.subscriptions.domain.model.commands.CreateInvoiceCommand;
import pe.edu.upc.quizbee.subscriptions.domain.services.InvoiceCommandService;
import pe.edu.upc.quizbee.subscriptions.infrastructure.persistence.jpa.repositories.InvoiceRepository;
import pe.edu.upc.quizbee.subscriptions.infrastructure.persistence.jpa.repositories.SubscriptionRepository;

@Service
public class InvoiceCommandServiceImpl implements InvoiceCommandService {

private final InvoiceRepository invoiceRepository;
private final SubscriptionRepository subscriptionRepository;

public InvoiceCommandServiceImpl(InvoiceRepository invoiceRepository,
    SubscriptionRepository subscriptionRepository) {
    this.invoiceRepository = invoiceRepository;
    this.subscriptionRepository = subscriptionRepository;
}

@Override
public Long handle(CreateInvoiceCommand command) {
    Subscription subscription = subscriptionRepository.findById(command.subscriptionId())
        .orElseThrow(() -> new IllegalArgumentException("Subscription not found with id: " + command.subscriptionId()));

    Invoice invoice = new Invoice(
        subscription.getId(),
        subscription.getUserId(),
        command.description(),
        subscription.getPrice()
    );

    invoice.markAsPaid(); // Auto-marcar como pagada
    invoiceRepository.save(invoice);
    return invoice.getId();
}
}