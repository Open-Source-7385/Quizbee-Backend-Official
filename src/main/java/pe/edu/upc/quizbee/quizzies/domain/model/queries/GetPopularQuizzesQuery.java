// src/main/java/pe/edu/upc/quizbee/quizzies/domain/model/queries/GetPopularQuizzesQuery.java
package pe.edu.upc.quizbee.quizzies.domain.model.queries;

public record GetPopularQuizzesQuery(Integer limit) {
    public GetPopularQuizzesQuery {
        if (limit == null || limit <= 0) {
            limit = 10;
        }
    }
}