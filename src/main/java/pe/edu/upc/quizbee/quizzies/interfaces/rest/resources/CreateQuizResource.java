// src/main/java/pe/edu/upc/quizbee/quizzies/interfaces/rest/resources/CreateQuizResource.java
package pe.edu.upc.quizbee.quizzies.interfaces.rest.resources;

import java.util.List;

public record CreateQuizResource(
        String title,
        String description,
        Long creatorUserId,
        String category,
        String type,
        Integer difficultyLevel,
        Integer timeLimit,
        List<QuestionResource> questions
) {
    public record QuestionResource(
            String textQuestion,
            Integer orderNumber,
            Integer points,
            List<AlternativeResource> alternatives
    ) {}

    public record AlternativeResource(
            String text,
            Boolean isCorrect
    ) {}
}