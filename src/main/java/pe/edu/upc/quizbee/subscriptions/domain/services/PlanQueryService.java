package pe.edu.upc.quizbee.subscriptions.domain.services;

import pe.edu.upc.quizbee.subscriptions.domain.model.aggregates.Plan;
import pe.edu.upc.quizbee.subscriptions.domain.model.queries.GetAllPlansQuery;
import pe.edu.upc.quizbee.subscriptions.domain.model.queries.GetPlanByIdQuery;

import java.util.List;
import java.util.Optional;

public interface PlanQueryService {
    List<Plan> handle(GetAllPlansQuery query);
    Optional<Plan> handle(GetPlanByIdQuery query);
}