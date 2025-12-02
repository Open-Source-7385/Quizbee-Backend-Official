package pe.edu.upc.quizbee.subscriptions.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.quizbee.subscriptions.domain.model.aggregates.Subscription;
import pe.edu.upc.quizbee.subscriptions.domain.model.valueobjects.SubscriptionStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByUserId(String userId);
    Optional<Subscription> findByUserIdAndStatus(String userId, SubscriptionStatus status);
    List<Subscription> findByStatus(SubscriptionStatus status);
    boolean existsByUserIdAndStatus(String userId, SubscriptionStatus status);
}