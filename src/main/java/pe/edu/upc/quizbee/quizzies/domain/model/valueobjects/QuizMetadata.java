// src/main/java/pe/edu/upc/quizbee/quizzies/domain/model/valueobjects/QuizMetadata.java
package pe.edu.upc.quizbee.quizzies.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record QuizMetadata(
        String category,
        String type,
        Integer difficultyLevel,
        Integer timeLimit
) {
    public QuizMetadata() {
        this(null, "questions", 1, 600);
    }

    public QuizMetadata {
        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException("Category cannot be null or blank");
        }
        if (difficultyLevel < 1 || difficultyLevel > 6) {
            throw new IllegalArgumentException("Difficulty level must be between 1 and 6");
        }
        if (timeLimit < 0) {
            throw new IllegalArgumentException("Time limit cannot be negative");
        }
    }

    public String getDifficultyStars() {
        return "⭐".repeat(difficultyLevel);
    }
}