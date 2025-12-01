package pe.edu.upc.quizbee.subscriptions.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.quizbee.subscriptions.domain.model.aggregates.Plan;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan> findByPopular(Boolean popular);
    Optional<Plan> findByName(String name);
}