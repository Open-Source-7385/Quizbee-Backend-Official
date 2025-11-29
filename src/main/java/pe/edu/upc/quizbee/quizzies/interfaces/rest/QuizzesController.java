// src/main/java/pe/edu/upc/quizbee/quizzies/interfaces/rest/QuizzesController.java
package pe.edu.upc.quizbee.quizzies.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.quizbee.quizzies.domain.model.commands.DeleteQuizCommand;
import pe.edu.upc.quizbee.quizzies.domain.model.commands.IncrementQuizPlaysCommand;
import pe.edu.upc.quizbee.quizzies.domain.model.queries.*;
import pe.edu.upc.quizbee.quizzies.domain.services.QuizCommandService;
import pe.edu.upc.quizbee.quizzies.domain.services.QuizQueryService;
import pe.edu.upc.quizbee.quizzies.interfaces.rest.resources.CreateQuizResource;
import pe.edu.upc.quizbee.quizzies.interfaces.rest.resources.QuizResource;
import pe.edu.upc.quizbee.quizzies.interfaces.rest.resources.UpdateQuizResource;
import pe.edu.upc.quizbee.quizzies.interfaces.rest.transform.CreateQuizCommandFromResourceAssembler;
import pe.edu.upc.quizbee.quizzies.interfaces.rest.transform.QuizResourceFromEntityAssembler;
import pe.edu.upc.quizbee.quizzies.interfaces.rest.transform.UpdateQuizCommandFromResourceAssembler;
import pe.edu.upc.quizbee.quizzies.domain.model.commands.UpdateQuizQuestionsCommand;
import pe.edu.upc.quizbee.quizzies.interfaces.rest.resources.UpdateQuizQuestionsResource;
import pe.edu.upc.quizbee.quizzies.interfaces.rest.transform.UpdateQuizQuestionsCommandFromResourceAssembler;

import java.util.List;

/**
 * QuizzesController
 * <p>
 *     REST controller for managing quizzes.
 *     Provides endpoints for CRUD operations and advanced queries.
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/quizzes", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Quizzes", description = "Quiz Management Endpoints")
public class QuizzesController {

    private final QuizCommandService quizCommandService;
    private final QuizQueryService quizQueryService;

    public QuizzesController(QuizCommandService quizCommandService, QuizQueryService quizQueryService) {
        this.quizCommandService = quizCommandService;
        this.quizQueryService = quizQueryService;
    }

    /**
     * Create a new quiz
     * @param resource the quiz data
     * @return the created quiz
     */
    @Operation(summary = "Create a new quiz", description = "Creates a new quiz with questions and alternatives")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Quiz created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid quiz data"),
            @ApiResponse(responseCode = "404", description = "Creator user not found")
    })
    @PostMapping
    public ResponseEntity<QuizResource> createQuiz(@RequestBody CreateQuizResource resource) {
        var createCommand = CreateQuizCommandFromResourceAssembler.toCommandFromResource(resource);
        var quiz = quizCommandService.handle(createCommand);

        if (quiz.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var quizResource = QuizResourceFromEntityAssembler.toResourceFromEntity(quiz.get());
        return new ResponseEntity<>(quizResource, HttpStatus.CREATED);
    }

    /**
     * Get all quizzes
     * @return list of all quizzes
     */
    @Operation(summary = "Get all quizzes", description = "Retrieves all quizzes in the system")
    @ApiResponse(responseCode = "200", description = "Quizzes retrieved successfully")
    @GetMapping
    public ResponseEntity<List<QuizResource>> getAllQuizzes() {
        var getAllQuizzesQuery = new GetAllQuizzesQuery();
        var quizzes = quizQueryService.handle(getAllQuizzesQuery);
        var quizResources = quizzes.stream()
                .map(QuizResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(quizResources);
    }

    /**
     * Get quiz by ID
     * @param quizId the quiz ID
     * @return the quiz
     */
    @Operation(summary = "Get quiz by ID", description = "Retrieves a quiz by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quiz found"),
            @ApiResponse(responseCode = "404", description = "Quiz not found")
    })
    @GetMapping("/{quizId}")
    public ResponseEntity<QuizResource> getQuizById(@PathVariable Long quizId) {
        var getQuizByIdQuery = new GetQuizByIdQuery(quizId);
        var quiz = quizQueryService.handle(getQuizByIdQuery);

        if (quiz.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var quizResource = QuizResourceFromEntityAssembler.toResourceFromEntity(quiz.get());
        return ResponseEntity.ok(quizResource);
    }

    /**
     * Update quiz
     * @param quizId the quiz ID
     * @param resource the updated quiz data
     * @return the updated quiz
     */
    @Operation(summary = "Update quiz", description = "Updates an existing quiz")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quiz updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid quiz data"),
            @ApiResponse(responseCode = "404", description = "Quiz not found")
    })
    @PutMapping("/{quizId}")
    public ResponseEntity<QuizResource> updateQuiz(
            @PathVariable Long quizId,
            @RequestBody UpdateQuizResource resource) {

        var updateCommand = UpdateQuizCommandFromResourceAssembler.toCommandFromResource(quizId, resource);
        var quiz = quizCommandService.handle(updateCommand);

        if (quiz.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var quizResource = QuizResourceFromEntityAssembler.toResourceFromEntity(quiz.get());
        return ResponseEntity.ok(quizResource);
    }

    // MÉTODO A AGREGAR:
    /**
     * Update quiz questions and title only
     * @param quizId the quiz ID
     * @param resource the quiz questions update data
     * @return the updated quiz
     */
    @Operation(summary = "Update quiz questions and title",
            description = "Updates only the title and questions of a quiz, preserving metadata")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quiz questions updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid quiz data"),
            @ApiResponse(responseCode = "404", description = "Quiz not found")
    })
    @PatchMapping("/{quizId}/questions")
    public ResponseEntity<QuizResource> updateQuizQuestions(
            @PathVariable Long quizId,
            @RequestBody UpdateQuizQuestionsResource resource) {

        var updateCommand = UpdateQuizQuestionsCommandFromResourceAssembler
                .toCommandFromResource(quizId, resource);
        var quiz = quizCommandService.handle(updateCommand);

        if (quiz.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var quizResource = QuizResourceFromEntityAssembler.toResourceFromEntity(quiz.get());
        return ResponseEntity.ok(quizResource);
    }

    /**
     * Delete quiz
     * @param quizId the quiz ID
     * @return no content
     */
    @Operation(summary = "Delete quiz", description = "Deletes a quiz by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Quiz deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Quiz not found")
    })
    @DeleteMapping("/{quizId}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long quizId) {
        var deleteCommand = new DeleteQuizCommand(quizId);
        quizCommandService.handle(deleteCommand);
        return ResponseEntity.noContent().build();
    }

    /**
     * Increment quiz plays
     * @param quizId the quiz ID
     * @return the updated quiz
     */
    @Operation(summary = "Increment quiz plays", description = "Increments the play count of a quiz")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plays incremented successfully"),
            @ApiResponse(responseCode = "404", description = "Quiz not found")
    })
    @PatchMapping("/{quizId}/plays")
    public ResponseEntity<QuizResource> incrementPlays(@PathVariable Long quizId) {
        var incrementCommand = new IncrementQuizPlaysCommand(quizId);
        var quiz = quizCommandService.handle(incrementCommand);

        if (quiz.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var quizResource = QuizResourceFromEntityAssembler.toResourceFromEntity(quiz.get());
        return ResponseEntity.ok(quizResource);
    }

    /**
     * Get quizzes by category
     * @param category the category
     * @return list of quizzes in that category
     */
    @Operation(summary = "Get quizzes by category", description = "Retrieves all quizzes in a specific category")
    @ApiResponse(responseCode = "200", description = "Quizzes retrieved successfully")
    @GetMapping("/category/{category}")
    public ResponseEntity<List<QuizResource>> getQuizzesByCategory(@PathVariable String category) {
        var query = new GetQuizzesByCategoryQuery(category);
        var quizzes = quizQueryService.handle(query);
        var quizResources = quizzes.stream()
                .map(QuizResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(quizResources);
    }

    /**
     * Get quizzes by difficulty level
     * @param difficultyLevel the difficulty level (1-6)
     * @return list of quizzes with that difficulty
     */
    @Operation(summary = "Get quizzes by difficulty", description = "Retrieves all quizzes with a specific difficulty level")
    @ApiResponse(responseCode = "200", description = "Quizzes retrieved successfully")
    @GetMapping("/difficulty/{difficultyLevel}")
    public ResponseEntity<List<QuizResource>> getQuizzesByDifficulty(@PathVariable Integer difficultyLevel) {
        var query = new GetQuizzesByDifficultyQuery(difficultyLevel);
        var quizzes = quizQueryService.handle(query);
        var quizResources = quizzes.stream()
                .map(QuizResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(quizResources);
    }

    /**
     * Get quizzes by type
     * @param type the quiz type (questions, voices, sounds)
     * @return list of quizzes of that type
     */
    @Operation(summary = "Get quizzes by type", description = "Retrieves all quizzes of a specific type")
    @ApiResponse(responseCode = "200", description = "Quizzes retrieved successfully")
    @GetMapping("/type/{type}")
    public ResponseEntity<List<QuizResource>> getQuizzesByType(@PathVariable String type) {
        var query = new GetQuizzesByTypeQuery(type);
        var quizzes = quizQueryService.handle(query);
        var quizResources = quizzes.stream()
                .map(QuizResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(quizResources);
    }

    /**
     * Get popular quizzes
     * @param limit the maximum number of quizzes to return (default: 10)
     * @return list of most played quizzes
     */
    @Operation(summary = "Get popular quizzes", description = "Retrieves the most played quizzes")
    @ApiResponse(responseCode = "200", description = "Popular quizzes retrieved successfully")
    @GetMapping("/popular")
    public ResponseEntity<List<QuizResource>> getPopularQuizzes(
            @RequestParam(defaultValue = "10") Integer limit) {
        var query = new GetPopularQuizzesQuery(limit);
        var quizzes = quizQueryService.handle(query);
        var quizResources = quizzes.stream()
                .map(QuizResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(quizResources);
    }

    /**
     * Get quizzes by creator
     * @param creatorUserId the creator user ID
     * @return list of quizzes created by that user
     */
    @Operation(summary = "Get quizzes by creator", description = "Retrieves all quizzes created by a specific user")
    @ApiResponse(responseCode = "200", description = "Quizzes retrieved successfully")
    @GetMapping("/creator/{creatorUserId}")
    public ResponseEntity<List<QuizResource>> getQuizzesByCreator(@PathVariable Long creatorUserId) {
        var query = new GetQuizzesByCreatorQuery(creatorUserId);
        var quizzes = quizQueryService.handle(query);
        var quizResources = quizzes.stream()
                .map(QuizResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(quizResources);
    }

    /**
     * Search quizzes
     * @param q the search term
     * @return list of matching quizzes
     */
    @Operation(summary = "Search quizzes", description = "Searches quizzes by title or description")
    @ApiResponse(responseCode = "200", description = "Search results retrieved successfully")
    @GetMapping("/search")
    public ResponseEntity<List<QuizResource>> searchQuizzes(@RequestParam String q) {
        var query = new SearchQuizzesQuery(q);
        var quizzes = quizQueryService.handle(query);
        var quizResources = quizzes.stream()
                .map(QuizResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(quizResources);
    }

}