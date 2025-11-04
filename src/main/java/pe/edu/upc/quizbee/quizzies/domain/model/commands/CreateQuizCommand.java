// src/main/java/pe/edu/upc/quizbee/quizzies/domain/model/commands/CreateQuizCommand.java
package pe.edu.upc.quizbee.quizzies.domain.model.commands;

import java.util.List;

public record CreateQuizCommand(
        String title,
        String description,
        Long creatorUserId,
        String category,
        String type,
        Integer difficultyLevel,
        Integer timeLimit,
        List<QuestionData> questions
) {
    public record QuestionData(
            String textQuestion,
            Integer orderNumber,
            Integer points,
            List<AlternativeData> alternatives
    ) {}

    public record AlternativeData(
            String text,
            Boolean isCorrect
    ) {}
}