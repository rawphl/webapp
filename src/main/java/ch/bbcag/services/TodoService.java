package ch.bbcag.services;

import ch.bbcag.models.ApplicationUser;
import ch.bbcag.models.Todo;
import ch.bbcag.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    public Optional<Todo> findOne(int id) {
        return todoRepository.findById(id);
    }

    public Iterable<Todo> findAll(Todo todo) {
        return todoRepository.findAll();
    }

    public Iterable<Todo> findAllOwnedBy(int userId) {
        return todoRepository.findAllOwnedBy(userId);
    }
    public Todo create(Todo todo) {
        return todoRepository.save(todo);
    }
    public Todo update(Todo todo) {
        return todoRepository.save(todo);
    }

    public void delete(int id) {
        todoRepository.deleteById(id);
    }

    public boolean isOwner(Todo todo, ApplicationUser user) {
        return Objects.equals(todo.getUser().getId(), user.getId());
    }
}
