// src/main/java/pe/edu/upc/quizbee/quizzies/interfaces/rest/transform/UpdateQuizQuestionsCommandFromResourceAssembler.java
package pe.edu.upc.quizbee.quizzies.interfaces.rest.transform;

import pe.edu.upc.quizbee.quizzies.domain.model.commands.UpdateQuizQuestionsCommand;
import pe.edu.upc.quizbee.quizzies.interfaces.rest.resources.UpdateQuizQuestionsResource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UpdateQuizQuestionsCommandFromResourceAssembler {

    public static UpdateQuizQuestionsCommand toCommandFromResource(
            Long quizId,
            UpdateQuizQuestionsResource resource) {

        List<UpdateQuizQuestionsCommand.QuestionUpdateData> questions;

        if (resource.questions() != null && !resource.questions().isEmpty()) {
            questions = resource.questions().stream()
                    .map(q -> {
                        List<UpdateQuizQuestionsCommand.AlternativeUpdateData> alternatives;

                        if (q.alternatives() != null) {
                            alternatives = q.alternatives().stream()
                                    .map(a -> new UpdateQuizQuestionsCommand.AlternativeUpdateData(
                                            a.id(),
                                            a.text(),
                                            a.isCorrect()
                                    ))
                                    .collect(Collectors.toList());
                        } else {
                            alternatives = Collections.emptyList();
                        }

                        return new UpdateQuizQuestionsCommand.QuestionUpdateData(
                                q.id(),
                                q.textQuestion(),
                                q.orderNumber(),
                                q.points(),
                                alternatives
                        );
                    })
                    .collect(Collectors.toList());
        } else {
            questions = Collections.emptyList();
        }

        return new UpdateQuizQuestionsCommand(
                quizId,
                resource.title(),
                questions
        );
    }
}