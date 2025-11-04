// src/main/java/pe/edu/upc/quizbee/quizzies/interfaces/rest/transform/QuizResourceFromEntityAssembler.java
package pe.edu.upc.quizbee.quizzies.interfaces.rest.transform;

import pe.edu.upc.quizbee.quizzies.domain.model.aggregates.Quiz;
import pe.edu.upc.quizbee.quizzies.interfaces.rest.resources.QuizResource;

import java.util.stream.Collectors;

public class QuizResourceFromEntityAssembler {

    public static QuizResource toResourceFromEntity(Quiz entity) {
        var creatorResource = new QuizResource.CreatorResource(
                entity.getCreator().userId(),
                entity.getCreator().name(),
                entity.getCreator().avatar()
        );

        var questionResources = entity.getQuestions().stream()
                .map(q -> {
                    var alternativeResources = q.getAlternatives().stream()
                            .map(a -> new QuizResource.AlternativeResource(
                                    a.getId(),
                                    a.getText(),
                                    a.getIsCorrect()
                            ))
                            .collect(Collectors.toList());

                    return new QuizResource.QuestionResource(
                            q.getId(),
                            q.getTextQuestion(),
                            q.getOrderNumber(),
                            q.getPoints(),
                            alternativeResources
                    );
                })
                .collect(Collectors.toList());

        return new QuizResource(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                creatorResource,
                entity.getMetadata().category(),
                entity.getMetadata().type(),
                entity.getMetadata().difficultyLevel(),
                entity.getMetadata().getDifficultyStars(),
                entity.getPoints(),
                entity.getMetadata().timeLimit(),
                entity.getFormattedTime(),
                entity.getPlays(),
                entity.getTotalQuestions(),
                entity.getCreatedAt(),
                questionResources
        );
    }
}