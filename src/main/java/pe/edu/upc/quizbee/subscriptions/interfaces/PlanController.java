package pe.edu.upc.quizbee.subscriptions.interfaces;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upc.quizbee.subscriptions.domain.model.queries.GetAllPlansQuery;
import pe.edu.upc.quizbee.subscriptions.domain.model.queries.GetPlanByIdQuery;
import pe.edu.upc.quizbee.subscriptions.domain.services.PlanQueryService;
import pe.edu.upc.quizbee.subscriptions.interfaces.rest.resources.PlanResource;
import pe.edu.upc.quizbee.subscriptions.interfaces.rest.transform.PlanResourceFromEntityAssembler;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/plans", produces = "application/json")
@Tag(name = "Plans", description = "Plan Management Endpoints")
public class PlanController {

    private final PlanQueryService planQueryService;

    public PlanController(PlanQueryService planQueryService) {
        this.planQueryService = planQueryService;
    }

    @GetMapping
    public ResponseEntity<List<PlanResource>> getAllPlans() {
        var query = new GetAllPlansQuery();
        var plans = planQueryService.handle(query);
        var resources = plans.stream()
            .map(PlanResourceFromEntityAssembler::toResourceFromEntity)
            .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{planId}")
    public ResponseEntity<PlanResource> getPlanById(@PathVariable Long planId) {
        var query = new GetPlanByIdQuery(planId);
        var plan = planQueryService.handle(query);
        if (plan.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var resource = PlanResourceFromEntityAssembler.toResourceFromEntity(plan.get());
        return ResponseEntity.ok(resource);
    }
}