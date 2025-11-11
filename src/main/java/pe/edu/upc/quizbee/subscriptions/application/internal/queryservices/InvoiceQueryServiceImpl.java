package pe.edu.upc.quizbee.subscriptions.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.quizbee.subscriptions.domain.model.aggregates.Invoice;
import pe.edu.upc.quizbee.subscriptions.domain.model.queries.GetInvoiceByIdQuery;
import pe.edu.upc.quizbee.subscriptions.domain.model.queries.GetInvoicesBySubscriptionIdQuery;
import pe.edu.upc.quizbee.subscriptions.domain.services.InvoiceQueryService;
import pe.edu.upc.quizbee.subscriptions.infrastructure.persistence.jpa.repositories.InvoiceRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceQueryServiceImpl implements InvoiceQueryService {

private final InvoiceRepository invoiceRepository;

public InvoiceQueryServiceImpl(InvoiceRepository invoiceRepository) {
    this.invoiceRepository = invoiceRepository;
}

@Override
public Optional<Invoice> handle(GetInvoiceByIdQuery query) {
    return invoiceRepository.findById(query.invoiceId());
}

@Override
public List<Invoice> handle(GetInvoicesBySubscriptionIdQuery query) {
    return invoiceRepository.findBySubscriptionId(query.subscriptionId());
}
}