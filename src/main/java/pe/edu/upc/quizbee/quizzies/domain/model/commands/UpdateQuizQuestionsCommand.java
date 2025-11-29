package pe.edu.upc.quizbee.quizzies.domain.model.commands;

import java.util.List;

/**
 * Command to update quiz title and questions
 * Only allows editing the name and questions, not metadata
 */
public record UpdateQuizQuestionsCommand(
        Long quizId,
        String title,
        List<QuestionUpdateData> questions
) {
    public record QuestionUpdateData(
            Long id, // null for new questions
            String textQuestion,
            Integer orderNumber,
            Integer points,
            List<AlternativeUpdateData> alternatives
    ) {}

    public record AlternativeUpdateData(
            Long id, // null for new alternatives
            String text,
            Boolean isCorrect
    ) {}
}