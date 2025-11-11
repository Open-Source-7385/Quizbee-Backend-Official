package pe.edu.upc.quizbee.subscriptions.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.quizbee.subscriptions.domain.model.aggregates.Plan;
import pe.edu.upc.quizbee.subscriptions.domain.model.queries.GetAllPlansQuery;
import pe.edu.upc.quizbee.subscriptions.domain.model.queries.GetPlanByIdQuery;
import pe.edu.upc.quizbee.subscriptions.domain.services.PlanQueryService;
import pe.edu.upc.quizbee.subscriptions.infrastructure.persistence.jpa.repositories.PlanRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PlanQueryServiceImpl implements PlanQueryService {

private final PlanRepository planRepository;

public PlanQueryServiceImpl(PlanRepository planRepository) {
    this.planRepository = planRepository;
}

@Override
public List<Plan> handle(GetAllPlansQuery query) {
    return planRepository.findAll();
}

@Override
public Optional<Plan> handle(GetPlanByIdQuery query) {
    return planRepository.findById(query.planId());
}
}