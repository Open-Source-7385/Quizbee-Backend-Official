// src/main/java/pe/edu/upc/quizbee/quizzies/interfaces/rest/transform/CreateQuizCommandFromResourceAssembler.java
package pe.edu.upc.quizbee.quizzies.interfaces.rest.transform;

import pe.edu.upc.quizbee.quizzies.domain.model.commands.CreateQuizCommand;
import pe.edu.upc.quizbee.quizzies.interfaces.rest.resources.CreateQuizResource;

import java.util.stream.Collectors;

public class CreateQuizCommandFromResourceAssembler {

    public static CreateQuizCommand toCommandFromResource(CreateQuizResource resource) {
        var questions = resource.questions() != null ?
                resource.questions().stream()
                        .map(q -> new CreateQuizCommand.QuestionData(
                                q.textQuestion(),
                                q.orderNumber(),
                                q.points(),
                                q.alternatives().stream()
                                        .map(a -> new CreateQuizCommand.AlternativeData(a.text(), a.isCorrect()))
                                        .collect(Collectors.toList())
                        ))
                        .collect(Collectors.toList()) : null;

        return new CreateQuizCommand(
                resource.title(),
                resource.description(),
                resource.creatorUserId(),
                resource.category(),
                resource.type(),
                resource.difficultyLevel(),
                resource.timeLimit(),
                questions
        );
    }
}