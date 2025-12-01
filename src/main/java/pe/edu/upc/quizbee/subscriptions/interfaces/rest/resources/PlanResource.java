package pe.edu.upc.quizbee.subscriptions.interfaces.rest.resources;

import java.math.BigDecimal;
import java.util.List;

public record PlanResource(
    Long id,
    String name,
    String subtitle,
    BigDecimal priceAmount,
    String priceCurrency,
    List<String> features,
    Boolean popular
) {
}