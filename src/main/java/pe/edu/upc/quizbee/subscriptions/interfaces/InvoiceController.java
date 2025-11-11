package pe.edu.upc.quizbee.subscriptions.interfaces;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.quizbee.subscriptions.domain.model.commands.CreateInvoiceCommand;
import pe.edu.upc.quizbee.subscriptions.domain.model.queries.GetInvoiceByIdQuery;
import pe.edu.upc.quizbee.subscriptions.domain.model.queries.GetInvoicesBySubscriptionIdQuery;
import pe.edu.upc.quizbee.subscriptions.domain.services.InvoiceCommandService;
import pe.edu.upc.quizbee.subscriptions.domain.services.InvoiceQueryService;
import pe.edu.upc.quizbee.subscriptions.interfaces.rest.resources.InvoiceResource;
import pe.edu.upc.quizbee.subscriptions.interfaces.rest.transform.InvoiceResourceFromEntityAssembler;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/invoices", produces = "application/json")
@Tag(name = "Invoices", description = "Invoice Management Endpoints")
public class InvoiceController {

    private final InvoiceCommandService invoiceCommandService;
    private final InvoiceQueryService invoiceQueryService;

    public InvoiceController(InvoiceCommandService invoiceCommandService,
                            InvoiceQueryService invoiceQueryService) {
        this.invoiceCommandService = invoiceCommandService;
        this.invoiceQueryService = invoiceQueryService;
    }

    @PostMapping
    public ResponseEntity<Long> createInvoice(@RequestParam Long subscriptionId,
                                              @RequestParam String description) {
        var command = new CreateInvoiceCommand(subscriptionId, description);
        var invoiceId = invoiceCommandService.handle(command);
        return new ResponseEntity<>(invoiceId, HttpStatus.CREATED);
    }

    @GetMapping("/{invoiceId}")
    public ResponseEntity<InvoiceResource> getInvoiceById(@PathVariable Long invoiceId) {
        var query = new GetInvoiceByIdQuery(invoiceId);
        var invoice = invoiceQueryService.handle(query);
        if (invoice.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var resource = InvoiceResourceFromEntityAssembler.toResourceFromEntity(invoice.get());
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/subscription/{subscriptionId}")
    public ResponseEntity<List<InvoiceResource>> getInvoicesBySubscriptionId(@PathVariable Long subscriptionId) {
        var query = new GetInvoicesBySubscriptionIdQuery(subscriptionId);
        var invoices = invoiceQueryService.handle(query);
        var resources = invoices.stream()
                .map(InvoiceResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }
}