package pe.edu.upc.quizbee.subscriptions.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.quizbee.subscriptions.domain.services.PlanCommandService;
import pe.edu.upc.quizbee.subscriptions.infrastructure.persistence.jpa.repositories.PlanRepository;

@Service
public class PlanCommandServiceImpl implements PlanCommandService {

private final PlanRepository planRepository;

public PlanCommandServiceImpl(PlanRepository planRepository) {
    this.planRepository = planRepository;
}

// Por ahora no hay comandos para crear/actualizar planes
// Los planes se crean directamente en la base de datos o con scripts de inicialización
}