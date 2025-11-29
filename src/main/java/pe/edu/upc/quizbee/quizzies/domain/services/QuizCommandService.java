// src/main/java/pe/edu/upc/quizbee/quizzies/domain/services/QuizCommandService.java
package pe.edu.upc.quizbee.quizzies.domain.services;

import pe.edu.upc.quizbee.quizzies.domain.model.aggregates.Quiz;
import pe.edu.upc.quizbee.quizzies.domain.model.commands.*;

import java.util.Optional;

public interface QuizCommandService {
    Optional<Quiz> handle(CreateQuizCommand command);
    Optional<Quiz> handle(UpdateQuizCommand command);
    Optional<Quiz> handle(UpdateQuizQuestionsCommand command);
    void handle(DeleteQuizCommand command);
    Optional<Quiz> handle(IncrementQuizPlaysCommand command);
}