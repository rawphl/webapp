package ch.bbcag.controllers;

import ch.bbcag.models.ApplicationUser;
import ch.bbcag.models.Todo;
import ch.bbcag.repositories.ApplicationUserRepository;
import ch.bbcag.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/todos")
public class TodosController {
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @GetMapping
    public Iterable<Todo> getAll() {
        return todoRepository.findAll();
    }

    @PostMapping
    public Todo create(@Valid @RequestBody Todo todo) {
        String userName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ApplicationUser user = applicationUserRepository.findByUsername(userName);
        todo.setUser(user);
        todoRepository.save(todo);
        return todo;
    }
}
