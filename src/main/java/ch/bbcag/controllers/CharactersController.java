package ch.bbcag.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/characters")
public class CharactersController {
    @GetMapping
    public List<ch.bbcag.models.Character> getCharacters() {
        return ch.bbcag.models.Character.getCharacters();
    }

    @GetMapping(path = "{id}")
    public ch.bbcag.models.Character getCharacter(@PathVariable Integer id) {
        return ch.bbcag.models.Character.getCharacters().get(0);
    }
}
