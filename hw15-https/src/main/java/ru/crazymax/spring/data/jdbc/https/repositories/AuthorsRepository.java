package ru.crazymax.spring.data.jdbc.https.repositories;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.crazymax.spring.data.jdbc.https.entities.Author;

@Repository
public interface AuthorsRepository extends ListCrudRepository<Author, Long> {
}