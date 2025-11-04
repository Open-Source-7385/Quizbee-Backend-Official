// src/main/java/pe/edu/upc/quizbee/quizzies/interfaces/rest/resources/UpdateQuizResource.java
package pe.edu.upc.quizbee.quizzies.interfaces.rest.resources;

public record UpdateQuizResource(
        String title,
        String description,
        String category,
        String type,
        Integer difficultyLevel,
        Integer timeLimit
) {}