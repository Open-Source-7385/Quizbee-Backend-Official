// src/main/java/pe/edu/upc/quizbee/quizzies/application/internal/commandservices/QuizCommandServiceImpl.java
package pe.edu.upc.quizbee.quizzies.application.internal.commandservices;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.quizbee.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import pe.edu.upc.quizbee.quizzies.domain.model.aggregates.Quiz;
import pe.edu.upc.quizbee.quizzies.domain.model.commands.*;
import pe.edu.upc.quizbee.quizzies.domain.model.entities.Alternative;
import pe.edu.upc.quizbee.quizzies.domain.model.entities.Question;
import pe.edu.upc.quizbee.quizzies.domain.model.valueobjects.Creator;
import pe.edu.upc.quizbee.quizzies.domain.model.valueobjects.QuizMetadata;
import pe.edu.upc.quizbee.quizzies.domain.services.QuizCommandService;
import pe.edu.upc.quizbee.quizzies.infrastructure.persistence.jpa.repositories.QuizRepository;

import java.util.Optional;

@Service
public class QuizCommandServiceImpl implements QuizCommandService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;

    public QuizCommandServiceImpl(QuizRepository quizRepository, UserRepository userRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Optional<Quiz> handle(CreateQuizCommand command) {
        // Validate creator exists
        var user = userRepository.findById(command.creatorUserId())
                .orElseThrow(() -> new IllegalArgumentException("Creator user not found with id: " + command.creatorUserId()));

        // Create Creator value object
        var creator = new Creator(user.getId(), user.getUsername(), "👤");

        // Create QuizMetadata
        var metadata = new QuizMetadata(
                command.category(),
                command.type(),
                command.difficultyLevel(),
                command.timeLimit()
        );

        // Create Quiz
        var quiz = new Quiz(command.title(), command.description(), creator, metadata);

        // Add questions and alternatives
        if (command.questions() != null) {
            for (var questionData : command.questions()) {
                var question = new Question(
                        questionData.textQuestion(),
                        questionData.orderNumber(),
                        questionData.points()
                );

                // Add alternatives
                if (questionData.alternatives() != null) {
                    for (var altData : questionData.alternatives()) {
                        var alternative = new Alternative(altData.text(), altData.isCorrect());
                        question.addAlternative(alternative);
                    }
                }

                quiz.addQuestion(question);
            }
        }

        // Validate quiz
        if (!quiz.isValid()) {
            throw new IllegalArgumentException("Quiz is not valid. Ensure all questions have correct answers.");
        }

        // Save quiz
        quizRepository.save(quiz);
        return Optional.of(quiz);
    }

    @Override
    @Transactional
    public Optional<Quiz> handle(UpdateQuizCommand command) {
        var quiz = quizRepository.findById(command.quizId())
                .orElseThrow(() -> new IllegalArgumentException("Quiz not found with id: " + command.quizId()));

        // Update basic fields
        if (command.title() != null && !command.title().isBlank()) {
            quiz.setTitle(command.title());
        }

        if (command.description() != null) {
            quiz.setDescription(command.description());
        }

        // Update metadata
        if (command.category() != null || command.type() != null ||
                command.difficultyLevel() != null || command.timeLimit() != null) {

            var newMetadata = new QuizMetadata(
                    command.category() != null ? command.category() : quiz.getMetadata().category(),
                    command.type() != null ? command.type() : quiz.getMetadata().type(),
                    command.difficultyLevel() != null ? command.difficultyLevel() : quiz.getMetadata().difficultyLevel(),
                    command.timeLimit() != null ? command.timeLimit() : quiz.getMetadata().timeLimit()
            );
            quiz.setMetadata(newMetadata);
        }

        quizRepository.save(quiz);
        return Optional.of(quiz);
    }

    @Override
    @Transactional
    public void handle(DeleteQuizCommand command) {
        if (!quizRepository.existsById(command.quizId())) {
            throw new IllegalArgumentException("Quiz not found with id: " + command.quizId());
        }
        quizRepository.deleteById(command.quizId());
    }

    @Override
    @Transactional
    public Optional<Quiz> handle(IncrementQuizPlaysCommand command) {
        var quiz = quizRepository.findById(command.quizId())
                .orElseThrow(() -> new IllegalArgumentException("Quiz not found with id: " + command.quizId()));

        quiz.incrementPlays();
        quizRepository.save(quiz);
        return Optional.of(quiz);
    }
    @Override
    @Transactional
    public Optional<Quiz> handle(UpdateQuizQuestionsCommand command) {
        var quiz = quizRepository.findById(command.quizId())
                .orElseThrow(() -> new IllegalArgumentException("Quiz not found with id: " + command.quizId()));

        // Update title if provided
        if (command.title() != null && !command.title().isBlank()) {
            quiz.setTitle(command.title());
        }

        // Update questions if provided
        if (command.questions() != null && !command.questions().isEmpty()) {
            // Remove all existing questions
            var questionsToRemove = new java.util.ArrayList<>(quiz.getQuestions());
            for (var question : questionsToRemove) {
                quiz.removeQuestion(question);
            }

            // Add updated questions
            for (var questionData : command.questions()) {
                var question = new Question(
                        questionData.textQuestion(),
                        questionData.orderNumber(),
                        questionData.points()
                );

                // Add alternatives
                if (questionData.alternatives() != null) {
                    for (var altData : questionData.alternatives()) {
                        var alternative = new Alternative(
                                altData.text(),
                                altData.isCorrect()
                        );
                        question.addAlternative(alternative);
                    }
                }

                quiz.addQuestion(question);
            }
        }

        // Validate quiz
        if (!quiz.isValid()) {
            throw new IllegalArgumentException("Quiz is not valid. Ensure all questions have correct answers.");
        }

        quizRepository.save(quiz);
        return Optional.of(quiz);
    }
}