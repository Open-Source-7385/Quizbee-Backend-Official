package pe.edu.upc.quizbee.subscriptions.interfaces.rest.transform;

import pe.edu.upc.quizbee.subscriptions.domain.model.aggregates.Invoice;
import pe.edu.upc.quizbee.subscriptions.interfaces.rest.resources.InvoiceResource;

public class InvoiceResourceFromEntityAssembler {
    
    public static InvoiceResource toResourceFromEntity(Invoice entity) {
        return new InvoiceResource(
            entity.getId(),
            entity.getInvoiceNumber(),
            entity.getSubscriptionId(),
            entity.getUserId(),
            entity.getDescription(),
            entity.getAmount().amount(),
            entity.getAmount().currency(),
            entity.getIssueDate(),
            entity.getStatus().name()
        );
    }
}