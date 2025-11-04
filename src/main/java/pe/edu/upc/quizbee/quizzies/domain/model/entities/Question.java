// src/main/java/pe/edu/upc/quizbee/quizzies/domain/model/entities/Question.java
package pe.edu.upc.quizbee.quizzies.domain.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.quizbee.quizzies.domain.model.aggregates.Quiz;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String textQuestion;

    @Column(nullable = false)
    private Integer orderNumber;

    @Column(nullable = false)
    private Integer points = 10;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alternative> alternatives = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    public Question() {}

    public Question(String textQuestion, Integer orderNumber, Integer points) {
        this.textQuestion = textQuestion;
        this.orderNumber = orderNumber;
        this.points = points;
    }

    public void addAlternative(Alternative alternative) {
        alternatives.add(alternative);
        alternative.setQuestion(this);
    }

    public void removeAlternative(Alternative alternative) {
        alternatives.remove(alternative);
        alternative.setQuestion(null);
    }

    public boolean hasCorrectAnswer() {
        return alternatives.stream().anyMatch(Alternative::getIsCorrect);
    }

    public Optional<Alternative> getCorrectAlternative() {
        return alternatives.stream()
                .filter(Alternative::getIsCorrect)
                .findFirst();
    }
}