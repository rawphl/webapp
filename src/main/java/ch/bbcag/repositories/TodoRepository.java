package ch.bbcag.repositories;

import ch.bbcag.models.Todo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TodoRepository extends CrudRepository<Todo, Integer> {
    @Query("SELECT t from Todo t WHERE t.user.id = :user_id")
    public Iterable<Todo> findAllOwnedBy(@Param("user_id") int userId);
}
