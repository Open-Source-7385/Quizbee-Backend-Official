package pe.edu.upc.quizbee.subscriptions.domain.services;

import pe.edu.upc.quizbee.subscriptions.domain.model.aggregates.Invoice;
import pe.edu.upc.quizbee.subscriptions.domain.model.queries.GetInvoiceByIdQuery;
import pe.edu.upc.quizbee.subscriptions.domain.model.queries.GetInvoicesBySubscriptionIdQuery;

import java.util.List;
import java.util.Optional;

public interface InvoiceQueryService {
    Optional<Invoice> handle(GetInvoiceByIdQuery query);
    List<Invoice> handle(GetInvoicesBySubscriptionIdQuery query);
}