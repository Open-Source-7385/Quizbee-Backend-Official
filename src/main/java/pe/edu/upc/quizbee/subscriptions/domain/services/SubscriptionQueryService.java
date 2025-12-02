package pe.edu.upc.quizbee.subscriptions.domain.services;

import pe.edu.upc.quizbee.subscriptions.domain.model.aggregates.Subscription;
import pe.edu.upc.quizbee.subscriptions.domain.model.queries.GetSubscriptionByIdQuery;
import pe.edu.upc.quizbee.subscriptions.domain.model.queries.GetSubscriptionByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface SubscriptionQueryService {
    Optional<Subscription> handle(GetSubscriptionByIdQuery query);
    List<Subscription> handle(GetSubscriptionByUserIdQuery query);
}