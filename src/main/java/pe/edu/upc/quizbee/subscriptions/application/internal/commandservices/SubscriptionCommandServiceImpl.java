package pe.edu.upc.quizbee.subscriptions.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.quizbee.subscriptions.domain.model.aggregates.Plan;
import pe.edu.upc.quizbee.subscriptions.domain.model.aggregates.Subscription;
import pe.edu.upc.quizbee.subscriptions.domain.model.commands.CancelSubscriptionCommand;
import pe.edu.upc.quizbee.subscriptions.domain.model.commands.ChangePlanCommand;
import pe.edu.upc.quizbee.subscriptions.domain.model.commands.CreateSubscriptionCommand;
import pe.edu.upc.quizbee.subscriptions.domain.model.commands.UpdatePaymentMethodCommand;
import pe.edu.upc.quizbee.subscriptions.domain.services.SubscriptionCommandService;
import pe.edu.upc.quizbee.subscriptions.infrastructure.persistence.jpa.repositories.PlanRepository;
import pe.edu.upc.quizbee.subscriptions.infrastructure.persistence.jpa.repositories.SubscriptionRepository;

import java.util.Optional;

@Service
public class SubscriptionCommandServiceImpl implements SubscriptionCommandService {

    private final SubscriptionRepository subscriptionRepository;
    private final PlanRepository planRepository;

    public SubscriptionCommandServiceImpl(SubscriptionRepository subscriptionRepository, 
                                          PlanRepository planRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.planRepository = planRepository;
    }

    @Override
    public Long handle(CreateSubscriptionCommand command) {
        Plan plan = planRepository.findById(command.planId())
                .orElseThrow(() -> new IllegalArgumentException("Plan not found with id: " + command.planId()));

        Subscription subscription = new Subscription(
                command.userId(),
                command.planId(),
                plan.getPrice(),
                command.paymentMethod()
        );

        subscriptionRepository.save(subscription);
        return subscription.getId();
    }

    @Override
    public Optional<Subscription> handle(ChangePlanCommand command) {
        return subscriptionRepository.findById(command.subscriptionId())
                .map(subscription -> {
                    Plan newPlan = planRepository.findById(command.newPlanId())
                            .orElseThrow(() -> new IllegalArgumentException("Plan not found with id: " + command.newPlanId()));
                    
                    subscription.changePlan(command.newPlanId(), newPlan.getPrice());
                    subscriptionRepository.save(subscription);
                    return subscription;
                });
    }

    @Override
    public Optional<Subscription> handle(UpdatePaymentMethodCommand command) {
        return subscriptionRepository.findById(command.subscriptionId())
                .map(subscription -> {
                    subscription.updatePaymentMethod(command.paymentMethod());
                    subscriptionRepository.save(subscription);
                    return subscription;
                });
    }

    @Override
    public Optional<Subscription> handle(CancelSubscriptionCommand command) {
        return subscriptionRepository.findById(command.subscriptionId())
                .map(subscription -> {
                    subscription.cancel();
                    subscriptionRepository.save(subscription);
                    return subscription;
                });
    }
}