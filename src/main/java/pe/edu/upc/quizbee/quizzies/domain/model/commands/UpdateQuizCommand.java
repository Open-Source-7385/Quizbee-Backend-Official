// src/main/java/pe/edu/upc/quizbee/quizzies/domain/model/commands/UpdateQuizCommand.java
package pe.edu.upc.quizbee.quizzies.domain.model.commands;

public record UpdateQuizCommand(
        Long quizId,
        String title,
        String description,
        String category,
        String type,
        Integer difficultyLevel,
        Integer timeLimit
) {}