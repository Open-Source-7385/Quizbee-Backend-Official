package pe.edu.upc.quizbee.quizzies.interfaces.rest.resources;

import java.util.List;

public record UpdateQuizQuestionsResource(
        String title,
        List<QuestionUpdateResource> questions
) {
    public record QuestionUpdateResource(
            Long id,
            String textQuestion,
            Integer orderNumber,
            Integer points,
            List<AlternativeUpdateResource> alternatives
    ) {}

    public record AlternativeUpdateResource(
            Long id,
            String text,
            Boolean isCorrect
    ) {}
}
