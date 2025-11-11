package pe.edu.upc.quizbee.subscriptions.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.quizbee.subscriptions.domain.model.aggregates.Subscription;
import pe.edu.upc.quizbee.subscriptions.domain.model.queries.GetSubscriptionByIdQuery;
import pe.edu.upc.quizbee.subscriptions.domain.model.queries.GetSubscriptionByUserIdQuery;
import pe.edu.upc.quizbee.subscriptions.domain.services.SubscriptionQueryService;
import pe.edu.upc.quizbee.subscriptions.infrastructure.persistence.jpa.repositories.SubscriptionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionQueryServiceImpl implements SubscriptionQueryService {

private final SubscriptionRepository subscriptionRepository;

public SubscriptionQueryServiceImpl(SubscriptionRepository subscriptionRepository) {
    this.subscriptionRepository = subscriptionRepository;
}

@Override
public Optional<Subscription> handle(GetSubscriptionByIdQuery query) {
    return subscriptionRepository.findById(query.subscriptionId());
}

@Override
public List<Subscription> handle(GetSubscriptionByUserIdQuery query) {
    return subscriptionRepository.findByUserId(query.userId());
}
}