package pe.edu.upc.quizbee.subscriptions.interfaces.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.quizbee.subscriptions.domain.model.queries.GetSubscriptionByUserIdQuery;
import pe.edu.upc.quizbee.subscriptions.domain.model.valueobjects.SubscriptionStatus;
import pe.edu.upc.quizbee.subscriptions.domain.services.SubscriptionQueryService;

/**
 * Anti-Corruption Layer (ACL) Facade for Subscription Context
 * 
 * Esta clase permite que otros Bounded Contexts (como Profile, IAM, Quizzes)
 * accedan a información del contexto de Subscriptions sin conocer su implementación interna.
 * 
 * Proporciona una interfaz simplificada y desacoplada.
 */
@Service
public class SubscriptionContextFacade {

    private final SubscriptionQueryService subscriptionQueryService;

    public SubscriptionContextFacade(SubscriptionQueryService subscriptionQueryService) {
        this.subscriptionQueryService = subscriptionQueryService;
    }

    /**
     * Verifica si un usuario tiene una suscripción activa
     * 
     * @param userId El ID del usuario a verificar
     * @return true si el usuario tiene suscripción activa, false en caso contrario
     */
    public boolean hasActiveSubscription(String userId) {
        var query = new GetSubscriptionByUserIdQuery(userId);
        var subscriptions = subscriptionQueryService.handle(query);
        
        return subscriptions.stream()
                .anyMatch(subscription -> subscription.getStatus() == SubscriptionStatus.ACTIVE);
    }

    /**
     * Obtiene el ID del plan actual del usuario
     * 
     * @param userId El ID del usuario
     * @return El ID del plan si existe una suscripción activa, null en caso contrario
     */
    public Long getUserActivePlanId(String userId) {
        var query = new GetSubscriptionByUserIdQuery(userId);
        var subscriptions = subscriptionQueryService.handle(query);
        
        return subscriptions.stream()
                .filter(subscription -> subscription.getStatus() == SubscriptionStatus.ACTIVE)
                .findFirst()
                .map(subscription -> subscription.getPlanId())
                .orElse(null);
    }

    /**
     * Verifica si un usuario es Premium (tiene cualquier suscripción activa)
     * 
     * @param userId El ID del usuario
     * @return true si el usuario es Premium, false en caso contrario
     */
    public boolean isPremiumUser(String userId) {
        return hasActiveSubscription(userId);
    }

    /**
     * Obtiene el tipo de suscripción del usuario
     * 
     * @param userId El ID del usuario
     * @return El nombre del plan si existe, "FREE" en caso contrario
     */
    public String getUserSubscriptionType(String userId) {
        var planId = getUserActivePlanId(userId);
        if (planId == null) {
            return "FREE";
        }
        
        // Aquí podrías hacer una query adicional para obtener el nombre del plan
        // Por simplicidad, retornamos el planId como string
        return "PREMIUM_" + planId;
    }
}