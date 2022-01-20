package ch.bbcag.repositories;

import ch.bbcag.models.Todo;
import org.springframework.data.repository.CrudRepository;

public interface TodoRepository extends CrudRepository<Todo, Integer> {
}
