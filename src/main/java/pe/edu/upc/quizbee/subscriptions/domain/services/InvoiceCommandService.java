package pe.edu.upc.quizbee.subscriptions.domain.services;

import pe.edu.upc.quizbee.subscriptions.domain.model.commands.CreateInvoiceCommand;

public interface InvoiceCommandService {
    Long handle(CreateInvoiceCommand command);
}