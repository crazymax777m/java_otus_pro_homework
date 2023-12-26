package ru.crazymax.spring.data.jdbc.https.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.crazymax.spring.data.jdbc.https.repositories.AuthorsRepository;
import ru.crazymax.spring.data.jdbc.https.entities.Author;

import java.util.Optional;

@Service
public class AuthorsService {
    private final AuthorsRepository authorsRepository;

    @Autowired
    public AuthorsService(AuthorsRepository authorsRepository) {
        this.authorsRepository = authorsRepository;
    }

    public Optional<Author> findById(@PathVariable Long id) {
        return authorsRepository.findById(id);
    }
}
