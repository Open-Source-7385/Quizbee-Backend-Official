// src/main/java/pe/edu/upc/quizbee/quizzies/domain/model/aggregates/Quiz.java
package pe.edu.upc.quizbee.quizzies.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.quizbee.quizzies.domain.model.entities.Question;
import pe.edu.upc.quizbee.quizzies.domain.model.valueobjects.Creator;
import pe.edu.upc.quizbee.quizzies.domain.model.valueobjects.QuizMetadata;
import pe.edu.upc.quizbee.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.ArrayList;
import java.util.List;

/**
 * Quiz Aggregate Root
 * <p>
 * Represents a quiz in the system with all its questions and alternatives.
 * </p>
 */
@Getter
@Setter
@Entity
public class Quiz extends AuditableAbstractAggregateRoot<Quiz> {

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 1000)
    private String description;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "userId", column = @Column(name = "creator_user_id")),
            @AttributeOverride(name = "name", column = @Column(name = "creator_name")),
            @AttributeOverride(name = "avatar", column = @Column(name = "creator_avatar"))
    })
    private Creator creator;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "category", column = @Column(name = "category")),
            @AttributeOverride(name = "type", column = @Column(name = "type")),
            @AttributeOverride(name = "difficultyLevel", column = @Column(name = "difficulty_level")),
            @AttributeOverride(name = "timeLimit", column = @Column(name = "time_limit"))
    })
    private QuizMetadata metadata;

    @Column(nullable = false)
    private Integer points = 0;

    @Column(nullable = false)
    private Integer plays = 0;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    public Quiz() {
        super();
    }

    public Quiz(String title, String description, Creator creator, QuizMetadata metadata) {
        this.title = title;
        this.description = description;
        this.creator = creator;
        this.metadata = metadata;
    }

    // Business methods
    public void addQuestion(Question question) {
        questions.add(question);
        question.setQuiz(this);
        recalculatePoints();
    }

    public void removeQuestion(Question question) {
        questions.remove(question);
        question.setQuiz(null);
        recalculatePoints();
    }

    public void incrementPlays() {
        this.plays++;
    }

    public int getTotalQuestions() {
        return questions.size();
    }

    public void recalculatePoints() {
        this.points = questions.stream()
                .mapToInt(Question::getPoints)
                .sum();
    }

    public boolean isValid() {
        return title != null && !title.isBlank()
                && !questions.isEmpty()
                && questions.stream().allMatch(Question::hasCorrectAnswer);
    }

    public String getFormattedTime() {
        int minutes = metadata.timeLimit() / 60;
        return minutes + " min";
    }
}