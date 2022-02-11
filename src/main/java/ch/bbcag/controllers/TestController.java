package ch.bbcag.controllers;

import ch.bbcag.models.Tag;
import ch.bbcag.models.Todo;
import ch.bbcag.repositories.TagRepository;
import ch.bbcag.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TodoService todoService;

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping
    @Transactional
    public Object test() {

        Iterable<Todo> todosIterable = todoService.findAll();
        List<Todo> result = new ArrayList<Todo>();
        todosIterable.forEach(result::add);
        Todo todo = result.get(0);
        Tag tag = todo.getTags().get(0);
        todo.getTags().remove(tag);
        todoService.update(todo);

        for(Todo t : tag.getTodos()){
            System.out.println(t);
        }

        System.out.println("SELECT######################################");
        /*
        todosIterable = todoService.findAll();
        result = new ArrayList<Todo>();
        todosIterable.forEach(result::add);
        todo = result.get(0);
        tag = todo.getTags().get(0);
        */
        //tag.getTodos().remove(todo);
        System.out.println("CONTAINS TODO " + tag.getTodos().contains(todo));

        HashMap<String, Object> map = new HashMap<>();
        map.put("todo", todo);
        return map;
    }
}
