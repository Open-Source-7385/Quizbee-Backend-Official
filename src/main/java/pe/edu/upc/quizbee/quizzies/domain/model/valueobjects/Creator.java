// src/main/java/pe/edu/upc/quizbee/quizzies/domain/model/valueobjects/Creator.java
package pe.edu.upc.quizbee.quizzies.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Creator(
        Long userId,
        String name,
        String avatar
) {
    public Creator() {
        this(null, null, null);
    }

    public Creator {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Creator name cannot be null or blank");
        }
    }
}