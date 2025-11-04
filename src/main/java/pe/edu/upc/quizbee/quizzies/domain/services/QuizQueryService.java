// src/main/java/pe/edu/upc/quizbee/quizzies/domain/services/QuizQueryService.java
package pe.edu.upc.quizbee.quizzies.domain.services;

import pe.edu.upc.quizbee.quizzies.domain.model.aggregates.Quiz;
import pe.edu.upc.quizbee.quizzies.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface QuizQueryService {
    List<Quiz> handle(GetAllQuizzesQuery query);
    Optional<Quiz> handle(GetQuizByIdQuery query);
    List<Quiz> handle(GetQuizzesByCategoryQuery query);
    List<Quiz> handle(GetQuizzesByDifficultyQuery query);
    List<Quiz> handle(GetQuizzesByTypeQuery query);
    List<Quiz> handle(GetPopularQuizzesQuery query);
    List<Quiz> handle(GetQuizzesByCreatorQuery query);
    List<Quiz> handle(SearchQuizzesQuery query);
}