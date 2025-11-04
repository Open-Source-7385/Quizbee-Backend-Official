// src/main/java/pe/edu/upc/quizbee/quizzies/interfaces/rest/transform/UpdateQuizCommandFromResourceAssembler.java
package pe.edu.upc.quizbee.quizzies.interfaces.rest.transform;

import pe.edu.upc.quizbee.quizzies.domain.model.commands.UpdateQuizCommand;
import pe.edu.upc.quizbee.quizzies.interfaces.rest.resources.UpdateQuizResource;

public class UpdateQuizCommandFromResourceAssembler {

    public static UpdateQuizCommand toCommandFromResource(Long quizId, UpdateQuizResource resource) {
        return new UpdateQuizCommand(
                quizId,
                resource.title(),
                resource.description(),
                resource.category(),
                resource.type(),
                resource.difficultyLevel(),
                resource.timeLimit()
        );
    }
}