// src/main/java/pe/edu/upc/quizbee/quizzies/domain/model/entities/Alternative.java
package pe.edu.upc.quizbee.quizzies.domain.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Alternative {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private Boolean isCorrect = false;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    public Alternative() {}

    public Alternative(String text, Boolean isCorrect) {
        this.text = text;
        this.isCorrect = isCorrect;
    }
}