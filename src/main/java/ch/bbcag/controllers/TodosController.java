package ch.bbcag.controllers;

import ch.bbcag.models.ApplicationUser;
import ch.bbcag.models.Todo;
import ch.bbcag.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/todos")
public class TodosController extends BaseController {
    @Autowired
    private TodoService todoService;

    @GetMapping
    public Iterable<Todo> getAll() {
        ApplicationUser user = getCurrentUser();
        return todoService.findAllOwnedBy(user.getId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Todo create(@Valid @RequestBody Todo todo) {
        ApplicationUser user = getCurrentUser();
        todo.setUser(user);
        todoService.create(todo);
        return todo;
    }

    @PutMapping("{id}")
    @Transactional
    public Todo update(@Valid @RequestBody Todo todo) {
        ApplicationUser user = getCurrentUser();
        Todo todoToUpdate = todoService.findOne(todo.getId()).orElseThrow();
        if(!todoService.isOwner(todoToUpdate, user)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        todo.setUser(user);
        todoService.update(todo);
        return todo;
    }

    @DeleteMapping("{id}")
    @Transactional
    public Todo delete(@PathVariable Integer id) {
        ApplicationUser user = getCurrentUser();
        Todo todo = todoService.findOne(id).orElseThrow();

        if(!todoService.isOwner(todo, user)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        todo.setUser(user);
        todoService.delete(id);
        return todo;
    }
}
