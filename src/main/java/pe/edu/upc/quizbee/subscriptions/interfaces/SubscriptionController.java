package pe.edu.upc.quizbee.subscriptions.interfaces;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.quizbee.subscriptions.domain.model.commands.CancelSubscriptionCommand;
import pe.edu.upc.quizbee.subscriptions.domain.model.commands.ChangePlanCommand;
import pe.edu.upc.quizbee.subscriptions.domain.model.commands.UpdatePaymentMethodCommand;
import pe.edu.upc.quizbee.subscriptions.domain.model.queries.GetSubscriptionByIdQuery;
import pe.edu.upc.quizbee.subscriptions.domain.model.queries.GetSubscriptionByUserIdQuery;
import pe.edu.upc.quizbee.subscriptions.domain.model.valueobjects.PaymentMethod;
import pe.edu.upc.quizbee.subscriptions.domain.services.SubscriptionCommandService;
import pe.edu.upc.quizbee.subscriptions.domain.services.SubscriptionQueryService;
import pe.edu.upc.quizbee.subscriptions.interfaces.rest.resources.CreateSubscriptionResource;
import pe.edu.upc.quizbee.subscriptions.interfaces.rest.resources.SubscriptionResource;
import pe.edu.upc.quizbee.subscriptions.interfaces.rest.resources.UpdatePaymentMethodResource;
import pe.edu.upc.quizbee.subscriptions.interfaces.rest.transform.CreateSubscriptionCommandFromResourceAssembler;
import pe.edu.upc.quizbee.subscriptions.interfaces.rest.transform.SubscriptionResourceFromEntityAssembler;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/subscriptions", produces = "application/json")
@Tag(name = "Subscriptions", description = "Subscription Management Endpoints")
public class SubscriptionController {

    private final SubscriptionCommandService subscriptionCommandService;
    private final SubscriptionQueryService subscriptionQueryService;

    public SubscriptionController(SubscriptionCommandService subscriptionCommandService,
                                   SubscriptionQueryService subscriptionQueryService) {
        this.subscriptionCommandService = subscriptionCommandService;
        this.subscriptionQueryService = subscriptionQueryService;
    }

    @PostMapping
    public ResponseEntity<Long> createSubscription(@RequestBody CreateSubscriptionResource resource) {
        var command = CreateSubscriptionCommandFromResourceAssembler.toCommandFromResource(resource);
        var subscriptionId = subscriptionCommandService.handle(command);
        return new ResponseEntity<>(subscriptionId, HttpStatus.CREATED);
    }

    @GetMapping("/{subscriptionId}")
    public ResponseEntity<SubscriptionResource> getSubscriptionById(@PathVariable Long subscriptionId) {
        var query = new GetSubscriptionByIdQuery(subscriptionId);
        var subscription = subscriptionQueryService.handle(query);
        if (subscription.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var resource = SubscriptionResourceFromEntityAssembler.toResourceFromEntity(subscription.get());
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SubscriptionResource>> getSubscriptionsByUserId(@PathVariable String userId) {
        var query = new GetSubscriptionByUserIdQuery(userId);
        var subscriptions = subscriptionQueryService.handle(query);
        var resources = subscriptions.stream()
                .map(SubscriptionResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @PutMapping("/{subscriptionId}/plan/{newPlanId}")
    public ResponseEntity<SubscriptionResource> changePlan(@PathVariable Long subscriptionId,
                                                           @PathVariable Long newPlanId) {
        var command = new ChangePlanCommand(subscriptionId, newPlanId);
        var subscription = subscriptionCommandService.handle(command);
        if (subscription.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var resource = SubscriptionResourceFromEntityAssembler.toResourceFromEntity(subscription.get());
        return ResponseEntity.ok(resource);
    }

    @PutMapping("/{subscriptionId}/payment-method")
    public ResponseEntity<SubscriptionResource> updatePaymentMethod(@PathVariable Long subscriptionId,
                                                                    @RequestBody UpdatePaymentMethodResource resource) {
        var paymentMethod = new PaymentMethod(
                resource.paymentType(),
                resource.cardLastFourDigits(),
                resource.cardExpiryDate()
        );
        var command = new UpdatePaymentMethodCommand(subscriptionId, paymentMethod);
        var subscription = subscriptionCommandService.handle(command);
        if (subscription.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var subscriptionResource = SubscriptionResourceFromEntityAssembler.toResourceFromEntity(subscription.get());
        return ResponseEntity.ok(subscriptionResource);
    }

    @DeleteMapping("/{subscriptionId}")
    public ResponseEntity<Void> cancelSubscription(@PathVariable Long subscriptionId) {
        var command = new CancelSubscriptionCommand(subscriptionId);
        var subscription = subscriptionCommandService.handle(command);
        if (subscription.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}