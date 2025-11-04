// src/main/java/pe/edu/upc/quizbee/quizzies/application/internal/queryservices/QuizQueryServiceImpl.java
package pe.edu.upc.quizbee.quizzies.application.internal.queryservices;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.quizbee.quizzies.domain.model.aggregates.Quiz;
import pe.edu.upc.quizbee.quizzies.domain.model.queries.*;
import pe.edu.upc.quizbee.quizzies.domain.services.QuizQueryService;
import pe.edu.upc.quizbee.quizzies.infrastructure.persistence.jpa.repositories.QuizRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class QuizQueryServiceImpl implements QuizQueryService {

    private final QuizRepository quizRepository;

    public QuizQueryServiceImpl(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Override
    public List<Quiz> handle(GetAllQuizzesQuery query) {
        return quizRepository.findAll();
    }

    @Override
    public Optional<Quiz> handle(GetQuizByIdQuery query) {
        return quizRepository.findById(query.quizId());
    }

    @Override
    public List<Quiz> handle(GetQuizzesByCategoryQuery query) {
        return quizRepository.findByMetadataCategory(query.category());
    }

    @Override
    public List<Quiz> handle(GetQuizzesByDifficultyQuery query) {
        return quizRepository.findByMetadataDifficultyLevel(query.difficultyLevel());
    }

    @Override
    public List<Quiz> handle(GetQuizzesByTypeQuery query) {
        return quizRepository.findByMetadataType(query.type());
    }

    @Override
    public List<Quiz> handle(GetPopularQuizzesQuery query) {
        var pageable = PageRequest.of(0, query.limit(), Sort.by(Sort.Direction.DESC, "plays"));
        return quizRepository.findAll(pageable).getContent();
    }

    @Override
    public List<Quiz> handle(GetQuizzesByCreatorQuery query) {
        return quizRepository.findByCreatorUserId(query.creatorUserId());
    }

    @Override
    public List<Quiz> handle(SearchQuizzesQuery query) {
        return quizRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                query.searchTerm(),
                query.searchTerm()
        );
    }
}