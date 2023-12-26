package ru.crazymax.spring.data.jdbc.https.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Table("BOOKS_DETAILS")
public class BookDetails {
    @Id
    private Long id;
    private Long bookId;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @PersistenceCreator
    public BookDetails(Long id, Long bookId, String description) {
        this.id = id;
        this.bookId = bookId;
        this.description = description;
    }
}
