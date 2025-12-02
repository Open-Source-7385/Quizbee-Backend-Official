// src/main/java/pe/edu/upc/quizbee/quizzies/infrastructure/persistence/jpa/repositories/QuizRepository.java
package pe.edu.upc.quizbee.quizzies.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.quizbee.quizzies.domain.model.aggregates.Quiz;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    // Find by metadata fields
    List<Quiz> findByMetadataCategory(String category);
    List<Quiz> findByMetadataDifficultyLevel(Integer difficultyLevel);
    List<Quiz> findByMetadataType(String type);

    // Find by creator
    List<Quiz> findByCreatorUserId(Long creatorUserId);

    // Search
    List<Quiz> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String titleKeyword,
            String descriptionKeyword
    );
}