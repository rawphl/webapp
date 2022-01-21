package ch.bbcag;

import ch.bbcag.models.ApplicationUser;
import ch.bbcag.models.Todo;
import ch.bbcag.services.ApplicationUserService;
import ch.bbcag.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private ApplicationUserService applicationUserService;
    @Autowired
    private TodoService todoService;
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ApplicationUser user = new ApplicationUser("user", "test");
        applicationUserService.signUp(user);
        Todo newTodo = new Todo();
        newTodo.setName("my fist todo!");
        newTodo.setUser(user);
        todoService.create(newTodo);
    }
}