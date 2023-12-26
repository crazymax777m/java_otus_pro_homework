package ru.crazymax.spring.data.jdbc.https.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.crazymax.spring.data.jdbc.https.dtos.AuthorDto;
import ru.crazymax.spring.data.jdbc.https.entities.Author;
import ru.crazymax.spring.data.jdbc.https.exceptions.ResourceNotFoundException;
import ru.crazymax.spring.data.jdbc.https.services.AuthorsService;

import java.util.function.Function;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorsController {
    private final AuthorsService authorsService;

    private static final Function<Author, AuthorDto> MAP_TO_DTO_FUNCTION = a -> new AuthorDto(a.getId(), a.getFullName());

    @Autowired
    public AuthorsController(AuthorsService authorsService) {
        this.authorsService = authorsService;
    }

    @GetMapping("/{id}")
    public AuthorDto findById(@PathVariable Long id) {
        return authorsService.findById(id).map(MAP_TO_DTO_FUNCTION).orElseThrow(() -> new ResourceNotFoundException("Автор не найден"));
    }
}
