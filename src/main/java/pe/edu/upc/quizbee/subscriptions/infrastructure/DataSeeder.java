package pe.edu.upc.quizbee.subscriptions.infrastructure;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.quizbee.subscriptions.domain.model.aggregates.Plan;
import pe.edu.upc.quizbee.subscriptions.domain.model.valueobjects.Money;
import pe.edu.upc.quizbee.subscriptions.infrastructure.persistence.jpa.repositories.PlanRepository;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
public class DataSeeder {

    private final PlanRepository planRepository;

    public DataSeeder(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @PostConstruct
    @Transactional
    public void init() {
        // Solo seed si no hay planes
        if (planRepository.count() == 0) {
            seedPlans();
        }
    }

    private void seedPlans() {
        System.out.println("🌱 Seeding plans for Subscriptions BC...");

        // Plan 1: Básico Premium
        Plan basicPlan = new Plan(
            "Básico Premium",
            "100% individual",
            new Money(new BigDecimal("19.99"), "USD"),
            Arrays.asList(
                "Acceso ilimitado a lecciones",
                "Video lecciones HD",
                "Práctica de habilidades",
                "Seguimiento de progreso",
                "Sin anuncios"
            ),
            false
        );

        // Plan 2: Super Familia (Popular)
        Plan familyPlan = new Plan(
            "Super Familia",
            "Hasta 6 usuarios - $1.65 al mes",
            new Money(new BigDecimal("10.69"), "USD"),
            Arrays.asList(
                "Contenido de aprendizaje completo",
                "Vidas ilimitadas",
                "Práctica de habilidades avanzada",
                "Desafíos sin límites",
                "Sin anuncios",
                "¡Hasta 6 usuarios pueden disfrutar de aprendizaje súper veloz!"
            ),
            true
        );

        // Plan 3: Ultra Mega-Premium
        Plan ultraPlan = new Plan(
            "Ultra Mega-Premium",
            "Plan definitivo",
            new Money(new BigDecimal("49.99"), "USD"),
            Arrays.asList(
                "Profesional diario 1X8",
                "Todo Premium Plus incluido",
                "Clases particulares con tutores",
                "Ejercicios de dominio avanzado",
                "Certificados oficiales",
                "Aprendizaje sin conexión"
            ),
            false
        );

        planRepository.saveAll(Arrays.asList(basicPlan, familyPlan, ultraPlan));
        System.out.println("✅ Plans seeded successfully for Subscriptions BC!");
    }
}