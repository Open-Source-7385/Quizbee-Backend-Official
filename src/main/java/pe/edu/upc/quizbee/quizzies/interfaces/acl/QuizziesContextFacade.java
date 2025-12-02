// src/main/java/pe/edu/upc/quizbee/quizzies/interfaces/acl/QuizziesContextFacade.java
package pe.edu.upc.quizbee.quizzies.interfaces.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.quizbee.quizzies.domain.model.queries.GetQuizByIdQuery;
import pe.edu.upc.quizbee.quizzies.domain.model.queries.GetQuizzesByCreatorQuery;
import pe.edu.upc.quizbee.quizzies.domain.services.QuizQueryService;

import java.util.List;

/**
 * Quizzies Context Facade
 * <p>
 *     ACL for other bounded contexts to interact with Quizzies.
 * </p>
 */
@Service
public class QuizziesContextFacade {

    private final QuizQueryService quizQueryService;

    public QuizziesContextFacade(QuizQueryService quizQueryService) {
        this.quizQueryService = quizQueryService;
    }

    /**
     * Get quiz title by ID
     * @param quizId the quiz ID
     * @return the quiz title
     */
    public String getQuizTitleById(Long quizId) {
        var query = new GetQuizByIdQuery(quizId);
        var quiz = quizQueryService.handle(query);
        return quiz.map(q -> q.getTitle()).orElse("");
    }

    /**
     * Get total quizzes created by user
     * @param userId the user ID
     * @return the count of quizzes
     */
    public Integer getTotalQuizzesCreatedByUser(Long userId) {
        var query = new GetQuizzesByCreatorQuery(userId);
        var quizzes = quizQueryService.handle(query);
        return quizzes.size();
    }

    /**
     * Check if user has created quizzes
     * @param userId the user ID
     * @return true if user has quizzes
     */
    public Boolean userHasQuizzes(Long userId) {
        return getTotalQuizzesCreatedByUser(userId) > 0;
    }
}