// src/main/java/pe/edu/upc/quizbee/quizzies/interfaces/rest/resources/QuizResource.java
package pe.edu.upc.quizbee.quizzies.interfaces.rest.resources;

import java.util.Date;
import java.util.List;

public record QuizResource(
        Long id,
        String title,
        String description,
        CreatorResource creator,
        String category,
        String type,
        Integer difficultyLevel,
        String difficultyStars,
        Integer points,
        Integer timeLimit,
        String formattedTime,
        Integer plays,
        Integer totalQuestions,
        Date createdAt,
        List<QuestionResource> questions
) {
    public record CreatorResource(
            Long userId,
            String name,
            String avatar
    ) {}

    public record QuestionResource(
            Long id,
            String textQuestion,
            Integer orderNumber,
            Integer points,
            List<AlternativeResource> alternatives
    ) {}

    public record AlternativeResource(
            Long id,
            String text,
            Boolean isCorrect
    ) {}
}